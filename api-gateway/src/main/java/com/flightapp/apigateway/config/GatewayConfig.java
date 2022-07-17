package com.flightapp.apigateway.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import com.flightapp.apigateway.filter.AuthenticationFilter;

@Configuration
public class GatewayConfig {


	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder,AuthenticationFilter filter) {
		return builder.routes()
				.route("auth-service",r ->r.path("/api/auth/**")
						.filters(f -> f.filter(filter).dedupeResponseHeader("Access-Control-Allow-Origin", "RETAIN_LAST")).uri("lb://auth-service"))
				.route("booking-service",r ->r.path("/api/booking/**")
						.filters(f -> f.filter(filter).dedupeResponseHeader("Access-Control-Allow-Origin", "RETAIN_LAST")).uri("lb://booking-service"))
				.route("inventory-service",r ->r.path("/api/inventory/**")
						.filters(f -> f.filter(filter).dedupeResponseHeader("Access-Control-Allow-Origin", "RETAIN_LAST")).uri("lb://inventory-service"))
				.route("flight-service",r ->r.path("/api/flight/**","/api/airline/**","/api/airport/**")
						.filters(f -> f.filter(filter).dedupeResponseHeader("Access-Control-Allow-Origin", "RETAIN_LAST")).uri("lb://flight-service"))
				.route("api-gateway",r ->r.path("/api/api-gateway/**")
						.filters(f -> f.filter(filter).dedupeResponseHeader("Access-Control-Allow-Origin", "RETAIN_LAST")).uri("lb://api-gateway"))
				.build();
	}
	
	    @Bean
	    public CorsWebFilter corsWebFilter() {

	        final CorsConfiguration corsConfig = new CorsConfiguration();
	        corsConfig.setAllowedOrigins(Collections.singletonList("*"));
	        corsConfig.setMaxAge(3600L);
	        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST","PUT","DELETE"));
	        corsConfig.addAllowedHeader("*");

	        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", corsConfig);

	        return new CorsWebFilter(source);
	    }  
	

}
