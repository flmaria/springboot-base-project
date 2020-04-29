package com.flm.baseproject.dto;

import java.util.List;

import lombok.Data;

@Data
public class PageResult<T> {
	
	private final List<T> data;
	
	private final long total;

}
