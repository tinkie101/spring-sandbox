package com.example.microadventure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

@SpringBootTest
class MicroAdventureApplicationTests {

    @Test
    void contextLoads(ApplicationContext applicationContext) {
        Assertions.assertNotNull(applicationContext.getBean("userWebClient"));
        Assertions.assertNotNull(applicationContext.getBean("adventureWebClient"));
        Assertions.assertNotNull(applicationContext.getBean("adventureService"));
        Assertions.assertNotNull(applicationContext.getBean("adventureRepository"));
    }

}
