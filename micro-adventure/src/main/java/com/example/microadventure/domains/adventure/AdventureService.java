package com.example.microadventure.domains.adventure;

import com.example.microadventure.domains.adventureuser.AdventureUser;
import com.example.microadventure.domains.adventureuser.AdventureUserRepository;
import com.example.microadventure.domains.user.UserDTO;
import com.example.microadventure.exceptions.AdventureNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
public class AdventureService {
    private final Logger logger = LoggerFactory.getLogger(AdventureService.class);

    private final WebClient userWebClient;
    private final AdventureRepository adventureRepository;
    private final AdventureUserRepository adventureUserRepository;

    public AdventureService(WebClient userWebClient, AdventureRepository adventureRepository, AdventureUserRepository adventureUserRepository) {
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

    public Mono<AdventureDTO> createAdventure(Mono<AdventureDTO> adventureDto) {
        logger.debug("Add adventure");

        return adventureDto
                .map(dto -> {
                    Adventure adventure = new Adventure();
                    adventure.setDescription(dto.getDescription());
                    adventure.setName(dto.getName());
                    return adventure;
                })
                .flatMap(adventureRepository::save)
                .map(ra -> new AdventureDTO(ra.getId(), ra.getName(), ra.getDescription()));
    }

    public Mono<AdventureDTO> putUpdateAdventure(UUID adventureId, Mono<AdventureDTO> adventure) {
        logger.debug("put update adventure: {}", adventureId);

        return adventureRepository.findById(adventureId)
                .switchIfEmpty(Mono.error(new AdventureNotFound("Could not update adventure " + adventureId)))
                .flatMap((o) -> adventure)
                .map(adventureDTO -> new Adventure(adventureDTO.getId(), adventureDTO.getName(), adventureDTO.getDescription()))
                .flatMap(adventureRepository::save)
                .map(ra -> new AdventureDTO(ra.getId(), ra.getName(), ra.getDescription()));
    }

    public Mono<Void> deleteAdventure(UUID adventureId) {
        logger.debug("delete adventure: {}", adventureId);

        return adventureRepository.findById(adventureId)
                .switchIfEmpty(Mono.error(new AdventureNotFound("Could not delete adventure " + adventureId)))
                .flatMap(adventureRepository::delete);
    }

    public Flux<Adventure> getAllAdventures() {
        return adventureRepository.findAll();
    }

    private Mono<UserDTO> getUserFromService(UUID userID) {
        return ReactiveSecurityContextHolder.getContext()
                .map(context -> (JwtAuthenticationToken) context.getAuthentication())
                .map(AbstractOAuth2TokenAuthenticationToken::getToken)
                .flatMap(authToken -> userWebClient
                        .get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/user/{userId}")
                                .build(userID.toString()))
                        .headers(headers -> headers.put("Authorization", List.of("Bearer " + authToken.getTokenValue())))
                        .retrieve()
                        .onStatus(httpStatus -> httpStatus != HttpStatus.OK,
                                clientResponse -> Mono.just(new RuntimeException("Could not find user: " + clientResponse.statusCode())))
                        .bodyToMono(UserDTO.class));
    }
}
