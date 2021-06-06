package com.example.microuser.domains.user;

import com.example.microuser.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public List<User> getAllUsers() {
        return getUsers();
    }

    public User getUser(UUID userId) {
        Optional<User> user = getUsers().stream().filter(u -> u.getId().equals(userId)).findFirst();

        if (user.isEmpty()) {
            UserNotFoundException notFound = new UserNotFoundException("User not found!");
            logger.error("User not found " + userId, notFound);

            throw notFound;
        }
        else
            return user.get();
    }

    private List<User> getUsers() {
        return List.of(
                new User(UUID.fromString("7dfd9786-c6b2-11eb-b8bc-0242ac130003"), "Albert", "Volschenk"),
                new User(UUID.fromString("6d1e3ffa-c6b3-11eb-b8bc-0242ac130003"), "Latham", "van der Walt"),
                new User(UUID.fromString("70ff6838-c6b3-11eb-b8bc-0242ac130003"), "Lisa", "Horn"),
                new User(UUID.fromString("ae4c2172-c6b3-11eb-b8bc-0242ac130003"), "Arno", "Pouwels")
        );
    }
}
