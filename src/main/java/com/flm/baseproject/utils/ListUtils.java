package com.flm.baseproject.utils;

import java.util.List;

public class ListUtils {

	public static boolean isEmpty(List<?> list) {
		if (list == null || list.isEmpty())
			return true;
		
		return false;
	}
	
	public static boolean isNotEmpty(List<?> list) {
		return !isEmpty(list);
	}
	
}
