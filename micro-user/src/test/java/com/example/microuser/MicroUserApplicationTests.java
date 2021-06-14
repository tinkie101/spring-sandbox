package com.example.microuser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class MicroUserApplicationTests {

    @Test
    void contextLoads(ApplicationContext applicationContext) {
        Assertions.assertNotNull(applicationContext.getBean("userService"));
    }
}
