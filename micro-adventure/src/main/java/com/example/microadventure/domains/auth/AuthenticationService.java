package com.example.microadventure.domains.auth;

import com.example.microadventure.exceptions.InvalidUserCredentials;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class AuthenticationService {
    private final WebClient keycloakLoginClient;

    public AuthenticationService(WebClient keycloakLoginClient) {
        this.keycloakLoginClient = keycloakLoginClient;
    }

    public Mono<AuthTokenDTO> getUserAccessToken(Mono<UserCredentialDTO> userCredentials) {
        return userCredentials.map(uc -> {
            MultiValueMap<String, String> credentialMap = new LinkedMultiValueMap<>();

            credentialMap.put("client_id", List.of("sandbox-client"));
            credentialMap.put("client_secret", List.of("sandbox-client"));
            credentialMap.put("grant_type", List.of(uc.getGrant_type().toString()));

            if (uc.getGrant_type() == GrantType.password) {
                credentialMap.put("username", List.of(uc.getUsername()));
                credentialMap.put("password", List.of(uc.getPassword()));
            } else {
                credentialMap.put("refresh_token", List.of(uc.getRefresh_token()));
            }
            return credentialMap;
        }).flatMap(credentialMap ->
                keycloakLoginClient.post()
                        .body(BodyInserters.fromFormData(credentialMap))
                        .retrieve()
                        .onStatus(s -> s == HttpStatus.UNAUTHORIZED, (ClientResponse response) -> {
                            throw new InvalidUserCredentials("Invalid user credentials: " + response.statusCode().getReasonPhrase());
                        })
                        .bodyToMono(AuthTokenDTO.class)
        );
    }
}
