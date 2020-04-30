package com.flm.baseproject.aux;


import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flm.baseproject.dto.LoginRequest;
import com.flm.baseproject.dto.LoginResponse;

public class TestRestClient {

	private TestRestTemplate testRestTemplate;
	
	public TestRestClient(TestRestTemplate testRestTemplate) {
        this.testRestTemplate = testRestTemplate;
	}
	
	public <T> ResponseEntity<T> get(String restPath, Credentials credentials, Class<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, credentials.getToken());

        return testRestTemplate.exchange(restPath, HttpMethod.GET, new HttpEntity<>(headers), responseType);
    }
	
	public <T> ResponseEntity<T> post(String restPath, Credentials credentials, Object body, Class<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        
        if (credentials != null)
        	headers.add(HttpHeaders.AUTHORIZATION, credentials.getToken());
        
        return testRestTemplate.exchange(restPath, HttpMethod.POST, new HttpEntity<>(body, headers), responseType);
    }
	
	public Credentials login(String login, String password) throws Exception {
		ResponseEntity<LoginResponse> response = this.loginWithResponse(login, password);
		LoginResponse loginResponse = (LoginResponse) response.getBody();
		
		return new Credentials(loginResponse.getToken());
	}
	
	public ResponseEntity<LoginResponse> loginWithResponse(String login, String password) throws Exception {
		LoginRequest loginRequest = new LoginRequest(login, password);
        
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<String> request = new HttpEntity(new ObjectMapper().writeValueAsBytes(loginRequest), headers);
		
        return testRestTemplate.postForEntity("/api/login", request, LoginResponse.class);
	}
	
}
