package com.example.microuser.dtos;

import java.util.Objects;
import java.util.UUID;

public class UserDTO {
    private final UUID id;
    private final String name;
    private final String surname;

    public UserDTO(UUID id, String name, String surname) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(id, userDTO.id) && Objects.equals(name, userDTO.name) && Objects.equals(surname, userDTO.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname);
    }
}
