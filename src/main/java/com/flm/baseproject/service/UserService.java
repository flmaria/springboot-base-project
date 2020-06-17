package com.flm.baseproject.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flm.baseproject.dto.PageResult;
import com.flm.baseproject.enumerator.Profiles;
import com.flm.baseproject.exception.Validations;
import com.flm.baseproject.model.User;
import com.flm.baseproject.repository.ProfileRepository;
import com.flm.baseproject.repository.UserRepository;
import com.flm.baseproject.utils.StringUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository repository;
	
	private final ProfileRepository profileRepository;

	private final PasswordEncoder passwordEncoder;

	@Transactional
	public User save(User user) {
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
		
		//Name
		if (StringUtils.isEmpty(user.getName())) {
			validations.add("name", "Enter the Name");
		}
		
		//Login
		if (StringUtils.isEmpty(user.getLogin())) {
			validations.add("login", "Enter the Login");
		}
		
		if (user.getId() == 0l) {
			if (this.findByLogin(user.getLogin()) != null) {
				validations.add("login", "Enter a different Login");
			}
		}
		
		//E-mail
		if (StringUtils.isEmpty(user.getEmail())) {
			validations.add("e-mail", "Enter the E-mail");
		}
		
		if (user.getId() == 0l) {
			if (this.repository.findByEmail(user.getEmail()) != null) {
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
		
		if (triggerException)
			validations.throwsExceptions();
	}
	
	private void validateUpdate(User user) {
		Validations validations = new Validations();
		
		this.validateNew(user, validations, false);
		
		User current = this.findById(user.getId());
		
		if (current == null)
			validations.add("user", "User not found");
		else {
			if (StringUtils.isNotEmpty(user.getLogin()) && !user.getLogin().equals(current.getLogin())) {
				if (this.findByLogin(user.getLogin()) != null) {
					validations.add("login", "Enter a different Login");
				}
			}
			
			if (StringUtils.isNotEmpty(user.getEmail()) && !user.getEmail().equals(current.getEmail())) {
				if (this.repository.findByEmail(user.getEmail()) != null) {
					validations.add("e-mail", "Enter a different E-mail");
				}
			}
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
		
		if (user == null)
			validations.add("user", "User not found");
		
		if (id == loggedUserId) {
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
