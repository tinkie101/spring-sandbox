package com.example.microadventure.domains.user;

import com.example.microadventure.domains.adventure.AdventureDTO;
import com.example.microadventure.domains.adventure.AdventureService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class UserController {
    private final AdventureService adventureService;

    UserController(AdventureService adventureService) {
        this.adventureService = adventureService;
    }

    @Bean
    public RouterFunction<ServerResponse> allUserRoutes() {
        return route(GET("/user/{userId}/adventures"), this::getAdventuresByUserId);
    }

    @NonNull
    private Mono<ServerResponse> getAdventuresByUserId(ServerRequest request) {
        return ok().body(adventureService.getUserAdventures(UUID.fromString(request.pathVariable("userId"))), AdventureDTO.class);
    }
}
