package com.flm.baseproject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.flm.baseproject.model.Profile;
import com.flm.baseproject.model.User;

@SpringBootTest
public class CustomUserDetailsServiceTest {

	@Mock
	UserService userService;
	
	@InjectMocks
	CustomUserDetailsService service;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void it_should_load_user_by_username() {
		
		User user = new User();
		user.setName("custom-user-details-service-test");
		user.setLogin("custom-user-details-service-test");
		user.setEmail("custom-user-details-service-test@test.com");
		user.setProfile(new Profile());
		
		Mockito.when(userService.findByLoginOrEmail(Mockito.any(String.class))).thenReturn(user);
		
		
		assertEquals(user.getName(), this.service.loadUserByUsername("custom-user-details-service-test").getUsername());
	}
	
	@Test(expected = UsernameNotFoundException.class)
	public void it_should_not_load_user_by_username_not_exists() {
		Mockito.when(userService.findByLoginOrEmail(Mockito.any(String.class))).thenReturn(null);
		
		this.service.loadUserByUsername("custom-user-details-service-test");
	}
	
	@Test
	public void it_should_load_user_by_id() {
		
		User user = new User();
		user.setId(99l);
		user.setName("custom-user-details-service-test");
		user.setLogin("custom-user-details-service-test");
		user.setEmail("custom-user-details-service-test@test.com");
		user.setProfile(new Profile());
		
		Mockito.when(userService.findById(Mockito.anyLong())).thenReturn(user);
		
		
		assertEquals(user.getName(), this.service.loadUserById(user.getId()).getUsername());
	}
	
	@Test(expected = UsernameNotFoundException.class)
	public void it_should_not_load_user_by_id_not_exists() {
		Mockito.when(userService.findById(Mockito.anyLong())).thenReturn(null);
		this.service.loadUserById(99l);
	}

}
