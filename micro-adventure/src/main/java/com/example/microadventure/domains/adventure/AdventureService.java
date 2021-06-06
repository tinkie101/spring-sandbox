package com.example.microadventure.domains.adventure;

import com.example.microadventure.domains.user.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdventureService {
    private final WebClient userWebClient;
    private final AdventureRepository adventureRepository;

    public List<AdventureDTO> getUserAdventures(UUID user) {
        return getAdventures().stream()
                .filter(adventure -> adventure.getUsers().stream().anyMatch(u -> u.equals(user)))
                .map(a -> new AdventureDTO(a.getId(),a.getName(),a.getDescription(), a.getUsers().stream().map(AdventureUser::getUserId).collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    public List<UserDTO> getAdventureUsers(UUID adventureId) {
        return getAdventures()
                .stream()
                .filter(adventureDTO -> adventureDTO.getId().equals(adventureId))
                .flatMap(adventureDTO -> adventureDTO.getUsers().stream())
                .map(userID -> userWebClient
                        .get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/user/{userId}")
                                .build(userID.getUserId().toString()))
                        .retrieve()
                        .onStatus(httpStatus -> httpStatus != HttpStatus.OK,
                                clientResponse -> Mono.just(new RuntimeException("Could not find user: " + clientResponse.statusCode())))
                        .bodyToMono(UserDTO.class)
                        .block()
                )
                .collect(Collectors.toList());
    }

    private List<Adventure> getAdventures() {
        return (List<Adventure>) adventureRepository.findAll();
    }
}
