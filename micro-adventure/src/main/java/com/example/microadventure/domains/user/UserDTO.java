package com.example.microadventure.domains.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
public class UserDTO {
    private UUID id;
    private String name;
    private String surname;
}