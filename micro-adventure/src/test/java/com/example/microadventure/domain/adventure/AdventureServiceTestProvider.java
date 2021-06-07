package com.example.microadventure.domain.adventure;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.UUID;
import java.util.stream.Stream;

public class AdventureServiceTestProvider implements ArgumentsProvider {
    public static final UUID USER1_ID = UUID.fromString("7df602d4-c7cd-11eb-b8bc-0242ac130003");
    public static final UUID USER2_ID = UUID.fromString("7df60568-c7cd-11eb-b8bc-0242ac130003");
    public static final UUID USER3_ID = UUID.fromString("7df60784-c7cd-11eb-b8bc-0242ac130003");
    public static final UUID USER4_ID = UUID.fromString("7df60856-c7cd-11eb-b8bc-0242ac130003");

    @Override
    public Stream<AdventureServiceTestArgumentType> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                AdventureServiceTestArgumentType.of(USER1_ID, 2),
                AdventureServiceTestArgumentType.of(USER2_ID, 2),
                AdventureServiceTestArgumentType.of(USER3_ID, 1),
                AdventureServiceTestArgumentType.of(USER4_ID, 1)
        );
    }
}