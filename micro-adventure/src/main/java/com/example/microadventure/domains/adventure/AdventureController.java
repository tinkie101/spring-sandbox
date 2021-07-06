package com.example.microadventure.domains.adventure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class AdventureController {
    private final AdventureService adventureService;

    AdventureController(AdventureService adventureService) {
        this.adventureService = adventureService;
    }

    @Bean
    public RouterFunction<ServerResponse> allAdventureRoutes() {
        return route(GET("/adventure/{adventureId}/users"), this::getUsersByAdventureId)
                .andRoute(GET("/adventures"), this::getAllAdventures)
                .andRoute(PUT("/adventure/{adventureId}"), this::putUpdateAdventure);
    }

    @NonNull
    private Mono<ServerResponse> getUsersByAdventureId(ServerRequest request) {
        return ok().body(adventureService.getUsersByAdventureId(UUID.fromString(request.pathVariable("adventureId"))), AdventureDTO.class);
    }

    @NonNull
    private Mono<ServerResponse> putUpdateAdventure(ServerRequest request) {
        return ok().body(adventureService.putUpdateAdventure(UUID.fromString(request.pathVariable("adventureId")), request.bodyToMono(AdventureDTO.class)), AdventureDTO.class);
    }

    @NonNull
    private Mono<ServerResponse> getAllAdventures(ServerRequest request) {
        return ok().body(adventureService.getAllAdventures(), AdventureDTO.class);
    }
}
