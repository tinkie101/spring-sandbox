package com.example.microadventure.domain.user;

import com.example.microadventure.domains.adventure.AdventureDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;
//e2e test || integration test
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    @Autowired
    private WebTestClient webClient;

    @Test
    public void getAdventuresByUser() {
        webClient.get().uri((uriBuilder) -> {
            uriBuilder.path("/user/{userId}/adventures");
            return uriBuilder.build(UUID.fromString("7dfd9786-c6b2-11eb-b8bc-0242ac130003"));
        }).exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(AdventureDTO.class)
                .hasSize(2);
    }
}