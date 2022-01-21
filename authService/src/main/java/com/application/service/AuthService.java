package com.application.service;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.application.dao.AuthRepository;
import com.application.dto.UserDTO;
import com.application.entity.Role;
import com.application.entity.UserEntity;
import com.application.exception.CustomException;
import com.application.security.JwtTokenProvider;

@Service
public class AuthService {

	@Autowired
	ModelMapper mapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	AuthRepository authRepository;

	public String register(UserDTO userData) throws CustomException {
		if (null != authRepository.getUser(userData.getEmail())) {
			throw new CustomException("User is already added.", HttpStatus.CONFLICT);
		} else {
			userData.setPassword(passwordEncoder.encode(userData.getPassword()));
			UserEntity userEntity = mapper.map(userData, UserEntity.class);
			authRepository.register(userEntity);
			return jwtTokenProvider.createToken(userData.getEmail(), new ArrayList<Role>());
		}
	}

	public String login(String email, String password) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
			return jwtTokenProvider.createToken(email, new ArrayList<Role>());
		} catch (AuthenticationException e) {
			throw new CustomException("Invalid username/password supplied", HttpStatus.UNAUTHORIZED);
		}
	}
	public String refresh(String email) {
	    return jwtTokenProvider.createToken(email, new ArrayList<Role>());
	  }
	
	public UserDTO getUserData(String email) throws CustomException {
		UserEntity userEntity = authRepository.getUser(email);
		if (userEntity == null)
			throw new CustomException("User Not Found", HttpStatus.NOT_FOUND);
		else
			return mapper.map(userEntity, UserDTO.class);
	}


}
