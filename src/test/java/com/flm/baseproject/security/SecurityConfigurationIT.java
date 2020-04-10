package com.flm.baseproject.security;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flm.baseproject.BasicApplication;
import com.flm.baseproject.aux.TestRestClient;
import com.flm.baseproject.dto.LoginResponse;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BasicApplication.class)
public class SecurityConfigurationIT {

	@Resource
    private TestRestTemplate rest;
	
	ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
    private TestRestTemplate testRestTemplate;
	
	private TestRestClient restClient;
	
	@Before
	public void init() {
		this.restClient = new TestRestClient(testRestTemplate);
	}
	
	
	@Test
    public void it_should_authenticate_an_existing_user() throws Exception {
		ResponseEntity<LoginResponse> response = this.restClient.loginWithResponse("admin", "admin");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		LoginResponse loginResponse = (LoginResponse) response.getBody();
		assertNotNull(loginResponse.getToken());
	}
	
	
	
	
	
}
