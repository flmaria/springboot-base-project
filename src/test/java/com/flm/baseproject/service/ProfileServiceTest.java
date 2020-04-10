package com.flm.baseproject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;


import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.flm.baseproject.enumerator.Roles;
import com.flm.baseproject.exception.RequiredFieldsException;
import com.flm.baseproject.model.Profile;
import com.flm.baseproject.model.Role;
import com.flm.baseproject.repository.ProfileRepository;

@SpringBootTest
public class ProfileServiceTest {

	@Mock
	ProfileRepository repository;
	
	@InjectMocks
	ProfileService service;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void it_should_save() throws Exception {
		Profile profile = new Profile();
		profile.setName("profile-service-test");
		
		List<Role> roleList = new ArrayList<Role>();
		
		Role role = new Role();
		role.setName(Roles.ROLE_USER_LIST);
		role.setDescription(Roles.ROLE_USER_LIST.getDescription());
		roleList.add(role);
		
		profile.setRoles(roleList);
		
		
		Mockito.when(repository.save(Mockito.any(Profile.class))).thenReturn(profile);
		
		assertEquals(profile.getName(), this.service.save(profile).getName());
	}
	
	@Test(expected = RequiredFieldsException.class)
	public void it_should_not_save_empty_name() throws Exception {
		Profile profile = new Profile();
		
		this.service.save(profile);
	}
	
	@Test(expected = RequiredFieldsException.class)
	public void it_should_not_save_empty_roles() throws Exception {
		Profile profile = new Profile();
		profile.setName("profile-service-test");
		
		this.service.save(profile);
	}
	
	@Test(expected = RequiredFieldsException.class)
	public void it_should_not_save_duplicated_roles() throws Exception {
		Profile profile = new Profile();
		profile.setName("profile-service-test");
		
		List<Role> roleList = new ArrayList<Role>();
		Role role = new Role();
		role.setName(Roles.ROLE_USER_LIST);
		role.setDescription(Roles.ROLE_USER_LIST.getDescription());
		roleList.add(role);
		
		Role role2 = new Role();
		role2.setName(Roles.ROLE_USER_LIST);
		role2.setDescription(Roles.ROLE_USER_LIST.getDescription());
		roleList.add(role2);
		
		profile.setRoles(roleList);
		
		this.service.save(profile);
	}
	
	@Test
	public void it_should_update() throws Exception {
		Profile profile = new Profile();
		profile.setId(99l);
		profile.setName("profile-service-test");
		
		List<Role> roleList = new ArrayList<Role>();
		
		Role role = new Role();
		
		role.setName(Roles.ROLE_USER_LIST);
		role.setDescription(Roles.ROLE_USER_LIST.getDescription());
		roleList.add(role);
		
		profile.setRoles(roleList);
		
		
		Mockito.when(repository.save(Mockito.any(Profile.class))).thenReturn(profile);
		
		assertEquals(profile.getId(), this.service.save(profile).getId());
	}
	
	@Test(expected = RequiredFieldsException.class)
	public void it_should_not_update_empty_name() throws Exception {
		Profile profile = new Profile();
		
		this.service.save(profile);
	}
	
	@Test(expected = RequiredFieldsException.class)
	public void it_should_not_update_empty_roles() throws Exception {
		Profile profile = new Profile();
		profile.setName("profile-service-test");
		
		this.service.save(profile);
	}
	
	@Test(expected = RequiredFieldsException.class)
	public void it_should_not_update_duplicated_roles() throws Exception {
		Profile profile = new Profile();
		profile.setName("profile-service-test");
		
		List<Role> roleList = new ArrayList<Role>();
		Role role = new Role();
		role.setName(Roles.ROLE_USER_LIST);
		role.setDescription(Roles.ROLE_USER_LIST.getDescription());
		roleList.add(role);
		
		Role role2 = new Role();
		role2.setName(Roles.ROLE_USER_LIST);
		role2.setDescription(Roles.ROLE_USER_LIST.getDescription());
		roleList.add(role2);
		
		profile.setRoles(roleList);
		
		this.service.save(profile);
	}
	
	@Test
	public void it_should_return_by_id() throws Exception {
		Profile profile = new Profile();
		profile.setId(99l);
		profile.setName("profile-service-test");
		
		List<Role> roleList = new ArrayList<Role>();
		Role role = new Role();
		role.setName(Roles.ROLE_USER_LIST);
		role.setDescription(Roles.ROLE_USER_LIST.getDescription());
		roleList.add(role);
		
		profile.setRoles(roleList);
		
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(profile);
		
		assertEquals(profile.getId(), this.service.findById(99l).getId());
	}
	
	@Test
	public void it_should_return_by_name() throws Exception {
		Profile profile = new Profile();
		profile.setName("profile-service-test");
		
		List<Role> roleList = new ArrayList<Role>();
		Role role = new Role();
		role.setName(Roles.ROLE_USER_LIST);
		role.setDescription(Roles.ROLE_USER_LIST.getDescription());
		roleList.add(role);
		
		Mockito.when(repository.findByName(Mockito.any(String.class))).thenReturn(profile);
		
		assertEquals(profile.getName(), this.service.findByName("profile-service-test").getName());
	}
}
