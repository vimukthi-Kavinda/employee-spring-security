package com.example.security1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.security1.entity.User;
import com.example.security1.repo.UserReposiroty;

@Service
public class UserService {

	@Autowired
	private UserReposiroty reposiroty;
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);// this encoder for encode on saving user owd

	public User addUser(User user) {
		user.setPassword(encoder.encode(user.getPassword()));//encode and set usr pwd
		//now need to decrypt the pwd in auth provider -> coz request got plain pwd
		
		return reposiroty.save(user);
	}
	
	
	
	
	
}
