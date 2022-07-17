//package com.flightapp.apigateway.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class ApiGatewayPropertiesConfig {
//
//	@Value("${server.port:}")
//	private static String port;
//	@Value("${eureka.client.serviceUrl.defaultZone}")
//	private static String eurekaDefaultZone;
//	@Value("${jwt.secret}")
//	private static String jwtSecret;
//	@Value("${jwt.token.validity}")
//	private static String jwtValidity;
//	@Value("${spring.application.name}")
//	private static String applicationName;
//	@Value("${logging.level.root}")
//	private static String loggingLevelRoot;
//	@Value("${logging.level.org.springframework.cloud.gateway}")
//	private static String loggingLevelCloudGateway;
//	@Value("${logging.level.org.springframework.cloud.gateway.route.RouteDefinitionLocator}")
//	private static String loggingLevelCloudGatewayRouteDefinitionLocator;
//	@Value("${spring.cloud.gateway.default-filters[0]}")
//	private static String cloudGatewayDefaultFilter;
//
//	public static String getPort() {
//		return port;
//	}
//
//	public void setPort(String port) {
//		ApiGatewayPropertiesConfig.port = port;
//	}
//
//	public static String getEurekaDefaultZone() {
//		return eurekaDefaultZone;
//	}
//
//	public void setEurekaDefaultZone(String eurekaDefaultZone) {
//		ApiGatewayPropertiesConfig.eurekaDefaultZone = eurekaDefaultZone;
//	}
//
//	public static String getJwtSecret() {
//		return jwtSecret;
//	}
//
//	public void setJwtSecret(String jwtSecret) {
//		ApiGatewayPropertiesConfig.jwtSecret = jwtSecret;
//	}
//
//	public static String getJwtValidity() {
//		return jwtValidity;
//	}
//
//	public void setJwtValidity(String jwtValidity) {
//		ApiGatewayPropertiesConfig.jwtValidity = jwtValidity;
//	}
//
//	public static String getApplicationName() {
//		return applicationName;
//	}
//
//	public void setApplicationName(String applicationName) {
//		ApiGatewayPropertiesConfig.applicationName = applicationName;
//	}
//
//	public static String getLoggingLevelRoot() {
//		return loggingLevelRoot;
//	}
//
//	public void setLoggingLevelRoot(String loggingLevelRoot) {
//		ApiGatewayPropertiesConfig.loggingLevelRoot = loggingLevelRoot;
//	}
//
//	public static String getLoggingLevelCloudGateway() {
//		return loggingLevelCloudGateway;
//	}
//
//	public void setLoggingLevelCloudGateway(String loggingLevelCloudGateway) {
//		ApiGatewayPropertiesConfig.loggingLevelCloudGateway = loggingLevelCloudGateway;
//	}
//
//	public static String getLoggingLevelCloudGatewayRouteDefinitionLocator() {
//		return loggingLevelCloudGatewayRouteDefinitionLocator;
//	}
//
//	public void setLoggingLevelCloudGatewayRouteDefinitionLocator(
//			String loggingLevelCloudGatewayRouteDefinitionLocator) {
//		ApiGatewayPropertiesConfig.loggingLevelCloudGatewayRouteDefinitionLocator = loggingLevelCloudGatewayRouteDefinitionLocator;
//	}
//
//	public static String getCloudGatewayDefaultFilter() {
//		return cloudGatewayDefaultFilter;
//	}
//
//	public void setCloudGatewayDefaultFilter(String cloudGatewayDefaultFilter) {
//		ApiGatewayPropertiesConfig.cloudGatewayDefaultFilter = cloudGatewayDefaultFilter;
//	}
//
//}
