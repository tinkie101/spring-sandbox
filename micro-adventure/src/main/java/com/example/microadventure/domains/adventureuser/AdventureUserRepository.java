package com.example.microadventure.domains.adventureuser;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AdventureUserRepository extends ReactiveCrudRepository<AdventureUser, UUID> {

}
