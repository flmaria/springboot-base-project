package com.flm.baseproject.utils;

public class StringUtils {

	public static boolean isEmpty(String value) {
		if (value == null || value.trim().equals(""))
			return true;
		
		return false;
	}
	
	public static boolean isNotEmpty(String value) {
		return !isEmpty(value);
	}
	
}
