package com.example.microadventure.domains.adventure;

import com.example.microadventure.domains.adventureuser.AdventureUser;
import com.example.microadventure.domains.adventureuser.AdventureUserRepository;
import com.example.microadventure.domains.user.UserDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class AdventureService {
    private final Logger logger = LoggerFactory.getLogger(AdventureService.class);

    private final WebClient userWebClient;
    private final AdventureRepository adventureRepository;
    private final AdventureUserRepository adventureUserRepository;

    public AdventureService(WebClient userWebClient,AdventureRepository adventureRepository, AdventureUserRepository adventureUserRepository) {
        this.userWebClient = userWebClient;
        this.adventureRepository = adventureRepository;
        this.adventureUserRepository = adventureUserRepository;
    }

    public Flux<AdventureDTO> getUserAdventures(UUID userId) {
        logger.debug("Get user adventures for: {}", userId);

        return adventureUserRepository.findAll()
                .filter(adventureUser -> adventureUser.getUserId().equals(userId))
                .map(AdventureUser::getAdventureId)
                .distinct()
                .flatMap(adventureRepository::findById)
                .map(a -> new AdventureDTO(a.getId(), a.getName(), a.getDescription()));
    }

    public Flux<UserDTO> getUsersByAdventureId(UUID adventureId) {
        logger.debug("Get adventure users for: {}", adventureId);

        return adventureUserRepository.findAll()
                .filter(adventureUser -> adventureUser.getAdventureId().equals(adventureId))
                .map(AdventureUser::getUserId)
                .distinct()
                .flatMap(this::getUserFromService);
    }

    public Flux<Adventure> getAllAdventures() {
        return adventureRepository.findAll();
    }

    private Mono<UserDTO> getUserFromService(UUID userID) {
        return userWebClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/user/{userId}")
                        .build(userID.toString()))
                .retrieve()
                .onStatus(httpStatus -> httpStatus != HttpStatus.OK,
                        clientResponse -> Mono.just(new RuntimeException("Could not find user: " + clientResponse.statusCode())))
                .bodyToMono(UserDTO.class);
    }
}
