package com.example.microadventure.domain.adventure;

import com.example.microadventure.domains.adventure.Adventure;
import com.example.microadventure.domains.adventure.AdventureRepository;
import com.example.microadventure.domains.adventure.AdventureService;
import com.example.microadventure.domains.adventure.AdventureUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ArgumentsSources;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
public class AdventureServiceTest {
    public static final UUID USER1_ID = UUID.fromString("7df602d4-c7cd-11eb-b8bc-0242ac130003");
    public static final UUID USER2_ID = UUID.fromString("7df60568-c7cd-11eb-b8bc-0242ac130003");
    public static final UUID USER3_ID = UUID.fromString("7df60784-c7cd-11eb-b8bc-0242ac130003");
    public static final UUID USER4_ID = UUID.fromString("7df60856-c7cd-11eb-b8bc-0242ac130003");

    @Autowired
    AdventureService adventureService;

    @MockBean
    AdventureRepository adventureRepository;

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
        @MockBean
        public WebClient userWebClient;

        @Bean
        public AdventureService adventureService(WebClient userWebClient, AdventureRepository adventureRepository) {
            return new AdventureService(userWebClient, adventureRepository);
        }
    }

    @BeforeEach
    public void given() {
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

        AdventureUser adventureUser2 = new AdventureUser();
        adventureUser2.setUserId(USER2_ID);
        adventureUser2.setAdventureId(adventure.getId());

        AdventureUser adventureUser3 = new AdventureUser();
        adventureUser3.setUserId(USER3_ID);
        adventureUser3.setAdventureId(adventure.getId());

        AdventureUser adventureUser4 = new AdventureUser();
        adventureUser4.setUserId(USER4_ID);
        adventureUser4.setAdventureId(adventure.getId());

        adventure.setUsers(List.of(adventureUser, adventureUser2));
        adventure2.setUsers(List.of(adventureUser, adventureUser2, adventureUser3, adventureUser4));

        Mockito.when(adventureRepository.findAll()).thenReturn(List.of(adventure, adventure2));
    }

    @ParameterizedTest
    @ArgumentsSources({@ArgumentsSource(AdventureServiceTestProvider.class)})
    public void whenGetUserAdventures(UUID userId, Integer expectedSize) {
        Assertions.assertEquals(adventureService.getUserAdventures(userId).size(), expectedSize);
    }
}
