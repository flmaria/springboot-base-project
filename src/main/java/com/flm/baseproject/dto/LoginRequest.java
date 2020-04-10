package com.flm.baseproject.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginRequest {
	
	public LoginRequest() {
		
	}

	public LoginRequest(String login, String password) {
		this.login = login;
		this.password = password;
	}
	
	@NotBlank
	private String login;

	@NotBlank
	private String password;

}
