package com.example.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity        // dont use default use this
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		//return http.build(); // no filter applied
		
		//if we applied only these -> only app.prop file credentials (UN PWD) used
//		spring.security.user.name=vkka
//				spring.security.user.password=1111
//		return http.csrf(csrf->csrf.disable())//csrf disable
//				.authorizeHttpRequests(req->req.anyRequest())// auth all incoming request
//				.httpBasic(Customizer.withDefaults()) // for postman like requests
//				.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))// new session id per each req
//				.build();
		
		
		
		return http.csrf(csrf->csrf.disable())//csrf disable   -> each line a filter in filter service?
				.authorizeHttpRequests(req->req.anyRequest().authenticated())// auth all incoming request
				.httpBasic(Customizer.withDefaults()) // for postman like requests
				.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))// new session id per each req
				.build();
						
	}

}
