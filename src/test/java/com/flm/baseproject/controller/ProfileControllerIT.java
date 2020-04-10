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
import com.flm.baseproject.service.ProfileService;
import com.flm.baseproject.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BasicApplication.class)
@AutoConfigureMockMvc
public class ProfileControllerIT {
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private ProfileService profileService;
	
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
	
	private List<Profile> createAllProfiles() {
		List<Profile> profileList = new ArrayList<Profile>();
		
		for (Profiles p : Profiles.values()) {
			Profile profile = new Profile();
			profile.setName(p.getName());
			
			for (Enum e : p.getRoles()) {
				Roles roles = (Roles) e;
				
				Role role = new Role();
				role.setName(roles);
				role.setDescription(roles.getDescription());
				
				profile.getRoles().add(role);
			}
			
			profileList.add(profile);
		}
		
		return profileList;
	}
	
	@Test
	public void it_should_list_all_profiles() throws Exception {
		//required when mocking authentication
		Mockito.when(userService.findById(Mockito.anyLong())).thenReturn(this.createAdminUser());
		
		Mockito.when(this.profileService.findAll()).thenReturn(this.createAllProfiles());
		MvcResult result = testMockRestClient.get("/profiles", this.credentials);

		assertEquals(mapper.writeValueAsString(this.createAllProfiles()), result.getResponse().getContentAsString());
	}
	
}
