package com.example.microadventure.domains.adventure;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Adventure {
    @Id
    private UUID id;

    private String name;
    private String description;
}
