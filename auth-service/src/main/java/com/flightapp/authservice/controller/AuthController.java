package com.flightapp.authservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.authservice.dto.JwtRequest;
import com.flightapp.authservice.service.JwtUserDetailsService;
import com.flightapp.authservice.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

	private final JwtUtil jwtUtil;
	
	private final JwtUserDetailsService userDetailsService;
	
	private final AuthenticationManager authenticationManager;



	@PostMapping(path = "authlogin")
	public ResponseEntity<String> login(@RequestBody JwtRequest authenticationRequest) {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		String token = jwtUtil.generateToken(userDetails);
		return new ResponseEntity<>(token, HttpStatus.OK);
	}

	@PostMapping(path = "authregister")
	public ResponseEntity<String> register(@RequestBody String username) {
		return new ResponseEntity<>("Registered", HttpStatus.OK);
	}

	private void authenticate(String username, String password) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}

}
