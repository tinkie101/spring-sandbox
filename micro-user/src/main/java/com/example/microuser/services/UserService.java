package com.example.microuser.services;

import com.example.microuser.dtos.UserDTO;
import com.example.microuser.exceptions.UserNotFoundException;
import com.example.microuser.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getAllUsers() {
        List<UserDTO> userDTOs = new ArrayList<>();

        userRepository.findAll().forEach(user -> userDTOs.add( new UserDTO(user.getId(), user.getName(), user.getSurname())));

        return userDTOs;
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

}
