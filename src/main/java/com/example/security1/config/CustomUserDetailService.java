package com.example.security1.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.security1.dto.CustomUserDetails;
import com.example.security1.entity.User;
import com.example.security1.repo.UserReposiroty;

//our configged user fetail service to access db and validate un pwd
@Service
public class CustomUserDetailService implements UserDetailsService {//class need to impl with UserDetailsService

	@Autowired
	private UserReposiroty userReposiroty;
	
	Logger logger = LoggerFactory.getLogger(CustomUserDetailService.class);
	
	//inside this method access db and  verify
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		logger.info("Validating {}",username);
		//get user by un
		User user = userReposiroty.findByUserName(username)
				.orElseThrow(()->new UsernameNotFoundException(username+" not found"));

		
		//create UserDetails and return -> here UserDetails is a intrface -> so create ur custom class and impl it with UserDetails
		//create custom UserDetails class - and pass user entity
		return new CustomUserDetails(user);
	}

}
