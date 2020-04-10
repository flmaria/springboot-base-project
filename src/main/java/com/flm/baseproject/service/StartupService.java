package com.flm.baseproject.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.flm.baseproject.enumerator.Profiles;
import com.flm.baseproject.enumerator.Roles;
import com.flm.baseproject.model.Profile;
import com.flm.baseproject.model.Role;
import com.flm.baseproject.model.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StartupService {
	
	private final RoleService roleService;

	private final ProfileService profileService;

	private final UserService userService;
	
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
		for (Roles roles : Roles.values()) {
			Role role = this.roleService.findByName(roles);
			if (role == null) {
				role = new Role();
				role.setName(roles);
				role.setDescription(roles.getDescription());
				this.roleService.save(role);
			}
		}

		// CREATE PROFILES
		for (Profiles p : Profiles.values()) {
			Profile profile = this.profileService.findByName(p.getName());
			
			if(null == profile) {
				profile = new Profile();
				profile.setName(p.getName());
				for (Enum e : p.getRoles()) {
					Roles r = (Roles) e;
					Role bdRole = this.roleService.findByName(r);
					
					profile.getRoles().add(bdRole);
				}
				
				this.profileService.save(profile);
			}
		}
		
		User adminUser = this.userService.findByLogin(adminLogin);
		
		if (adminUser == null) {
			adminUser = new User();
			adminUser.setEmail(adminEmail);
			adminUser.setName(adminName);
			adminUser.setLogin(adminLogin);
			adminUser.setNewPassword(adminPassword);
			adminUser.setProfile(this.profileService.findByName(Profiles.PROFILE_ADMIN.getName()));
			
			this.userService.save(adminUser);
		}
	}

}
