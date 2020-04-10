package com.flm.baseproject.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class ErrorFieldDTO {

	private final String field;
	
	private final String message;
}
