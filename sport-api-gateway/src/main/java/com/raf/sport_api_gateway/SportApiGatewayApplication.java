package com.raf.sport_api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class SportApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportApiGatewayApplication.class, args);
	}
	/*
	 * CORS Filter: Dozvoljava zahteve sa različitih portova.
	 * korisno jer Gateway prima sve zahteve spolja.
	 */
	@Bean
	public CorsFilter corsFilter() {
		 UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		 CorsConfiguration config = new CorsConfiguration();

		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("OPTIONS");
		config.addAllowedMethod("HEAD");
		config.addAllowedMethod("GET");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("DELETE");
		config.addAllowedMethod("PATCH");

		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

}
