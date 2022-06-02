package com.epam.brest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.epam.brest")
public class RestUniversityApp {
	public static void main(String[] args) {SpringApplication.run(RestUniversityApp.class, args);}
}
