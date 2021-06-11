package com.example.microuser.services;

import com.example.microuser.entities.User;
import com.example.microuser.dtos.UserDTO;
import com.example.microuser.exceptions.UserNotFoundException;
import com.example.microuser.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getAllUsers() {
        return getUsers().stream().map(user -> new UserDTO(user.getId(), user.getName(), user.getSurname())).collect(Collectors.toList());
    }

    public UserDTO getUserById(UUID userId) {
        Optional<UserDTO> user = userRepository.findById(userId).map(u -> new UserDTO(u.getId(), u.getName(), u.getSurname()));

        if (user.isEmpty()) {
            UserNotFoundException notFound = new UserNotFoundException("User not found!");
            logger.error("User not found " + userId, notFound);

            throw notFound;
        }
        else
            return user.get();
    }

    private List<User> getUsers() {
        return (List<User>)userRepository.findAll();

    }
}
