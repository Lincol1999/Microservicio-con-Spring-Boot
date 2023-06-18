package com.spring.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MotorbikeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MotorbikeServiceApplication.class, args);
	}

}
