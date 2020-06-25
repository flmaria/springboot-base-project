package com.flm.baseproject.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flm.baseproject.dto.LoginRequest;
import com.flm.baseproject.dto.LoginResponse;
import com.flm.baseproject.model.User;
import com.flm.baseproject.security.JwtTokenProvider;
import com.flm.baseproject.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/login")
public class LoginController {

	private final AuthenticationManager authenticationManager;

	private final UserService userService;
	
	private final JwtTokenProvider tokenProvider;

	@PostMapping()
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new LoginResponse(jwt));
	}

	@PostMapping("/register")
	public ResponseEntity<Boolean> register(@Validated @RequestBody User user) {
		userService.registerUser(user);
		return ResponseEntity.ok(Boolean.TRUE);
	}

}