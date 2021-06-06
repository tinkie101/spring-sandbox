package com.example.microadventure.domains.user;

import com.example.microadventure.domains.adventure.AdventureDTO;
import com.example.microadventure.domains.adventure.AdventureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final AdventureService adventureService;

    @GetMapping("/user/{userId}/adventures")
    public List<AdventureDTO> getUserAdventures(@PathVariable String userId) {
        return adventureService.getUserAdventures(UUID.fromString(userId));
    }
}
