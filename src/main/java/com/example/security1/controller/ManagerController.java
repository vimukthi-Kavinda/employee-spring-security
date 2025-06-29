package com.example.security1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.security1.dto.Manager;
import com.example.security1.service.ManagerService;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/manager")
public class ManagerController {

	public ManagerController(ManagerService managerService) {
		this.managerService = managerService;
	}

	private final ManagerService managerService;
	
	
	 
	
	@GetMapping("/get-all")
	public ResponseEntity<List<Manager>> getManagers(){
		return new ResponseEntity<List<Manager>>(managerService.getEmployees(),HttpStatus.OK);
	
	}
	
	//csrf
		//csrf token - changing session id over time
		//csrf effect on post ,put ,delete
	
	@PostMapping("/add-all")
	public ResponseEntity<String> addManagers(@RequestBody List<Manager> managers){
		managerService.addManagers(managers);
		return new ResponseEntity<String>("Successfully saved",HttpStatus.OK);
	
	}
	
	
	//X-CSRF-TOKEN   --> set this key in headers of request and set the token given bu this method as value
	@GetMapping("/csrf-tkn")
	public ResponseEntity<CsrfToken> greet(HttpServletRequest httpServletRequest){
		return new ResponseEntity<CsrfToken>(
				(CsrfToken)httpServletRequest.getAttribute("_csrf")//go to login page and find _csrf--> thats the key-- access value here //<input name="_csrf" type="hidden" value="RTfUcQ-YnBDM-WU0uVap0I1YJsMyMZXm3CW3acrGn1WkEeoiJgPmRG6h_3Phz1wB2nud5OtgC_tWAKbLuBzUXfP-rDPActIQ" />
				,HttpStatus.OK);
	}
	
	
	//config ur own filter chains.. inspead of spring security giving
	
	
	
	
}
