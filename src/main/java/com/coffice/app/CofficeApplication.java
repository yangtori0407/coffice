package com.coffice.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class CofficeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CofficeApplication.class, args);
	}

}
