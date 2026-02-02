package com.raf.sport_user_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class SportUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportUserServiceApplication.class, args);
	}

	@Bean
	@LoadBalanced // Ovo je bitno za Eureku da bi prepoznao "http://sports-service"
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
