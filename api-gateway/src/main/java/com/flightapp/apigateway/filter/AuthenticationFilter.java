package com.flightapp.apigateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.flightapp.apigateway.util.JwtUtil;
import com.flightapp.apigateway.util.RouterValidator;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

@RefreshScope
@Component
public class AuthenticationFilter implements GatewayFilter {

//    @Autowired
//    private RouterValidator routerValidator;//custom route validator
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();

		if (RouterValidator.isGetSecured(request)) {
			if (this.isAuthMissing(request))
				return this.onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);

			final String requestTokenHeader = this.getAuthHeader(request);
			String token = requestTokenHeader.substring(7);

			try {
			if (jwtUtil.isInvalid(token))
				return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
			}catch(Exception e ) {
				return this.onError(exchange, "Token is invalid", HttpStatus.UNAUTHORIZED);
			}
			this.populateRequestWithHeaders(exchange, token);
		}
		return chain.filter(exchange);
	}

	/* PRIVATE */

	private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(httpStatus);
		return response.setComplete();
	}

	private String getAuthHeader(ServerHttpRequest request) {
		return request.getHeaders().getOrEmpty("Authorization").get(0);
	}

	private boolean isAuthMissing(ServerHttpRequest request) {
		return !request.getHeaders().containsKey("Authorization");
	}

	private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
		Claims claims = jwtUtil.getAllClaimsFromToken(token);
		exchange.getRequest().mutate().header("username", String.valueOf(claims.get("username")))
				.header("role", String.valueOf(claims.get("role"))).build();
	}
}