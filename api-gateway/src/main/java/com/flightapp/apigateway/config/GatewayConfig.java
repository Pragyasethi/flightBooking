package com.flightapp.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.flightapp.apigateway.filter.AuthenticationFilter;

@Configuration
public class GatewayConfig {


	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder,AuthenticationFilter filter) {
		return builder.routes()
				.route("auth-service",r ->r.path("/api/auth/**")
						.filters(f -> f.filter(filter)).uri("lb://auth-service"))
				.route("booking-service",r ->r.path("/api/booking/**")
						.filters(f -> f.filter(filter)).uri("lb://booking-service"))
				.route("inventory-service",r ->r.path("/api/inventory/**")
						.filters(f -> f.filter(filter)).uri("lb://inventory-service"))
				.route("flight-service",r ->r.path("/api/flight/**","/api/airline/**","/api/airport/**")
						.filters(f -> f.filter(filter)).uri("lb://flight-service"))

				.build();
	}

}
