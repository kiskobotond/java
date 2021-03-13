package com.github.kiskobotond;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelloWorldTest {

    private HelloWorld underTest;

    @BeforeEach
    void setUp() {
        underTest = new HelloWorld();
    }

    @Test
    void testSayHello() {
        // given

        // when
        String result = underTest.sayHello();

        // then

        assertThat(result).isEqualTo("Hello Java");
    }
}
