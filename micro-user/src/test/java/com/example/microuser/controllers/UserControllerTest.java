package com.example.microuser.controllers;

import com.example.microuser.MicroUserApplication;
import com.example.microuser.dtos.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;
import java.util.stream.Stream;

//e2e test || integration test
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = MicroUserApplication.class)
@EnabledIfEnvironmentVariable(named = "SPRING_PROFILES_ACTIVE", matches = "(e2e)")
public class UserControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void getAllUsers() {
        webTestClient.get().uri((uriBuilder) -> {
            uriBuilder.path("/users");
            return uriBuilder.build();
        }).exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(UserDTO.class)
                .hasSize(4);
    }

    @ParameterizedTest
    @MethodSource("getUserByIdArguments")
    public void getUserById(UUID userId, String expectedName) {

        UserDTO user = webTestClient.get().uri((uriBuilder) -> {
            uriBuilder.path("/user/{userId}");
            return uriBuilder.build(userId);
        }).exchange()
                .expectStatus()
                .isOk()
                .expectBody(UserDTO.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(user);
        Assertions.assertEquals(expectedName, user.getName());
    }

    @Test
    public void testUserNotFound() {
       webTestClient.get().uri((uriBuilder) -> {
            uriBuilder.path("/user/{userId}");
            return uriBuilder.build(UUID.fromString("1dfd9786-c6b2-11eb-b8bc-0242ac130003"));
        }).exchange()
                .expectStatus()
                .isNotFound();
    }

    static private Stream<Arguments> getUserByIdArguments() {
        return Stream.of(
                Arguments.of(UUID.fromString("7dfd9786-c6b2-11eb-b8bc-0242ac130003"), "Albert"),
                Arguments.of(UUID.fromString("6d1e3ffa-c6b3-11eb-b8bc-0242ac130003"), "Latham"),
                Arguments.of(UUID.fromString("70ff6838-c6b3-11eb-b8bc-0242ac130003"), "Lisa"),
                Arguments.of(UUID.fromString("ae4c2172-c6b3-11eb-b8bc-0242ac130003"), "Arno")
        );
    }
}
