package com.flm.baseproject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.flm.baseproject.enumerator.Roles;
import com.flm.baseproject.exception.RequiredFieldsException;
import com.flm.baseproject.model.Role;
import com.flm.baseproject.repository.RoleRepository;

@SpringBootTest
public class RoleServiceTest {

	@Mock
	RoleRepository repository;
	
	@InjectMocks
	RoleService service;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void it_should_save() throws Exception {
		Roles roles = Roles.ROLE_USER_LIST;
		
		Role role = new Role();
		role.setName(roles);
		role.setDescription(roles.getDescription());
		
		Mockito.when(repository.save(Mockito.any(Role.class))).thenReturn(role);
		
		assertEquals(role.getName(), this.service.save(role).getName());
	}
	
	@Test(expected = RequiredFieldsException.class)
	public void it_should_not_save_empty_name() throws Exception {
		Roles roles = Roles.ROLE_USER_LIST;
		
		Role role = new Role();
		role.setDescription(roles.getDescription());
		this.service.save(role);
	}
	
	@Test(expected = RequiredFieldsException.class)
	public void it_should_not_save_empty_description() throws Exception {
		Roles roles = Roles.ROLE_USER_LIST;
		
		Role role = new Role();
		role.setName(roles);
		this.service.save(role);
	}
	
	@Test(expected = RequiredFieldsException.class)
	public void it_should_not_save_name_already_exists() throws Exception {
		Roles roles = Roles.ROLE_USER_LIST;
		
		Role role = new Role();
		role.setName(roles);
		role.setDescription(roles.getDescription());
		
		Mockito.when(repository.findByName(Mockito.any(Roles.class))).thenReturn(role);
		
		this.service.save(role);
	}
	
	@Test
	public void it_should_update() throws Exception {
		Roles roles = Roles.ROLE_USER_LIST;
		
		Role role = new Role();
		role.setId(99l);
		role.setName(roles);
		role.setDescription(roles.getDescription());
		
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(role);
		Mockito.when(repository.save(Mockito.any(Role.class))).thenReturn(role);
		
		assertEquals(role.getId(), this.service.save(role).getId());
	}
	
	@Test(expected = RequiredFieldsException.class)
	public void it_should_not_save_update_name() throws Exception {
		Roles roles = Roles.ROLE_USER_LIST;
		
		Role role = new Role();
		role.setId(99l);
		role.setDescription(roles.getDescription());
		this.service.save(role);
	}
	
	@Test(expected = RequiredFieldsException.class)
	public void it_should_not_update_name_already_exists() throws Exception {
		Roles roles = Roles.ROLE_USER_LIST;
		
		Role role = new Role();
		role.setId(99l);
		role.setName(roles);
		role.setDescription(roles.getDescription());
		
		Role role2 = new Role();
		role2.setId(100l);
		role2.setName(roles);
		role2.setDescription(roles.getDescription());
		
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(role);
		Mockito.when(repository.findByName((Mockito.any(Roles.class)))).thenReturn(role2);
		Mockito.when(repository.save(Mockito.any(Role.class))).thenReturn(role);
		
		
		assertEquals(role.getId(), this.service.save(role).getId());
	}
	
	@Test(expected = RequiredFieldsException.class)
	public void it_should_not_save_update_description() throws Exception {
		Roles roles = Roles.ROLE_USER_LIST;
		
		Role role = new Role();
		role.setId(99l);
		role.setName(roles);
		this.service.save(role);
	}
	
	public void it_should_return_by_id() throws Exception {
		Roles roles = Roles.ROLE_USER_LIST;
		
		Role role = new Role();
		role.setId(99l);
		role.setName(roles);
		role.setDescription(roles.getDescription());
		
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(role);
		
		assertEquals(role.getId(), this.service.findById(role.getId()).getId());
	}
	
	public void it_should_return_by_name() throws Exception {
		Roles roles = Roles.ROLE_USER_LIST;
		
		Role role = new Role();
		role.setId(99l);
		role.setName(roles);
		role.setDescription(roles.getDescription());
		
		Mockito.when(repository.findByName((Mockito.any(Roles.class)))).thenReturn(role);
		
		assertEquals(role.getName(), this.service.findByName(roles).getName());
	}

}
