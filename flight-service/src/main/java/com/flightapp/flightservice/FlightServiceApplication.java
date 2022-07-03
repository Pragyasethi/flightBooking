package com.flightapp.flightservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan({"com.flightapp"})
public class FlightServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(FlightServiceApplication.class, args);
	}

}
