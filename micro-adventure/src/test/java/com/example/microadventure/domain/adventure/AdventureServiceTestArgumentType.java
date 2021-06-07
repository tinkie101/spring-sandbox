package com.example.microadventure.domain.adventure;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.params.provider.Arguments;

import java.util.UUID;

public class AdventureServiceTestArgumentType  implements Arguments {
    private final UUID userId;
    private final Integer expectedSize;

    public AdventureServiceTestArgumentType(UUID userId, Integer expectedSize) {
        this.userId = userId;
        this.expectedSize = expectedSize;
    }

    static AdventureServiceTestArgumentType of(UUID userId, Integer expectedSize) {
        return new AdventureServiceTestArgumentType(userId, expectedSize);
    }

    @Override
    public Object[] get() {
        return Arrays.array(userId, expectedSize);
    }
}