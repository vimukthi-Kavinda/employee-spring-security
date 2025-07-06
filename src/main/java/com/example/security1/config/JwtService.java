package com.example.security1.config;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
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
				.expiration(new Date(System.currentTimeMillis()+1 * 60 * 1000))// 1 min expire
				.and()
				.signWith(getKey())
				.compact();
	}

	public JwtService() throws NoSuchAlgorithmException { //generate a secret key using javax crypto on Jwt service init
		KeyGenerator generator = KeyGenerator.getInstance("HmacSHA256");//algos in method definition . try ctrl+click in method
		SecretKey sk  = generator.generateKey();
		secret = Base64.getEncoder().encodeToString(sk.getEncoded()); //encode to string
	}

	private SecretKey getKey() {
	
		
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)); // decoding to byte[]
	
	}

	public String extractUserNameFromToken(String token) {// technically extractSubjectFromToken - subject normally UN
		// un is among claims - so extract it
		String extracted = extractClaim(token,Claims::getSubject);// we are getting subject of token - normally it woud be UN - but  we set user id as subject in token generation - then haNdle in jwt filter - accessing usre repo
		return extracted;
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimResolver) { // extract given feature(getSubject,getExpiration)/property from token - 
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);//claimResolver is the function - given function apply to all claims and fetch right one
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(getKey())
				.build().parseSignedClaims(token).getPayload();
	}

	public boolean validateToken(String realToken, UserDetails userDetails) {
		String un = extractUserNameFromToken(realToken);
		boolean userNameEquals = un.equals(userDetails.getUsername());
		boolean isTokenExpired = isTokenExpired(realToken);
		return userNameEquals && !isTokenExpired;
	}

	private boolean isTokenExpired(String realToken) {
		return extractExpiration(realToken).before(new Date()); // is expiring date before today?
	}

	private Date extractExpiration(String realToken) {
		return extractClaim(realToken, Claims::getExpiration); // extract expire date from token
	}


}
