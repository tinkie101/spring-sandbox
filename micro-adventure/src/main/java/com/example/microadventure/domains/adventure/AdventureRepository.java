package com.example.microadventure.domains.adventure;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AdventureRepository extends ReactiveCrudRepository<Adventure, UUID> {

}
