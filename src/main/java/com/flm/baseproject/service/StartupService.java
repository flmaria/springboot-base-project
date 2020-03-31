package com.flm.baseproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.flm.baseproject.enumerator.Profiles;
import com.flm.baseproject.enumerator.Roles;
import com.flm.baseproject.model.Profile;
import com.flm.baseproject.model.Role;
import com.flm.baseproject.model.User;
import com.flm.baseproject.repository.ProfileRepository;
import com.flm.baseproject.repository.RoleRepository;
import com.flm.baseproject.repository.UserRepository;

@Service
public class StartupService {
	
	@Autowired
	RoleRepository roleRepository;

	@Autowired
	ProfileRepository profileRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Value("${admin.login}")
	private String adminLogin;

	@Value("${admin.name}")
	private String adminName;

	@Value("${admin.email}")
	private String adminEmail;

	@Value("${admin.password}")
	private String adminPassword;

	@SuppressWarnings("rawtypes")
	public void init() {

		// INSERT ROLES
		for (Roles rn : Roles.values()) {
			Role bdRole = roleRepository.findByName(rn);
			if (null == bdRole) {
				Role role = new Role();
				role.setName(rn);
				role.setDescription(rn.getDescription());
				roleRepository.save(role);
			}
		}

		// CREATE PROFILES
		
		for (Profiles p : Profiles.values()) {
			Profile profile = profileRepository.findProfileByName(p.getName());
			if(null == profile) {
				profile = new Profile();
				profile.setName(p.getName());
				for (Enum e : p.getRoles()) {
					Roles r = (Roles) e;
					Role bdRole = roleRepository.findByName(r);
					profile.getRoles().add(bdRole);
				}
				profileRepository.save(profile);
			}

			
		}
		

		Boolean existsByLogin = userRepository.existsByLogin(adminLogin);
		String adminPswrd = passwordEncoder.encode(adminPassword);
		
		if (!existsByLogin) {
			User adminUser = new User();
			adminUser.setEmail(adminEmail);
			adminUser.setName(adminName);
			adminUser.setLogin(adminLogin);
			adminUser.setPassword(adminPswrd);
			adminUser.setProfile(this.profileRepository.findProfileByName(Profiles.PROFILE_ADMIN.getName()));
			
			userRepository.save(adminUser);
		}

	}

}
