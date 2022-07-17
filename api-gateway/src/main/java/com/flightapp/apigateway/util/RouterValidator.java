package com.flightapp.apigateway.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouterValidator {
	private RouterValidator() {
	}

	public static final List<String> openApiEndpoints = List.of("/api/auth/", "/api/booking", "/swagger-resources/**",
			"/swagger-ui.html**", "/webjars/**", "favicon.ico"

	);

	protected static Map<String, String> openApisMap;
	static {
		openApisMap = new HashMap<>();
		openApisMap.put("/api/auth/", "POST");
		openApisMap.put("/api/booking", "POST,GET,PUT,DELETE");
		openApisMap.put("/api/flight", "GET");
		openApisMap.put("/api/airport", "GET");
		openApisMap.put("/api/airline", "GET");
	}

	public static final Predicate<ServerHttpRequest> isSecured = request -> openApiEndpoints.stream()
			.noneMatch(uri -> request.getURI().getPath().contains(uri));

	public static boolean isGetSecured(ServerHttpRequest request) {
		for (Map.Entry<String, String> entry : openApisMap.entrySet()) {
			if (request.getURI().getPath().contains(entry.getKey())
					&& entry.getValue().contains(request.getMethodValue())) {
				return false;
			}
		}
		return true;
	}
}
