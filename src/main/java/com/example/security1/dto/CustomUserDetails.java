package com.example.security1.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.security1.entity.User;

public class CustomUserDetails implements UserDetails {
	
	private User user;

	public CustomUserDetails(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() { //roles
		// roles return - get from another tble i guess
		return List.of(new SimpleGrantedAuthority("USER"));
	}

	@Override
	public String getPassword() {
		// set pwd from user entity
		return user.getPassword();
	}
	
	public String getUserId() {
		// set un from user entity
		return user.getId().toString();
	}

	@Override
	public String getUsername() {
		// set un from user entity
		return user.getUserName();
	}
	
	//optional
	@Override
	public boolean isEnabled() {
		// is user account enabled
		return true;// set default rue
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}


	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}



}
