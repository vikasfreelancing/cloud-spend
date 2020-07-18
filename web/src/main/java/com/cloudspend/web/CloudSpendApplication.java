package com.cloudspend.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableKafka
public class CloudSpendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudSpendApplication.class, args);
	}

}
