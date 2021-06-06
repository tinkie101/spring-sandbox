package com.example.microadventure.domains.adventure;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class AdventureDTO {
    private final UUID id;
    private final String name;
    private final String description;

    private final List<UUID> users;
}
