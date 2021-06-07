package com.example.microadventure.domains.adventure;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Adventure {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private String description;

    @OneToMany(mappedBy="adventureId")
    private List<AdventureUser> users;

}
