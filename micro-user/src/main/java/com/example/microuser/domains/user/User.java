package com.example.microuser.domains.user;

import java.util.UUID;

public class User {
    private final UUID id;
    private final String name;
    private final String surname;

    public User(UUID id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
