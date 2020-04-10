package com.flm.baseproject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.flm.baseproject.enumerator.Profiles;
import com.flm.baseproject.exception.RequiredFieldsException;
import com.flm.baseproject.model.Profile;
import com.flm.baseproject.model.User;
import com.flm.baseproject.repository.UserRepository;

@SpringBootTest
public class UserServiceTest {

	@Mock
	UserRepository repository;
	
	@Mock
	PasswordEncoder passwordEncoder;
	
	@Mock
	ProfileService profileService;
	
	@InjectMocks
	UserService service;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void it_should_save() throws Exception {
		User user = new User();
		user.setName("user-service-test");
		user.setLogin("user-service-test-login");
		user.setEmail("user-service-test@test.com");
		user.setNewPassword("123");
		user.setProfile(new Profile());
		
		Mockito.when(passwordEncoder.encode(Mockito.any(String.class))).thenReturn("pwd");
		Mockito.when(repository.findByLogin(Mockito.any(String.class))).thenReturn(null);
		Mockito.when(repository.findByEmail(Mockito.any(String.class))).thenReturn(null);
		
		Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(user);
		
		assertEquals(user.getName(), this.service.save(user).getName());
	}
	
	@Test(expected = RequiredFieldsException.class)
	public void it_should_not_save_empty_name() throws Exception {
		User user = new User();
		user.setLogin("user-service-test-login");
		user.setEmail("user-service-test@test.com");
		user.setNewPassword("123");
		user.setProfile(new Profile());
		
		this.service.save(user);
	}
	
	@Test(expected = RequiredFieldsException.class)
	public void it_should_not_save_empty_login() throws Exception {
		User user = new User();
		user.setName("user-service-test");
		user.setEmail("user-service-test@test.com");
		user.setNewPassword("123");
		user.setProfile(new Profile());
		
		this.service.save(user);
	}
	
	@Test(expected = RequiredFieldsException.class)
	public void it_should_not_save_duplicated_login() throws Exception {
		User user = new User();
		user.setName("user-service-test");
		user.setLogin("user-service-test-login");
		user.setEmail("user-service-test@test.com");
		user.setNewPassword("123");
		user.setProfile(new Profile());
		
		User user2 = new User();
		user2.setId(100l);
		user2.setName("user-service-test");
		user2.setLogin("user-service-test-login");
		
		Mockito.when(repository.findByLogin(Mockito.any(String.class))).thenReturn(user2);
		
		this.service.save(user);
	}
	
	@Test(expected = RequiredFieldsException.class)
	public void it_should_not_save_empty_email() throws Exception {
		User user = new User();
		user.setName("user-service-test");
		user.setLogin("user-service-test-login");
		user.setNewPassword("123");
		user.setProfile(new Profile());
		
		this.service.save(user);
	}
	
	@Test(expected = RequiredFieldsException.class)
	public void it_should_not_save_duplicated_email() throws Exception {
		User user = new User();
		user.setName("user-service-test");
		user.setLogin("user-service-test-login");
		user.setEmail("user-service-test@test.com");
		user.setNewPassword("123");
		user.setProfile(new Profile());
		
		User user2 = new User();
		user2.setId(100l);
		user2.setName("user-service-test");
		user2.setLogin("user-service-test-login");
		user2.setEmail("user-service-test@test.com");
		
		Mockito.when(repository.findByEmail(Mockito.any(String.class))).thenReturn(user2);
		
		this.service.save(user);
	}
	
	@Test(expected = RequiredFieldsException.class)
	public void it_should_not_save_empty_new_password() throws Exception {
		User user = new User();
		user.setName("user-service-test");
		user.setLogin("user-service-test-login");
		user.setEmail("user-service-test@test.com");
		
		user.setProfile(new Profile());
		
		this.service.save(user);
	}
	
	@Test(expected = RequiredFieldsException.class)
	public void it_should_not_save_empty_profile() throws Exception {
		User user = new User();
		user.setName("user-service-test");
		user.setLogin("user-service-test-login");
		user.setEmail("user-service-test@test.com");
		user.setNewPassword("123");
		
		this.service.save(user);
	}
	
///////
	
	@Test
	public void it_should_register_user() throws Exception {
		User user = new User();
		user.setName("user-service-test");
		user.setLogin("user-service-test-login");
		user.setEmail("user-service-test@test.com");
		user.setNewPassword("123");
		
		Profile profile = new Profile();
		profile.setName(Profiles.PROFILE_REGULAR.getName());
		
		Mockito.when(profileService.findByName(Mockito.any(String.class))).thenReturn(profile);
		Mockito.when(passwordEncoder.encode(Mockito.any(String.class))).thenReturn("pwd");
		Mockito.when(repository.findByLogin(Mockito.any(String.class))).thenReturn(null);
		Mockito.when(repository.findByEmail(Mockito.any(String.class))).thenReturn(null);
		
		Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(user);
		
		assertEquals(user.getName(), this.service.registerUser(user).getName());
	}
	
	@Test
	public void it_should_register_user_with_regular_profile() throws Exception {
		User user = new User();
		user.setName("user-service-test");
		user.setLogin("user-service-test-login");
		user.setEmail("user-service-test@test.com");
		user.setNewPassword("123");
		
		Profile profile = new Profile();
		profile.setName(Profiles.PROFILE_REGULAR.getName());
		
		Mockito.when(profileService.findByName(Mockito.any(String.class))).thenReturn(profile);
		Mockito.when(passwordEncoder.encode(Mockito.any(String.class))).thenReturn("pwd");
		Mockito.when(repository.findByLogin(Mockito.any(String.class))).thenReturn(null);
		Mockito.when(repository.findByEmail(Mockito.any(String.class))).thenReturn(null);
		
		Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(user);
		
		assertEquals(Profiles.PROFILE_REGULAR.getName(), this.service.registerUser(user).getProfile().getName());
	}
	
	@Test(expected = RequiredFieldsException.class)
	public void it_should_not_register_user_empty_name() throws Exception {
		User user = new User();
		user.setLogin("user-service-test-login");
		user.setEmail("user-service-test@test.com");
		user.setNewPassword("123");
		
		Profile profile = new Profile();
		profile.setName(Profiles.PROFILE_REGULAR.getName());
		
		Mockito.when(profileService.findByName(Mockito.any(String.class))).thenReturn(profile);
		
		this.service.registerUser(user);
	}
	
	@Test(expected = RequiredFieldsException.class)
	public void it_should_not_register_user_empty_login() throws Exception {
		User user = new User();
		user.setName("user-service-test");
		user.setEmail("user-service-test@test.com");
		user.setNewPassword("123");
		
		Profile profile = new Profile();
		profile.setName(Profiles.PROFILE_REGULAR.getName());
		
		Mockito.when(profileService.findByName(Mockito.any(String.class))).thenReturn(profile);
		
		this.service.registerUser(user);
	}
	
	@Test(expected = RequiredFieldsException.class)
	public void it_should_not_register_user_duplicated_login() throws Exception {
		User user = new User();
		user.setName("user-service-test");
		user.setLogin("user-service-test-login");
		user.setEmail("user-service-test@test.com");
		user.setNewPassword("123");
		
		Profile profile = new Profile();
		profile.setName(Profiles.PROFILE_REGULAR.getName());
		
		Mockito.when(profileService.findByName(Mockito.any(String.class))).thenReturn(profile);
		
		User user2 = new User();
		user2.setId(100l);
		user2.setName("user-service-test");
		user2.setLogin("user-service-test-login");
		
		Mockito.when(repository.findByLogin(Mockito.any(String.class))).thenReturn(user2);
		
		this.service.registerUser(user);
	}
	
	@Test(expected = RequiredFieldsException.class)
	public void it_should_not_register_user_empty_email() throws Exception {
		User user = new User();
		user.setName("user-service-test");
		user.setLogin("user-service-test-login");
		user.setNewPassword("123");
		
		Profile profile = new Profile();
		profile.setName(Profiles.PROFILE_REGULAR.getName());
		
		Mockito.when(profileService.findByName(Mockito.any(String.class))).thenReturn(profile);
		
		this.service.registerUser(user);
	}
	
	@Test(expected = RequiredFieldsException.class)
	public void it_should_not_register_user_duplicated_email() throws Exception {
		User user = new User();
		user.setName("user-service-test");
		user.setLogin("user-service-test-login");
		user.setEmail("user-service-test@test.com");
		user.setNewPassword("123");
		
		Profile profile = new Profile();
		profile.setName(Profiles.PROFILE_REGULAR.getName());
		
		Mockito.when(profileService.findByName(Mockito.any(String.class))).thenReturn(profile);
		
		User user2 = new User();
		user2.setId(100l);
		user2.setName("user-service-test");
		user2.setLogin("user-service-test-login");
		user2.setEmail("user-service-test@test.com");
		
		Mockito.when(repository.findByEmail(Mockito.any(String.class))).thenReturn(user2);
		
		this.service.registerUser(user);
	}
	
	@Test(expected = RequiredFieldsException.class)
	public void it_should_not_register_user_empty_new_password() throws Exception {
		User user = new User();
		user.setName("user-service-test");
		user.setLogin("user-service-test-login");
		user.setEmail("user-service-test@test.com");
		
		Profile profile = new Profile();
		profile.setName(Profiles.PROFILE_REGULAR.getName());
		
		Mockito.when(profileService.findByName(Mockito.any(String.class))).thenReturn(profile);
		
		this.service.registerUser(user);
	}
	
	
	@Test
	public void it_should_update() throws Exception {
		User user = new User();
		user.setId(99l);
		user.setName("user-service-test");
		user.setLogin("user-service-test-login");
		user.setEmail("user-service-test@test.com");
		user.setProfile(new Profile());
		
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(user);
		
		Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(user);
		
		assertEquals(user.getId(), this.service.save(user).getId());
	}
	
	@Test(expected = RequiredFieldsException.class)
	public void it_should_update_user_not_found() throws Exception {
		User user = new User();
		user.setId(99l);
		user.setName("user-service-test");
		user.setLogin("user-service-test-login");
		user.setEmail("user-service-test@test.com");
		user.setProfile(new Profile());
		
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(null);
		
		this.service.save(user);
	}
	
	@Test(expected = RequiredFieldsException.class)
	public void it_should_not_update_empty_name() throws Exception {
		User user = new User();
		user.setId(99l);
		user.setLogin("user-service-test-login");
		user.setEmail("user-service-test@test.com");
		user.setProfile(new Profile());
		
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(user);
		
		Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(user);
		
		this.service.save(user);
	}
	
	@Test(expected = RequiredFieldsException.class)
	public void it_should_not_update_empty_login() throws Exception {
		User user = new User();
		user.setId(99l);
		user.setName("user-service-test");
		user.setEmail("user-service-test@test.com");
		user.setProfile(new Profile());
		
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(user);
		
		Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(user);
		
		this.service.save(user);
	}
	
	@Test(expected = RequiredFieldsException.class)
	public void it_should_not_update_duplicated_login() throws Exception {
		User user = new User();
		user.setId(99l);
		user.setName("user-service-test");
		user.setLogin("user-service-test-login-different");
		user.setEmail("user-service-test@test.com");
		user.setNewPassword("123");
		user.setProfile(new Profile());
		
		User currentUser = new User();
		currentUser.setId(99l);
		currentUser.setName("user-service-test");
		currentUser.setLogin("user-service-test-login");
		currentUser.setEmail("user-service-test@test.com");
		currentUser.setNewPassword("123");
		currentUser.setProfile(new Profile());
		
		User user2 = new User();
		user2.setId(100l);
		user2.setName("user-service-test");
		user2.setLogin("user-service-test-login-different");
		
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(currentUser);
		Mockito.when(repository.findByLogin(Mockito.any(String.class))).thenReturn(user2);
		
		this.service.save(user);
	}
	
	@Test(expected = RequiredFieldsException.class)
	public void it_should_not_update_empty_email() throws Exception {
		User user = new User();
		user.setId(99l);
		user.setName("user-service-test");
		user.setLogin("user-service-test-login");
		user.setProfile(new Profile());
		
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(user);
		
		Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(user);
		
		this.service.save(user);
	}
	
	@Test(expected = RequiredFieldsException.class)
	public void it_should_not_update_duplicated_email() throws Exception {
		User user = new User();
		user.setId(99l);
		user.setName("user-service-test");
		user.setLogin("user-service-test-login");
		user.setEmail("user-service-test@test.com.different");
		user.setNewPassword("123");
		user.setProfile(new Profile());
		
		User currentUser = new User();
		currentUser.setId(99l);
		currentUser.setName("user-service-test");
		currentUser.setLogin("user-service-test-login");
		currentUser.setEmail("user-service-test@test.com");
		currentUser.setNewPassword("123");
		currentUser.setProfile(new Profile());
		
		User user2 = new User();
		user2.setId(100l);
		user2.setName("user-service-test");
		user2.setLogin("user-service-test-login");
		user2.setEmail("user-service-test@test.com.different");
		
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(currentUser);
		Mockito.when(repository.findByEmail(Mockito.any(String.class))).thenReturn(user2);
		
		this.service.save(user);
	}
	
}
