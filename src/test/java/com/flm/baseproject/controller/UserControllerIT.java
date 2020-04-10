package com.flm.baseproject.controller;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flm.baseproject.BasicApplication;
import com.flm.baseproject.aux.Credentials;
import com.flm.baseproject.aux.TestMockRestClient;
import com.flm.baseproject.aux.TestRestClient;
import com.flm.baseproject.enumerator.Profiles;
import com.flm.baseproject.enumerator.Roles;
import com.flm.baseproject.model.Profile;
import com.flm.baseproject.model.Role;
import com.flm.baseproject.model.User;
import com.flm.baseproject.service.UserService;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BasicApplication.class)
@AutoConfigureMockMvc
public class UserControllerIT {
	
	@MockBean
	private UserService userService;
	
	@Autowired
	private  MockMvc mockMvc;
	
	private TestMockRestClient testMockRestClient;
	
	@Autowired
    private TestRestTemplate testRestTemplate;
	
	private TestRestClient restClient;
	
	private ObjectMapper mapper;
	
	private Credentials credentials;
	
	@Before
	public void init() throws Exception {
		this.mapper = new ObjectMapper();
		
		this.restClient = new TestRestClient(testRestTemplate);
		this.testMockRestClient = new TestMockRestClient(mockMvc);
      
		//Creates a token to an admin user
		Mockito.when(userService.findByLoginOrEmail(Mockito.any(String.class))).thenReturn(this.createAdminUser());
		this.credentials = restClient.login("admin", "admin");
	}
	
	private Profile createAdminProfile() {
		Profiles adminProfile = Profiles.PROFILE_ADMIN;
		
		Profile profile = new Profile();
		profile = new Profile();
		profile.setId(99l);
		profile.setName(adminProfile.getName());
		
		long countRole = 1l;
		
		for (Enum e : adminProfile.getRoles()) {
			Roles roles = (Roles) e;
			
			Role role = new Role();
			role.setId(countRole++);
			role.setName(roles);
			role.setDescription(roles.getDescription());
			
			profile.getRoles().add(role);
		}
		
		return profile;
	}
	
	private User createAdminUser() {
		User adminUser = new User();
		adminUser.setId(99l);
		
		adminUser.setEmail("admin@test.com");
		adminUser.setName("admin");
		adminUser.setLogin("admin");
		adminUser.setPassword("$2a$10$3zA8Lb3mydaEhu56ReO.fuxrx8GeqW5k1dE/kEAIsYqXGvGeoGUCi");
		adminUser.setProfile(this.createAdminProfile());
		
		return adminUser;
	}
	
	@Test
	public void it_should_list_all_users() throws Exception {
		List<User> userList = new ArrayList<User>();
		
		User user1 = new User();
		user1.setName("regular-user-controller1");
		user1.setEmail("regular-user-controller1@mail.com");
		user1.setLogin("regular-user-controller1");
		userList.add(user1);
		
		User user2 = new User();
		user2.setName("regular-user-controller2");
		user2.setEmail("regular-user-controller2@mail.com");
		user2.setLogin("regular-user-controller2");
		userList.add(user2);
		
		Mockito.when(userService.findById(Mockito.anyLong())).thenReturn(this.createAdminUser());
		Mockito.when(userService.findAllOrderByNameAsc()).thenReturn(userList);
		
		MvcResult result = testMockRestClient.get("/users/", this.credentials);

		assertEquals(mapper.writeValueAsString(userList), result.getResponse().getContentAsString());
	}
	
	@Test
	public void it_should_find_user_by_id() throws Exception {
		User user1 = new User();
		user1.setId(1111l);
		user1.setName("regular-user-controller1");
		user1.setEmail("regular-user-controller1@mail.com");
		user1.setLogin("regular-user-controller1");
		
		Mockito.when(userService.findById(this.createAdminUser().getId())).thenReturn(this.createAdminUser());
		Mockito.when(userService.findById(user1.getId())).thenReturn(user1);

		MvcResult result = testMockRestClient.get("/users/" + user1.getId(), this.credentials);

		assertEquals(mapper.writeValueAsString(user1), result.getResponse().getContentAsString());
	}
	
	@Test
	public void it_should_create_user() throws Exception {
		User user1 = new User();
		user1.setId(1111l);
		user1.setName("regular-user-controller1");
		user1.setEmail("regular-user-controller1@mail.com");
		user1.setLogin("regular-user-controller1");
		
		Mockito.when(userService.findById(this.createAdminUser().getId())).thenReturn(this.createAdminUser());
		Mockito.when(userService.save(Mockito.any(User.class))).thenReturn(user1);

		MvcResult result = testMockRestClient.post("/users/", this.credentials, mapper.writeValueAsBytes(user1));

		assertEquals(mapper.writeValueAsString(user1), result.getResponse().getContentAsString());
	}
	
	@Test
	public void it_should_update_user() throws Exception {
		User user1 = new User();
		user1.setId(1111l);
		user1.setName("regular-user-controller1");
		user1.setEmail("regular-user-controller1@mail.com");
		user1.setLogin("regular-user-controller1");
		
		Mockito.when(userService.findById(this.createAdminUser().getId())).thenReturn(this.createAdminUser());
		Mockito.when(userService.save(Mockito.any(User.class))).thenReturn(user1);

		MvcResult result = testMockRestClient.put("/users/", this.credentials, mapper.writeValueAsBytes(user1));

		assertEquals(mapper.writeValueAsString(user1), result.getResponse().getContentAsString());
	}
	
	@Test
	public void it_should_delete_user_by_id() throws Exception {
		User user1 = new User();
		user1.setId(1111l);
		user1.setName("regular-user-controller1");
		user1.setEmail("regular-user-controller1@mail.com");
		user1.setLogin("regular-user-controller1");
		
		Mockito.when(userService.findById(this.createAdminUser().getId())).thenReturn(this.createAdminUser());
		Mockito.doNothing().when(userService).deleteById(user1.getId());

		MvcResult result = testMockRestClient.delete("/users/" + user1.getId(), this.credentials);
		assertTrue(Boolean.valueOf(result.getResponse().getContentAsString()));
	}
	
	
}