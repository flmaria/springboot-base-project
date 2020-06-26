package com.flm.baseproject.utils;

import java.util.List;

public class ListUtils {

	public static boolean isEmpty(List<? extends Object> list) {
		if (list == null || list.isEmpty())
			return true;
		
		return false;
	}
	
	public static boolean isNotEmpty(List<? extends Object> list) {
		return !isEmpty(list);
	}
	
}
