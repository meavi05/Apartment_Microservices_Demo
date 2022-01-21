/**
 * 
 */
package com.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.dto.UserDTO;
import com.application.exception.CustomException;
import com.application.service.AuthService;


/**
 * @author avi08
 *
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	AuthService authService;

	@RequestMapping(value = "/welcome")
	public ResponseEntity<String> health() {
		return new ResponseEntity<String>("Welcome to the Auth Service", HttpStatus.OK);

	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<String> login( @RequestParam String email, @RequestParam String password) {
		 String jwtToken =  authService.login(email, password);
		return new ResponseEntity<String>(jwtToken, HttpStatus.OK);
	}

	@RequestMapping(value = "/refresh", method = RequestMethod.GET)
	  public String refresh(@RequestParam String email) {
	    return authService.refresh(email);
	  } 
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<String> register(@RequestBody UserDTO userData) throws CustomException {
		String jwtToken = authService.register(userData);
		return new ResponseEntity<String>(jwtToken, HttpStatus.OK);
	}
	@RequestMapping(value = "/getUserData/{email}", method = RequestMethod.GET)
	public ResponseEntity<UserDTO> getUserData(@PathVariable("email") String email) throws CustomException {
		UserDTO userDto = authService.getUserData(email);
		return new ResponseEntity<UserDTO>(userDto, HttpStatus.OK);
	}

}
