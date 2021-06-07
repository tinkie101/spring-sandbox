package com.example.microadventure.domains.adventure;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AdventureUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID adventureId;
    private UUID userId;
}