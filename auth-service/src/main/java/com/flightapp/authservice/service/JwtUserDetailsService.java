package com.flightapp.authservice.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.flightapp.authservice.dto.AuthRequest;
import com.flightapp.authservice.dto.AuthResponse;
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
	
	public AuthResponse saveUserDetails(AuthRequest authRequest) {
        //do validation if user already exists
        authRequest.setPassword(bCryptPasswordEncoder
                .encode(authRequest.getPassword()));

        UserRoleDetails user = userRepository.save(mapToModel(authRequest));
        UserDetails userDetails =new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				Arrays.asList(new SimpleGrantedAuthority(user.getRole())));
        String accessToken = jwtUtil.generateToken(userDetails);

        return new AuthResponse(accessToken);
		
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
