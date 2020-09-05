package com.example.microservices.shoppingcartservicebasic;

import com.netflix.discovery.DiscoveryClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class ShoppingCartServiceBasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingCartServiceBasicApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
