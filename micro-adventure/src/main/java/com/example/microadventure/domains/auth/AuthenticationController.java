package com.example.microadventure.domains.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Bean
    public RouterFunction<ServerResponse> allAuthRoutes() {
        return route(POST("/auth/login"), this::loginUser);
    }

    @NonNull
    private Mono<ServerResponse> loginUser(ServerRequest request) {
        return ok().body(authenticationService.getUserAccessToken(request.body(toMono(UserCredentialDTO.class))), AuthTokenDTO.class);
    }

}
