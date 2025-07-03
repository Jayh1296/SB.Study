package com.spring.study.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api")
public class Api {
	
	@Value("${app.version}")
	private String appVersion;
	
	@GetMapping("/version")
	public String getVersion() {
		return appVersion;
	}
	
	
}
