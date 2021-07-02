package com.example.microadventure.shared;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class SharedConfig {
    @Bean
    public WebClient userWebClient() {
        return WebClient.builder().baseUrl("http://localhost:9002").build();
    }

    @Bean
    public WebClient adventureWebClient() {
        return WebClient.builder().baseUrl("http://localhost:9000").build();
    }

    @Bean
    public WebClient keycloakLoginClient() {
        return WebClient.builder().baseUrl("http://localhost:5000/auth/realms/sandbox/protocol/openid-connect/token").build();
    }
}
