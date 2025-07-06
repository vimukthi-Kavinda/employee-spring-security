package com.example.security1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.security1.config.JwtService;
import com.example.security1.dto.CustomUserDetails;
import com.example.security1.dto.UserLoginDto;
import com.example.security1.entity.User;
import com.example.security1.repo.UserReposiroty;

@Service
public class UserService {

	@Autowired
	private UserReposiroty reposiroty;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtService jwtService;
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);// this encoder for encode on saving user owd

	public User addUser(User user) {
		user.setPassword(encoder.encode(user.getPassword()));//encode and set usr pwd
		//now need to decrypt the pwd in auth provider -> coz request got plain pwd
		
		return reposiroty.save(user);
	}

	public String logIn(UserLoginDto user) {
		//calls auth manager with UN and pwd
		Authentication a= authManager.authenticate(
				new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword())  //this is (UsernamePasswordAuthenticationToken) auth object
				);
		//authenticate() calls auth provider ie -> custom auth provide impl
		//then it returns  CustomUserDetails object with user entity details
		// that CustomUserDetails is set to a.getPrincipal();
		CustomUserDetails u = (CustomUserDetails)a.getPrincipal();
		if(a.isAuthenticated()) { // if successfully authed by auth mnger-> auth provider db validation
			return jwtService.generateToken(u.getUsername()); //generate token - set subect as user name - can be id too .. but in jwt filter chain.. neet to handle user fetch from token
		}
		return "Failed";
	}
	
	
	
	
	
}
