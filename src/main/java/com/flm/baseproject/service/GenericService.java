package com.flm.baseproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flm.baseproject.model.Generic;
import com.flm.baseproject.repository.GenericRepository;

@Service
public class GenericService {

	@Autowired
	GenericRepository genericRepository;

	public Generic save(Generic generic) {
		return genericRepository.save(generic);
	}

	public Optional<Generic> findById(long id) {
		return genericRepository.findById(id);
	}
	
	public Optional<Generic> findByIdAndUserId(long id, long userId) {
		return genericRepository.findByIdAndUserId(id, userId);
	}

	public void deleteById(long id) {
		genericRepository.deleteById(id);
	}

	public List<Generic> listAll() {
		return genericRepository.findAll();
	}
	
	public List<Generic> findAllByUserIdAndOrderByNameAsc(long userId) {
		return genericRepository.findAllByUserIdAndOrderByNameAsc(userId);
	}
	
	public List<Generic> searchByUserIdAndOrderByNameAsc(long userId, String text) {
		return genericRepository.searchByUserIdAndOrderByNameAsc(userId, text);
	}
		
}
