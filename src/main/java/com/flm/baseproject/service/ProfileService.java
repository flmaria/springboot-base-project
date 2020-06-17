package com.flm.baseproject.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flm.baseproject.exception.Validations;
import com.flm.baseproject.model.Profile;
import com.flm.baseproject.model.Role;
import com.flm.baseproject.repository.ProfileRepository;
import com.flm.baseproject.utils.ListUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProfileService {

	private final ProfileRepository repository;

	@Transactional
	public Profile save(Profile profile) {
		this.validate(profile);
		
		return repository.save(profile);
	}
	
	private void validate(Profile profile) {
		Validations validations = new Validations();
			
		//Name
		if (profile.getName() == null)
			validations.add("name", "Enter the Name");
		
		//Roles
		if (ListUtils.isEmpty(profile.getRoles()))
			validations.add("roles", "Enter the Roles");
		else {
			Set<String> roleNameSet = new HashSet<String>();
			
			for (Role role: profile.getRoles()) {
				roleNameSet.add(role.getName().name());
			}
			
			if (roleNameSet.size() < profile.getRoles().size())
				validations.add("roles", "Duplicate Roles");
		}
		
		validations.throwsExceptions();
	}
	
	public Profile findById(long id) {
		return repository.findById(id);
	}

	public Profile findByName(String name) {
		return this.repository.findByName(name);
	}
	
	public List<Profile> findAll() {
		return repository.findAll();
	}
	
}
