package com.example.microadventure.domains.adventure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AdventureConfig {
    @Bean
    public WebClient userWebClient() {
        return WebClient.builder().baseUrl("http://localhost:9002").build();
    }
}
