package com.example.microadventure.domains.adventure;

import com.example.microadventure.domains.user.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AdventureController {
    private final AdventureService adventureService;

    @GetMapping("/adventure/{adventureId}/users")
    public List<UserDTO> getAdventureUsers(@PathVariable String adventureId) {
        return adventureService.getAdventureUsers(UUID.fromString(adventureId));
    }
}
