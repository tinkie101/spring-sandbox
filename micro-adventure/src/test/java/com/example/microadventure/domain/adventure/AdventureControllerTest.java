package com.example.microadventure.domain.adventure;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.StatusAssertions;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdventureControllerTest {
    @Autowired
    private WebTestClient webClient;

    @Test
    public void getAdventureUsers() {
        StatusAssertions status = webClient.get().uri((uriBuilder) -> {
            uriBuilder.path("/adventure/{adventureId}/users");
            return uriBuilder.build(UUID.fromString("7dfd9998-c6b2-11eb-b8bc-0242ac130003"));
        }).exchange().expectStatus();

        status.isOk();
    }

}
