package com.example.security1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.security1.dto.UserLoginDto;
import com.example.security1.entity.User;
import com.example.security1.service.UserService;
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService service;
	
	@PostMapping("/sign-in")
	public ResponseEntity<User> addUser(@RequestBody User user){
		User user1 = service.addUser(user);
		return new ResponseEntity<User>(user,HttpStatus.OK);
	
	}
	
	@PostMapping("/log-in")
	public ResponseEntity<String> logIn(@RequestBody UserLoginDto user){
		String msg = service.logIn(user);
		return new ResponseEntity<String>(msg,HttpStatus.OK);
	
	}
}
