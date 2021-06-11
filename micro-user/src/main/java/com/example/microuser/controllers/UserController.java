package com.example.microuser.controllers;

import com.example.microuser.dtos.UserDTO;
import com.example.microuser.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class UserController {
    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/user/{userId}")
    public UserDTO getUser(@PathVariable String userId) {
        return userService.getUserById(UUID.fromString(userId));
    }
}
