package com.flightapp.apigateway.util;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouterValidator {
	private RouterValidator() {
	}

	public static final List<String> openApiEndpoints = List.of("/api/auth/", "/api/booking"

	);

	public static final Predicate<ServerHttpRequest> isSecured = request -> openApiEndpoints.stream()
			.noneMatch(uri -> request.getURI().getPath().contains(uri));

}
