package com.project.reddital_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
//@RestController
public class RedditalBackendApplication {

	//@RequestMapping("/helloWorld")
	public String helloWorld() {
		return "Hello World!";
	}

	public static void main(String[] args) {
		SpringApplication.run(RedditalBackendApplication.class, args);
	}

}
