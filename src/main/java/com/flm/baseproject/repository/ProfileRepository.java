package com.flm.baseproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flm.baseproject.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

	Profile findProfileByName(String name);

	List<Profile> findByNameContainingIgnoreCase(String name);
	
}
