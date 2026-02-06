package com.poc.servicemesh.registro_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class RegistroServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistroServiceApplication.class, args);
	}

}
