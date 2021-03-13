package com.github.kiskobotond;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculatorTest {

    private Calculator underTest;

    @BeforeEach
    void setUp() {
        underTest = new Calculator();
    }

    @Test
    void testAdd() {
        // given

        // when
        double result = underTest.add(3d, 4d);

        // then
        assertThat(result).isEqualTo(7d);
    }

    @Test
    void testSubtract() {
        // given

        // when
        double result = underTest.subtract(3d, 2d);

        // then
        assertThat(result).isEqualTo(1d);
    }

}
