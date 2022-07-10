//package com.flightapp.apigateway.filter;
//
//import java.util.List;
//import java.util.function.Predicate;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//
//import com.flightapp.apigateway.util.JwtUtil;
//
//import io.jsonwebtoken.Claims;
//import reactor.core.publisher.Mono;
//
//@Component
//public class JwtAuthenticationFilter implements GatewayFilter {
//
//	@Autowired
//	private JwtUtil jwtUtil;
//
//	@Override
//	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//		ServerHttpRequest request = exchange.getRequest();
//		final List<String> apiEndpoints = List.of("/api/auth/register", "/api/auth/login");
//
//		Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
//				.noneMatch(uri -> r.getURI().getPath().contains(uri));
//		if (isApiSecured.test(request)) {
//			if (!request.getHeaders().containsKey("Authorization")) {
//				ServerHttpResponse response = exchange.getResponse();
//				response.setStatusCode(HttpStatus.UNAUTHORIZED);
//
//				return response.setComplete();
//			}
//
//			final String token = request.getHeaders().getOrEmpty("Authorization").get(0);
//
//			jwtUtil.validateToken(token);
//
//			Claims claims = jwtUtil.getClaims(token);
//			exchange.getRequest().mutate().header("username", String.valueOf(claims.get("username")))
//					.header("password", String.valueOf(claims.get("password"))).build();
//		}
//
//		return chain.filter(exchange);
//	}
//
//}