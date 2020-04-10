package com.flm.baseproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RequiredFieldsException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public RequiredFieldsException(String message) {
		super(message);
	}

}
