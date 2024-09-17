package com.shpp.p2p.cs.anemeritskyy.assignment11;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Assignment11Part1Test {

    private static final Map<String[], Double> examples = new HashMap<>();

    @Test
    public void calculatorTest() {
        putExample(Math.log10(1000) * (Math.sin(45) + Math.cos(60)), "log10(a) * (sin(b) + cos(c))", "a = 1000", "b = 45", "c = 60");
        putExample(Math.sin(30) + Math.cos(60), "sin(a) + cos(b)", "a = 30", "b = 60");
        putExample(Math.sqrt(16) * (Math.sin(45) + Math.cos(30)), "sqrt(a) * (sin(b) + cos(c))", "a = 16", "b = 45", "c = 30");
        putExample((Math.sin(90) - Math.cos(45)) / Math.sqrt(9), "(sin(a) - cos(b)) / sqrt(c)", "a = 90", "b = 45", "c = 9");
        putExample(Math.tan(45) + (Math.log10(100) * log2(16)), "tan(a) + (log10(b) * log2(c))", "a = 45", "b = 100", "c = 16");
        putExample((Math.sin(60) + Math.cos(30)) * Math.log10(1000), "(sin(a) + cos(b)) * log10(c)", "a = 60", "b = 30", "c = 1000");
        putExample(log2(64) + Math.sqrt(49), "log2(a) + sqrt(b)", "a = 64", "b = 49");
        putExample(Math.atan(1) * (Math.sin(30) - Math.cos(60)), "atan(a) * (sin(b) - cos(c))", "a = 1", "b = 30", "c = 60");
        putExample(Math.sqrt(81) / (Math.sin(90) + Math.cos(0)), "sqrt(a) / (sin(b) + cos(c))", "a = 81", "b = 90", "c = 0");
        putExample(Math.sin(90) + (Math.cos(30) * Math.sqrt(16)), "sin(a) + (cos(b) * sqrt(c))", "a = 90", "b = 30", "c = 16");
        putExample(Math.sqrt(25) + Math.atan(1), "sqrt(a) + atan(b)", "a = 25", "b = 1");
        putExample(Math.tan(45) * (log2(8) + Math.log10(100)), "tan(a) * (log2(b) + log10(c))", "a = 45", "b = 8", "c = 100");
        putExample((Math.sin(60) - Math.cos(30)) / Math.sqrt(4), "(sin(a) - cos(b)) / sqrt(c)", "a = 60", "b = 30", "c = 4");
        putExample(Math.atan(1) + (log2(16) * Math.sqrt(9)), "atan(a) + (log2(b) * sqrt(c))", "a = 1", "b = 16", "c = 9");
        putExample(Math.log10(1000) * (Math.sin(45) + Math.cos(60)), "log10(a) * (sin(b) + cos(c))", "a = 1000", "b = 45", "c = 60");
        putExample((Math.sin(30) + Math.cos(60)) / Math.atan(1), "(sin(a) + cos(b)) / atan(c)", "a = 30", "b = 60", "c = 1");
        putExample(log2(64) - (Math.sin(90) * Math.sqrt(16)), "log2(a) - (sin(b) * sqrt(c))", "a = 64", "b = 90", "c = 16");
        putExample(Math.tan(45) + (Math.log10(100) / Math.sqrt(25)), "tan(a) + (log10(b) / sqrt(c))", "a = 45", "b = 100", "c = 25");
        putExample(Math.sqrt(10), "sqrt(10)");
        putExample(Math.sin(30) + Math.cos(60), "sin(a) + cos(b)", "a = 30", "b = 60");
        putExample(-18, "(-2-(a + b)) * 2", "a = 3", "b = 4");
        putExample(40, "(a * (b + (c - d)))", "a = 5", "b = 7", "c = 3", "d = 2");
        putExample(28, "(((a + b) - c) * d) / e", "a = 4", "b = 6", "c = 3", "d = 8", "e = 2");
        putExample(6, "a - (b * (c + d))", "a = 20", "b = 2", "c = 4", "d = 3");
        putExample(10, "(a / (b - c)) + d", "a = 12", "b = 10", "c = 8", "d = 4");
        putExample(27, "a * (b + (c - d) / e)", "a = 3", "b = 6", "c = 10", "d = 4", "e = 2");
        putExample(16, "((a + b) * (a - b)) / 2", "a = 6", "b = 2");
        putExample(16, "(a + (b - c) * (d / e))", "a = 10", "b = 5", "c = 2", "d = 8", "e = 4");
        putExample(26, "((a * b) - (c / d))", "a = 6", "b = 5", "c = 8", "d = 2");
        putExample(24, "a * ((b + c) / (d - e))", "a = 8", "b = 10", "c = 2", "d = 6", "e = 2");
        putExample(16, "2+(a + b) * 2", "a = 3", "b = 4");
        putExample(18, "(2+(a + b)) * 2", "a = 3", "b = 4");
        putExample(-18, "(-2-(a + b)) * 2", "a = 3", "b = 4");
        putExample(2, "--2");
        putExample(2, "-b", "b=-2");
        putExample(8, "-b*4", "b=-2");
        putExample(11, "3-b*4", "b=-2");
        putExample(-2, "1 + -a * 2 * 3.5 - ab + 2^3", "a = 2", "ab = -3");
        putExample(Math.pow(4, 0.3), "4^0.3");
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

    private double log2(double trigonometryValue) {
        return Math.log(trigonometryValue) / Math.log(2);
    }

    private static void checkCalculation() {
        for (Map.Entry<String[], Double> example : examples.entrySet()) {
            Calculator calculator = new Calculator(example.getKey());
            assertEquals(example.getValue(), calculator.getResult());
            System.out.println(calculator.getResult());
        }

        checkDivisionByZero();
    }

    private static void checkDivisionByZero() {
        assertThrows(ArithmeticException.class, () -> new Calculator(new String[]{"3/0"}).calculate());
    }

    private static void putExample(double expected, String... formula) {
        examples.put(formula, expected);
    }
}