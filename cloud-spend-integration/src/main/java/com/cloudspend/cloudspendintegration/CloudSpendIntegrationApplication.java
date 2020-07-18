package com.cloudspend.cloudspendintegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class CloudSpendIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudSpendIntegrationApplication.class, args);
	}

}
