package com.example.sport_notification_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class SportNotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportNotificationServiceApplication.class, args);
	}

}
