package com.flm.baseproject.controller;


import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flm.baseproject.BasicApplication;
import com.flm.baseproject.aux.TestMockRestClient;
import com.flm.baseproject.model.User;
import com.flm.baseproject.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BasicApplication.class)
@AutoConfigureMockMvc
public class LoginControllerIT {

	@MockBean
	private UserService userService;
	
	@Autowired
	private MockMvc mockMvc;

	private TestMockRestClient mockRestClient;
	
	private ObjectMapper mapper;
	
	@Before
	public void init() {
		this.mockRestClient = new TestMockRestClient(mockMvc);
		
		this.mapper = new ObjectMapper();
	}
	
	@Test
	public void it_should_register_user() throws Exception {
		User user = new User();
		user.setName("user-service-test");
		user.setLogin("user-service-test-login");
		user.setEmail("user-service-test@test.com");
		user.setNewPassword("123");
		
		Mockito.when(userService.registerUser(Mockito.any(User.class))).thenReturn(user);
		
		MvcResult result = mockRestClient.post("/api/login/register", mapper.writeValueAsBytes(user));

		assertTrue(Boolean.valueOf(result.getResponse().getContentAsString()));
	}
	
	@Test
	public void it_should_not_register_user_empty_name() throws Exception {
		User user = new User();
		user.setLogin("user-service-test-login");
		user.setEmail("user-service-test@test.com");
		user.setNewPassword("123");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/login/register").headers(headers).content(mapper.writeValueAsBytes(user)))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void it_should_not_register_user_empty_login() throws Exception {
		User user = new User();
		user.setName("user-service-test");
		user.setEmail("user-service-test@test.com");
		user.setNewPassword("123");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/login/register").headers(headers).content(mapper.writeValueAsBytes(user)))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void it_should_not_register_user_empty_email() throws Exception {
		User user = new User();
		user.setName("user-service-test");
		user.setLogin("user-service-test-login");
		user.setNewPassword("123");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/login/register").headers(headers).content(mapper.writeValueAsBytes(user)))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void it_should_not_register_user_invalid_email() throws Exception {
		User user = new User();
		user.setName("user-service-test");
		user.setLogin("user-service-test-login");
		user.setEmail("user-service-test");
		user.setNewPassword("123");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/login/register").headers(headers).content(mapper.writeValueAsBytes(user)))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	
	
	
	
}
