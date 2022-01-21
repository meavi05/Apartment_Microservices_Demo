package com.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ApartmentGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApartmentGatewayApplication.class, args);
	}

}
