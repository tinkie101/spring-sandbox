package com.example.microadventure.domain.adventure;

import com.example.microadventure.domains.adventure.AdventureDTO;
import com.example.microadventure.domains.user.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

//e2e test || integration test
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnabledIfEnvironmentVariable(named = "SPRING_PROFILES_ACTIVE", matches = "(e2e)")
public class AdventureControllerTest {
    @Autowired
    private WebTestClient webClient;

    @Test
    public void getAdventureUsers() {
        webClient.get().uri((uriBuilder) -> {
            uriBuilder.path("/adventure/{adventureId}/users");
            return uriBuilder.build(UUID.fromString("7dfd9998-c6b2-11eb-b8bc-0242ac130003"));
        }).exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(UserDTO.class)
                .hasSize(2);
    }

    @Test
    public void getAllAdventures() {
        webClient.get().uri((uriBuilder) -> {
            uriBuilder.path("/adventures");
            return uriBuilder.build();
        }).exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(AdventureDTO.class)
                .hasSize(2);
    }
}
