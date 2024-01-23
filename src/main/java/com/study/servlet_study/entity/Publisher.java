package com.study.servlet_study.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class Publisher {
	private int publisherId;
	private String publisherName;
	
}
