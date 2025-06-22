package com.example.security1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController("/employee")
public class EmployeeController {

	
	//with security it pass session id with response -> after success login till logout
	@GetMapping("")
	public ResponseEntity<String> greet(HttpServletRequest httpServletRequest){
		return new ResponseEntity<String>("Hi.. ur session ID is "+httpServletRequest.getSession().getId(),HttpStatus.OK);
	}
	
	
	
}
