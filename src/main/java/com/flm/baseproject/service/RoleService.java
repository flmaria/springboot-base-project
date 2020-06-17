package com.flm.baseproject.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flm.baseproject.enumerator.Roles;
import com.flm.baseproject.exception.Validations;
import com.flm.baseproject.model.Role;
import com.flm.baseproject.repository.RoleRepository;
import com.flm.baseproject.utils.StringUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RoleService {

	private final RoleRepository repository;
	
	public Role findById(long id) {
		return this.repository.findById(id);
	}
	
	public Role findByName(Roles roleName) {
		return this.repository.findByName(roleName);
	}
	
	@Transactional
	public Role save(Role role) {
		if (role.getId() == 0l)
			this.validateNew(role);
		else
			this.validateUpdate(role);
		
		return this.repository.save(role);
	}
	
	private void validateNew(Role role) {
		this.validateNew(role, null, true);
	}
	
	private void validateNew(Role role, Validations validations, boolean triggerException) {
		if (validations == null) {
			validations = new Validations();
		}
		
		//Name
		if (role.getName() == null) {
			validations.add("name", "Enter the Name");
		}
		
		if (this.findByName(role.getName()) != null) {
			validations.add("name", "Enter a different Name");
		}
		
		//Description
		if (StringUtils.isEmpty(role.getDescription())) {
			validations.add("description", "Enter the Description");
		}
		
		if (triggerException)
			validations.throwsExceptions();
	}
	
	private void validateUpdate(Role role) {
		Validations validations = new Validations();
		
		this.validateNew(role, validations, false);
		
		Role current = this.findById(role.getId());
		
		if (current == null)
			validations.add("role", "Role not found");
		else {
			if (!role.getName().equals(current.getName())) {
				if (this.findByName(role.getName()) != null) {
					validations.add("name", "Enter a different Name");
				}
			}
		}
		
		validations.throwsExceptions();
	}
	
}
