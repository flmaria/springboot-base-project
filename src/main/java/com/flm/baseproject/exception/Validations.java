package com.flm.baseproject.exception;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flm.baseproject.dto.ErrorFieldDTO;
import com.flm.baseproject.utils.StringUtils;


public class Validations {
	
	private List<ErrorFieldDTO> errorList;
	
	public Validations() {
		errorList = new ArrayList<ErrorFieldDTO>();
	}
	
	public void add(String field, String message) {
		if (StringUtils.isEmpty(field)) {
			this.errorList.add(new ErrorFieldDTO("field", "field cannot be empty."));
			this.throwsExceptions();
		}
			
		if (StringUtils.isEmpty(message)) { 
			this.errorList.add(new ErrorFieldDTO("message", "message cannot be empty"));
			this.throwsExceptions();
		}
		
		this.errorList.add(new ErrorFieldDTO(field, message));
	}
	
	public void throwsExceptions() {
		if (errorList.size() > 0) {
			ObjectMapper mapper = new ObjectMapper();

			try {
				throw new RequiredFieldsException(mapper.writeValueAsString(errorList));
				
			}
			catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}

}
