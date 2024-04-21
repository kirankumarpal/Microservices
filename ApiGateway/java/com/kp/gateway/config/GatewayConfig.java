package com.kp.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("UserService", r -> r.path("/users/**")
                .uri("http://localhost:8082"))
            .route("HotelService", r -> r.path("/hotels/**", "/staffs/**")
                .uri("http://localhost:8083"))
            .route("RatingService", r -> r.path("/ratings/**")
                .uri("http://localhost:8084"))
            .build();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder(); // Assuming RestClient is a custom class
    }
}
