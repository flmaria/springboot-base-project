package com.flm.baseproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flm.baseproject.enumerator.Roles;
import com.flm.baseproject.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	public Role findById(long id);
	
	Role findByName(Roles roleName);
	
}
