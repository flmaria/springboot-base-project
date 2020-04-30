package com.flm.baseproject.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flm.baseproject.model.Profile;
import com.flm.baseproject.service.ProfileService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/profiles")
public class ProfileController {

	private final ProfileService profileService;
	
	@GetMapping
    public List<Profile> listAll() {
		return this.profileService.findAll();
    }
	
}
