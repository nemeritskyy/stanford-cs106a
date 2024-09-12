package com.shpp.p2p.cs.anemeritskyy.assignment10;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Assignment10Part1Test {

    private static final Map<String[], Double> examples = new HashMap<>();

    @Test
    public void calculatorTest() {
        putExample(2, "--2");
        putExample(2, "-b", "b=-2");
        putExample(8, "-b*4", "b=-2");
        putExample(11, "3-b*4", "b=-2");
        putExample(-2, "1 + -a * 2 * 3.5 - ab + 2^3", "a = 2", "ab = -3");
        putExample(Math.pow(4, 0.3), "4^0.3");
        putExample(4, "-2^2");
        putExample(-4, "0-2^2");
        putExample(-2, "1 + -a * 2 * 3.5 - y + 2^3", "a = 2", "y = -3");
        putExample(7.5, "a * 2 + 3 - b / 2 + 2^2", "a = 1", "b = 3");
        putExample(10, " 2 + 2 * a ^2 - y", "a = 2", "y = 0");
        putExample(16, "2^3 + 4 - x", "x = -4");
        putExample(18.5, "a / 2 + 3.5 * 4 + 2^2", "a = 1");
        putExample(3, "a + 2^2 - b", "a = 3", "b = 4");
        putExample(14, "2^a + 2 + 4", "a = 3");
        putExample(14.5, "b * 4 + 5.5 - a / 2", "a = 6", "b = 3");
        putExample(6, "2 * a + b / 2 - 1", "a = 3", "b = 2");
        putExample(11, "a * 3 - b + 2^2", "a = 5", "b = 8");
        putExample(4, "2 ^ 3 / 2 - a + 1", "a = 1");
        putExample(12, "a * b + 2^a", "a = 2", "b = 4");
        putExample(24.5, "a * 3 + 2^2 + 5.5", "a = 5");
        putExample(25, "2 ^ a + b * 3 - 1", "a = 3", "b = 6");
        putExample(6, "2.5 * 2 + 2^b", "b = 0");
        putExample(10, "a ^2 + b - 3", "a = 3", "b = 4");
        putExample(3, "a - 2 + 3 * b", "a = 8", "b = -1");
        putExample(21, "2 + 2 * b + a", "a = 7", "b = 6");
        putExample(12, "2 + 2 * 3 + 4 * a", "a = 1");
        putExample(12, "3 * a + 2 * b + 2^2", "a = 2", "b = 1");
        putExample(9, "a + b * 2 + c ^ 2", "a = 1", "b = 2", "c = 2");
        putExample(-6.5, "a / 2 - b * 3 + 2^c", "a = 3", "b = 4", "c = 2");
        putExample(8, "2^a + b * 2 - c", "a = 3", "b = 2", "c = 4");
        putExample(13, "a * 3 + b - 2^c", "a = 4", "b = 5", "c = 2");
        putExample(8, "a - b + c * 3 + 2^2", "a = 3", "b = 2", "c = 1");
        checkCalculation();
    }

    private static void checkCalculation() {
        for (Map.Entry<String[], Double> example : examples.entrySet()) {
            Calculator calculator = new Calculator(example.getKey());
            assertEquals(example.getValue(), calculator.getResult());
        }

        checkDivisionByZero();
    }

    private static void checkDivisionByZero() {
        assertThrows(ArithmeticException.class, () -> {
            new Calculator(new String[]{"3/0"}).calculate();
        });
    }

    private static void putExample(double expected, String... formula) {
        examples.put(formula, expected);
    }
}