	package com.application.config;

	import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

	@Component
	public class JwtUtil {

	    
	    @Value("${jwt.secret}")
	    private String secretKey;


	    @PostConstruct
	    protected void init() {
	      secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	    }

	    public Claims getAllClaimsFromToken(String token) {
	        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
	    }

	    private boolean isTokenExpired(String token) {
	        return this.getAllClaimsFromToken(token).getExpiration().before(new Date());
	    }

	    public boolean isInvalid(String token) {
	        return this.isTokenExpired(token);
	    }

	}
