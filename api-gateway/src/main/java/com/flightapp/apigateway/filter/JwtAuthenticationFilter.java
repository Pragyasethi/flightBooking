//package com.flightapp.apigateway.filter;
//
//import java.util.List;
//import java.util.function.Predicate;
//
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//
//import com.flightapp.apigateway.util.JwtUtil;
//
//import io.jsonwebtoken.Claims;
//import lombok.RequiredArgsConstructor;
//import reactor.core.publisher.Mono;
//
//@Component
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter implements GatewayFilter {
//
//	private final JwtUtil jwtUtil;
//
//	@Override
//	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//		ServerHttpRequest request = (ServerHttpRequest) exchange.getRequest();
//		final List<String> apiEndPoints = List.of("/register", "/login");
//		Predicate<ServerHttpRequest> isApiSecured = route -> apiEndPoints.stream()
//				.noneMatch(uri -> route.getURI().getPath().contains(uri));
//		if (isApiSecured.test(request)) {
//			if (!request.getHeaders().containsKey("Authorization")) {
//				ServerHttpResponse response = exchange.getResponse();
//				response.setStatusCode(HttpStatus.UNAUTHORIZED);
//				return response.setComplete();
//
//			}
//			final String token = request.getHeaders().getOrEmpty("Authorization").get(0);
//			//jwtUtil.validateToken(token, userDetails);
//			//Claims claims = jwtUtil.getClaims(token);
//		//	exchange.getRequest().mutate().header("id", String.valueOf(claims.get("id"))).build();
//		}
//		return chain.filter(exchange);
//	}
//
//}
