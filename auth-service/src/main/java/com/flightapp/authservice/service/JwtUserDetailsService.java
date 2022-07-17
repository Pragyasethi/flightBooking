package com.flightapp.authservice.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.flightapp.authservice.dto.AuthRequest;
import com.flightapp.authservice.dto.MessageResponse;
import com.flightapp.authservice.model.UserRoleDetails;
import com.flightapp.authservice.repository.UserRoleDetailsRepository;
import com.flightapp.authservice.util.JwtUtil;

@Service
public class JwtUserDetailsService implements UserDetailsService{
	@Autowired
	UserRoleDetailsRepository userRepository;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserRoleDetails user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
		
		return  new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				Arrays.asList(new SimpleGrantedAuthority(user.getRole())));

	}
	
	public ResponseEntity<?> saveUserDetails(AuthRequest authRequest) {
        //do validation if user already exists
		if (Boolean.TRUE.equals(userRepository.existsByUsername(authRequest.getUsername()))) {
		      return ResponseEntity
		          .badRequest()
		          .body(new MessageResponse("Error: Username is already taken!"));
		    }

		    if (userRepository.existsByEmail(authRequest.getEmail())) {
		      return ResponseEntity
		          .badRequest()
		          .body(new MessageResponse("Error: Email is already in use!"));
		    }
        authRequest.setPassword(bCryptPasswordEncoder
                .encode(authRequest.getPassword()));

        userRepository.save(mapToModel(authRequest));
//        UserDetails userDetails =new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
//				Arrays.asList(new SimpleGrantedAuthority(user.getRole())));
//        String accessToken = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
		
	}
	
	private UserRoleDetails mapToModel(AuthRequest jwtRequest) {
		return UserRoleDetails.builder()
				.username(jwtRequest.getUsername())
				.password(jwtRequest.getPassword())
				.email(jwtRequest.getEmail())
				.role("ROLE_USER")
				.build();
	}
}
