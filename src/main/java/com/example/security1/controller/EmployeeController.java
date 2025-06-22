package com.example.security1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/employee")
public class EmployeeController {

	@GetMapping("")
	public ResponseEntity<String> greet(){
		return new ResponseEntity<String>("Hi",HttpStatus.OK);
	}
	
	
}
