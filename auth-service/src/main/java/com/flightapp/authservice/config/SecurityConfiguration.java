package com.flightapp.authservice.config;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.flightapp.authservice.filter.JwtRequestFilter;
import com.flightapp.authservice.util.JwtAuthenticationEntryPoint;
 
@Configuration
public class SecurityConfiguration {
 
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;
  
    @Bean
    public JwtRequestFilter getJwtRequestFilter() {
        return new JwtRequestFilter();
    }
    
    @Bean
    public JwtAuthenticationEntryPoint getJwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }
 
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationManager getAuthenticationManager() {
    	return super.authenticationManager;
    }
 
 
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.cors().and()
		.csrf().disable()
			.authorizeRequests()
				.antMatchers("/authenticate").permitAll()
				.antMatchers("/api/flight").access("hasRole('ADMIN')")
				.antMatchers("/api/airline").access("hasRole('ADMIN')")
				.antMatchers("/api/booking").access("hasRole('USER')")
				// all other requests need to be authenticated
				.anyRequest().authenticated().and().
				// make sure we use stateless session; session won't be used to
				// store user's state.
				exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Add a filter to validate the tokens with every request
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		return httpSecurity.build();
	}
 
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
    }
 
}