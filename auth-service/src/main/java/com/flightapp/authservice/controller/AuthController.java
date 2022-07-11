package com.flightapp.authservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.authservice.dto.AuthRequest;
import com.flightapp.authservice.dto.AuthResponse;
import com.flightapp.authservice.service.JwtUserDetailsService;
import com.flightapp.authservice.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {
	
	private  final JwtUtil jwtUtil;

	private final JwtUserDetailsService userDetailsService;

	private final AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authenticationRequest) {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		String token = jwtUtil.generateToken(userDetails);

		return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest authenticationRequest) {
		//userDetailsService.saveUserDetails(authenticationRequest);
		System.out.println("Info saved...");

		return ResponseEntity.ok(userDetailsService.saveUserDetails(authenticationRequest));
	}

	private void authenticate(String username, String password) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}

}
