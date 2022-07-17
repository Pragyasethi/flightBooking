package com.flightapp.commonmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.flightapp"})
public class CommonServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(CommonServiceApplication.class, args);
	}

}
