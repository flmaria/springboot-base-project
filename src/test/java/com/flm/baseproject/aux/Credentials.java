package com.flm.baseproject.aux;

public class Credentials {

	public Credentials(String token) {
		this.token = token;
	}
	
	private String token;
	
	public String getToken() {
		return "Bearer " + this.token;
	}
	
	
}
