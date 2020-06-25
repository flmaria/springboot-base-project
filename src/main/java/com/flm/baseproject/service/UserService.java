package com.flm.baseproject.service;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.flm.baseproject.dto.PageResult;
import com.flm.baseproject.enumerator.Profiles;
import com.flm.baseproject.exception.Validations;
import com.flm.baseproject.model.Profile;
import com.flm.baseproject.model.User;
import com.flm.baseproject.repository.ProfileRepository;
import com.flm.baseproject.repository.UserRepository;
import com.flm.baseproject.utils.StringUtils;

import lombok.RequiredArgsConstructor;

//@Validated
@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository repository;
	
	private final ProfileRepository profileRepository;

	private final PasswordEncoder passwordEncoder;

	@Transactional
	public User save(@Valid User user) {
		if (user.getId() == 0l) {
			this.validateNew(user);
			user.setPassword(passwordEncoder.encode(user.getNewPassword()));
			return repository.save(user);
		}
		else {
			this.validateUpdate(user);
			
			User updatedUser = this.findById(user.getId());
			updatedUser.setName(user.getName());
			updatedUser.setLogin(user.getLogin());
			updatedUser.setEmail(user.getEmail());
			updatedUser.setProfile(user.getProfile());
			
			if (StringUtils.isNotEmpty(user.getNewPassword()))
				updatedUser.setPassword(passwordEncoder.encode(user.getNewPassword()));
			
			return repository.save(updatedUser);
		}
	}
	
	@Transactional
	public User registerUser(User user) {
		user.setProfile(this.profileRepository.findByName(Profiles.PROFILE_REGULAR.getName()));
		
		this.validateNew(user);
		
		user.setPassword(passwordEncoder.encode(user.getNewPassword()));
		
		return this.save(user);
	}
	
	private void validateNew(User user) {
		this.validateNew(user, null, true);
	}
	
	private void validateNew(User user, Validations validations, boolean triggerException) {
		if (validations == null) {
			validations = new Validations();
		}
		
		//Login
		User userLogin = this.repository.findByLogin(user.getLogin());
		
		if (userLogin != null) {
			if (user.getId() == 0l || (user.getId() >= 0l && userLogin.getId() != user.getId())) {
				validations.add("login", "Enter a different Login");
			}
		}
		
		//E-mail
		User userEmail = this.repository.findByEmail(user.getEmail());
		
		if (userEmail != null) {
			if (user.getId() == 0l || (user.getId() >= 0l && userEmail.getId() != user.getId())) {
				validations.add("e-mail", "Enter a different E-mail");
			}
		}
		
		//Password
		if (user.getId() == 0l) {
			if (StringUtils.isEmpty(user.getNewPassword())) {
				validations.add("password", "Enter the Password");
			}
		}
		
		//Profile
		if (user.getProfile() == null) {
			validations.add("profile", "Enter a Profile");
		}
		else {
			if (this.profileRepository.findByName(user.getProfile().getName()) == null) {
				validations.add("profile", "Profile not found");
			}
		}
		
		if (triggerException)
			validations.throwsExceptions();
	}
	
	private void validateUpdate(User user) {
		Validations validations = new Validations();
		
		this.validateNew(user, validations, false);
		
		User current = this.findById(user.getId());
		
		if (current == null) {
			validations.add("user", "User not found");
		}
		
		validations.throwsExceptions();
	}

	public User findById(long id) {
		return repository.findById(id);
	}
	
	public User findByLogin(String login) {
		return repository.findByLogin(login);
	}

	@Transactional
	public void deleteById(long id, long loggedUserId) {
		this.validateDelete(id, loggedUserId);
		
		repository.deleteById(id);
	}
	
	public void validateDelete(long id, long loggedUserId) {
		Validations validations = new Validations();
		
		User user = this.findById(id);
		
		if (user == null) {
			validations.add("user", "User not found");
		}
		else if (id == loggedUserId) {
			validations.add("user", "You cannot delete your own user");
		}
		
		validations.throwsExceptions();
	}

	public User findByLoginOrEmail(String loginOrEmail) {
		return repository.findByLoginOrEmail(loginOrEmail);
	}
	
	public PageResult<User> findAllPageable(int pageIndex, int pageSize, String sortBy, boolean sortAscending) {
		Sort sort = null;
		
		if (StringUtils.isNotEmpty(sortBy)) {
			sort = Sort.by(sortBy);
			
			if (sortAscending) {
				sort = sort.ascending();
			}
			else {
				sort = sort.descending();
			}
		}
		
		Page<User> page = this.repository.findAll(PageRequest.of(pageIndex, pageSize, sort));
		return new PageResult<User>(page.getContent(), page.getTotalElements());
	}
		
}
