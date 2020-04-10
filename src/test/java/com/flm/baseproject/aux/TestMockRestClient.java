package com.flm.baseproject.aux;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class TestMockRestClient {

	private MockMvc mockMvc;
	
	public TestMockRestClient(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }
	
	public MvcResult get(String restPath) throws Exception {
        return this.get(restPath, null, null);
	}
	
	public MvcResult get(String restPath, byte[] body) throws Exception {
        return this.get(restPath, null, body);
	}
	
	public MvcResult get(String restPath, Credentials credentials) throws Exception {
        return this.get(restPath, credentials, null);
	}
	
	public MvcResult get(String restPath, Credentials credentials, byte[] body) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		if (credentials != null)
        	headers.add(HttpHeaders.AUTHORIZATION, credentials.getToken());
        
		if (body == null)
			return mockMvc.perform(MockMvcRequestBuilders.get(restPath).headers(headers)).andReturn();
		
        return mockMvc.perform(MockMvcRequestBuilders.get(restPath).headers(headers).content(body)).andReturn();
    }
	
	public MvcResult post(String restPath) throws Exception {
        return this.post(restPath, null, null);
	}
	
	public MvcResult post(String restPath, byte[] body) throws Exception {
        return this.post(restPath, null, body);
	}
	
	public MvcResult post(String restPath, Credentials credentials) throws Exception {
        return this.post(restPath, credentials, null);
	}
	
	public MvcResult post(String restPath, Credentials credentials, byte[] body) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		if (credentials != null)
        	headers.add(HttpHeaders.AUTHORIZATION, credentials.getToken());
        
		if (body == null)
			return mockMvc.perform(MockMvcRequestBuilders.post(restPath).headers(headers)).andReturn();
		
        return mockMvc.perform(MockMvcRequestBuilders.post(restPath).headers(headers).content(body)).andReturn();
	}
	
	public MvcResult put(String restPath, Credentials credentials, byte[] body) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		if (credentials != null)
        	headers.add(HttpHeaders.AUTHORIZATION, credentials.getToken());
        
		if (body == null)
			return mockMvc.perform(MockMvcRequestBuilders.put(restPath).headers(headers)).andReturn();
		
        return mockMvc.perform(MockMvcRequestBuilders.put(restPath).headers(headers).content(body)).andReturn();
    }
	
	public MvcResult delete(String restPath, Credentials credentials) throws Exception {
        return this.delete(restPath, credentials, null);
	}
	
	public MvcResult delete(String restPath, Credentials credentials, byte[] body) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		if (credentials != null)
        	headers.add(HttpHeaders.AUTHORIZATION, credentials.getToken());
        
		if (body == null)
			return mockMvc.perform(MockMvcRequestBuilders.delete(restPath).headers(headers)).andReturn();
		
        return mockMvc.perform(MockMvcRequestBuilders.delete(restPath).headers(headers).content(body)).andReturn();
    }
	
}
