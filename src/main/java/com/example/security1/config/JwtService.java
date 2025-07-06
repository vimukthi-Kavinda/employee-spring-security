package com.example.security1.config;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	String secret = null;
	
	public String generateToken(String userName) {
		
		Map<String ,Object> claims = new HashMap<String, Object>();
		
		return Jwts.builder()
				.claims()
				.add(claims)
				.subject(userName)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+60*60))
				.and()
				.signWith(getKey())
				.compact();
	}

	public JwtService() throws NoSuchAlgorithmException { //generate a secret key using javax crypto on Jwt service init
		KeyGenerator generator = KeyGenerator.getInstance("HmacSHA256");//algos in method definition . try ctrl+click in method
		SecretKey sk  = generator.generateKey();
		secret = Base64.getEncoder().encodeToString(sk.getEncoded()); //encode to string
	}

	private Key getKey() {
	
		
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)); // decoding to byte[]
	
	}

}
