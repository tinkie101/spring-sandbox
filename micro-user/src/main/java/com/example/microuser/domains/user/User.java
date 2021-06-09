package com.example.microuser.domains.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private String surname;

    public User(){}

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

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private UUID id;
        private String name;
        private String surname;

        UserBuilder() {
        }

        public UserBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public UserBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public User build() {
            return new User(id, name, surname);
        }

        public String toString() {
            return "AdventureUser.AdventureUserBuilder(id=" + this.id + ", name=" + this.name + ", surname=" + this.surname + ")";
        }
    }
}
