package com.rajesh.cachingpoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ChchingpocApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChchingpocApplication.class, args);
	}

}
