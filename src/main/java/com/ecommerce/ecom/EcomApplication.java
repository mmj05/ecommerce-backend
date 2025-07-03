package com.ecommerce.ecom;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcomApplication {

	public static void main(String[] args) {
		// Load .env file
		Dotenv dotenv = Dotenv.configure()
				.ignoreIfMissing() // Don't fail if .env file is missing (useful for production)
				.load();
		
		// Set system properties from .env file
		dotenv.entries().forEach(entry -> {
			System.setProperty(entry.getKey(), entry.getValue());
		});
		
		SpringApplication.run(EcomApplication.class, args);
	}

}
