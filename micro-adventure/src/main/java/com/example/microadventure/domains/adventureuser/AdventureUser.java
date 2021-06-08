package com.example.microadventure.domains.adventureuser;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class AdventureUser {
    @Id
    private UUID id;

    private UUID adventureId;
    private UUID userId;
}