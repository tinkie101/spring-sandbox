package com.example.microadventure.domain.adventure;

import com.example.microadventure.domains.adventure.Adventure;
import com.example.microadventure.domains.adventure.AdventureRepository;
import com.example.microadventure.domains.adventure.AdventureService;
import com.example.microadventure.domains.adventureuser.AdventureUser;
import com.example.microadventure.domains.adventureuser.AdventureUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ArgumentsSources;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class AdventureServiceTest {
    @Mock
    AdventureRepository mockAdventureRepository;

    @Mock
    AdventureUserRepository mockAdventureUserRepository;

    @Mock
    WebClient webclient;

    AdventureService adventureService;

    @BeforeEach
    public void given() {
        final UUID USER1_ID = UUID.fromString("7df602d4-c7cd-11eb-b8bc-0242ac130003");
        final UUID USER2_ID = UUID.fromString("7df60568-c7cd-11eb-b8bc-0242ac130003");
        final UUID USER3_ID = UUID.fromString("7df60784-c7cd-11eb-b8bc-0242ac130003");
        final UUID USER4_ID = UUID.fromString("7df60856-c7cd-11eb-b8bc-0242ac130003");

        adventureService = new AdventureService(webclient, mockAdventureRepository, mockAdventureUserRepository);

        Adventure adventure = new Adventure();
        adventure.setId(UUID.randomUUID());
        adventure.setName("Adventure");
        adventure.setDescription("Description");

        Adventure adventure2 = new Adventure();
        adventure2.setId(UUID.randomUUID());
        adventure2.setName("Adventure 2");
        adventure2.setDescription("Description 2");

        AdventureUser adventureUser = new AdventureUser();
        adventureUser.setUserId(USER1_ID);
        adventureUser.setAdventureId(adventure.getId());

        AdventureUser adventureUser_2 = new AdventureUser();
        adventureUser_2.setUserId(USER1_ID);
        adventureUser_2.setAdventureId(adventure2.getId());

        AdventureUser adventureUser2 = new AdventureUser();
        adventureUser2.setUserId(USER2_ID);
        adventureUser2.setAdventureId(adventure.getId());

        AdventureUser adventureUser2_2 = new AdventureUser();
        adventureUser2_2.setUserId(USER2_ID);
        adventureUser2_2.setAdventureId(adventure2.getId());

        AdventureUser adventureUser3 = new AdventureUser();
        adventureUser3.setUserId(USER3_ID);
        adventureUser3.setAdventureId(adventure2.getId());

        AdventureUser adventureUser4 = new AdventureUser();
        adventureUser4.setUserId(USER4_ID);
        adventureUser4.setAdventureId(adventure2.getId());

        Mockito.lenient().when(mockAdventureRepository.findById(adventure.getId())).thenReturn(Mono.just(adventure));
        Mockito.lenient().when(mockAdventureRepository.findById(adventure2.getId())).thenReturn(Mono.just(adventure2));


        Mockito.lenient().when(mockAdventureRepository.findAll()).thenReturn(Flux.fromIterable(List.of(adventure, adventure2)));
        Mockito.lenient().when(mockAdventureUserRepository.findAll()).thenReturn(Flux.fromIterable(List.of(adventureUser, adventureUser_2, adventureUser2, adventureUser2_2, adventureUser3, adventureUser4)));
    }

    @ParameterizedTest
    @ArgumentsSources({@ArgumentsSource(AdventureServiceTestProvider.class)})
    public void whenGetUserAdventures(UUID userId, Integer expectedSize) {
        StepVerifier.create(adventureService.getUserAdventures(userId))
                .expectNextCount(expectedSize)
                .expectComplete()
                .verify();
    }

    @Test
    public void whenGetAllAdventures() {
        StepVerifier.create(adventureService.getAllAdventures())
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }
}
