package com.flightapp.authservice.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.flightapp.authservice.dto.JwtRequest;
import com.flightapp.authservice.model.UserRoleDetails;
import com.flightapp.authservice.repository.UserRoleDetailsRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService{
	@Autowired
	UserRoleDetailsRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserRoleDetails user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
		
		return  new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				Arrays.asList(new SimpleGrantedAuthority(user.getRole())));

	}
	
	public void saveUserDetails(JwtRequest jwtRequest) {
		userRepository.save(mapToModel(jwtRequest));
		
	}
	
	private UserRoleDetails mapToModel(JwtRequest jwtRequest) {
		return UserRoleDetails.builder()
				.username(jwtRequest.getUsername())
				.password(jwtRequest.getPassword())
				.email(jwtRequest.getEmail())
				.role("USER")
				.build();
	}
}
