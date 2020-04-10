package com.flm.baseproject.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.flm.baseproject.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

	public Profile findById(long id);
	
	public Profile findByName(String name);
	
}
