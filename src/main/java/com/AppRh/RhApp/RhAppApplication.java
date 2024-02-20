package com.AppRh.RhApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = "com.AppRh.RhApp.models")
@SpringBootApplication
public class RhAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(RhAppApplication.class, args);
	}

}
