package com.flm.baseproject.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import com.flm.baseproject.enumerator.Profiles;
import com.flm.baseproject.exception.Validations;
import com.flm.baseproject.model.User;
import com.flm.baseproject.repository.UserRepository;
import com.flm.baseproject.utils.StringUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository repository;
	
	private final ProfileService profileService;

	private final PasswordEncoder passwordEncoder;

	public User save(User user) {
		if (user.getId() == 0l) {
			this.validateNew(user);
			user.setPassword(passwordEncoder.encode(user.getNewPassword()));
		}
		else {
			this.validateUpdate(user);
			
			if (StringUtils.isNotEmpty(user.getNewPassword()))
				user.setPassword(passwordEncoder.encode(user.getNewPassword()));
		}
		
		return repository.save(user);
	}
	
	public User registerUser(User user) {
		user.setProfile(this.profileService.findByName(Profiles.PROFILE_REGULAR.getName()));
		
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

	public void deleteById(long id) {
		this.validateDelete(id);
		
		repository.deleteById(id);
	}
	
	public void validateDelete(long id) {
		Validations validations = new Validations();
		
		User user = this.findById(id);
		
		if (user == null)
			validations.add("user", "User not found");
		
		validations.throwsExceptions();
	}

	public List<User> findAllOrderByNameAsc() {
		return repository.findAllOrderByNameAsc();
	}
	
	public User findByLoginOrEmail(String loginOrEmail) {
		return repository.findByLoginOrEmail(loginOrEmail);
	}

	
}
