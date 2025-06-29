package com.example.security1.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
//				.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))// (dont maintain sessions cant log in via forms-> httpServletRequest.getSession().getId( forcefully create a session but dont use it id will display )
//				.build();
		
		
		
		return http.csrf(csrf->csrf.disable())//csrf disable (CSRF protection is only necessary if you're using session cookies..)   -> each line a filter in filter service?
				.authorizeHttpRequests(req->req.anyRequest())// auth all incoming request... No public endpoints unless you explicitly allow them.
				.httpBasic(Customizer.withDefaults()) // for postman like requests basic auth.. In production, you typically replace this with token-based auth
				.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//// Do not create or use HTTP sessions  — each request must be fully authenticated (stateless)
				//Each request must include full authentication (e.g., via Authorization header).
				//Spring Security does not generate a new session ID per request — it doesn't use sessions at all.
				.build();
						 
	}
	
	public UserDetailsService userDetailsService() {
		
		UserDetails usr1 = User.withDefaultPasswordEncoder()//plain  txt password access -> but should encode this
				.username("un1")
				.password("simplepwd")//plain txt coz withDefaultPasswordEncoder
				.roles("usr")
				.build();
		UserDetails usr2 = User.withDefaultPasswordEncoder()//plain  txt password access -> but should encode this
				.username("un2")
				.password("simplepwd")//plain txt coz withDefaultPasswordEncoder
				.roles("admn")
				.build();
		
		return new InMemoryUserDetailsManager(List.of(usr1,usr2));
	}
	
	

}
