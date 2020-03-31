package com.flm.baseproject.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flm.baseproject.model.Profile;
import com.flm.baseproject.repository.ProfileRepository;

@Service
public class ProfileService {

	@Autowired
	ProfileRepository repository;

	public Profile save(Profile Profile) {
		return repository.save(Profile);
	}

	public Optional<Profile> findById(long id) {
		return repository.findById(id);
	}

	public void deleteById(long id) {
		repository.deleteById(id);
	}

	public Profile findByName(String name) {
		return this.repository.findProfileByName(name);
	}
	
	public List<Profile> findAll() {
		return repository.findAll();
	}

	public List<Profile> list(Map<String, String> params) {

		if (null == params || params.isEmpty()) {
			return this.findAll();
		} else {
			String name = null != params.get("name") ? params.get("name") : "";
			return repository.findByNameContainingIgnoreCase(name);
		}

	}
	
}
