package com.vinibarros.optisched;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class OptischedApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OptischedApiApplication.class, args);
	}

}
