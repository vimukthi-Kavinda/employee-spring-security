package com.example.security1.config;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class JwtFilter extends OncePerRequestFilter{// for each request this need to activate - once (at first) thats why extends with OncePerRequestFilter
	//u can extract headers such as token (custom headers tenant id) and set it to a context class.. to access publically
	//for this Tokencontext.getToken() -> set need to do here Tokencontext.setToken(tkn

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		//from client side token comes with Bearer extension
		//Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI3Y2U4MTUzYi0xODBjLTQwZjctODQzYS1lZTNmMmVjOGZjZTkiLCJpYXQiOjE3NTE3OTA0ODYsImV4cCI6MTc1MTc5MDQ5MH0.76q7tx85BA7pMEjOXCzyDbdaChsLu6TZq4P_2sOKK34
		String authToken = request.getHeader("Authorization");
		String realToken = null;
		String un = null;
		if(authToken!=null && authToken.startsWith("Bearer ")) {
			realToken = authToken.substring(7);//excluse "Bearer "
			un = jwtService.extractUserNameFromToken(realToken);
		}
		
		if(un !=null &&
				SecurityContextHolder.getContext().getAuthentication() == null) //not already authenticated? if context not null its already authed
		{
			//validate un and token
			
			//un is already in db - un validate
			//validate UN  using custom user detail service// dont inject directly.. access via application context - to avoid circular dependancy
			UserDetails userDetails = applicationContext.getBean(CustomUserDetailService.class)//get bean
			.loadUserByUsername(un);// call validate method
			
			//token validate
			boolean isValidated = jwtService.validateToken(realToken,userDetails);
			if(isValidated) {
			//now un pwd auth filter call
				
				//pass un-pwd-auth token for that filter - with user details
				UsernamePasswordAuthenticationToken authenticationToken =
						new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				
				//need to set all the details of request too 
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				//now set token to context - SecurityContextHolder
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
			
		}
		
		//now go to next filter
		filterChain.doFilter(request, response);
		
	} 

}
