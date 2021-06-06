package com.example.microadventure.domains.adventure;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AdventureRepository extends CrudRepository<Adventure, UUID> {

}
