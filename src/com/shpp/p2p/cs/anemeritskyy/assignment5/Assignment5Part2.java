package com.shpp.p2p.cs.anemeritskyy.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Compilation algorithm
 */
public class Assignment5Part2 extends TextProgram {
    /**
     * Colors for command line view, using for validate result
     */
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

    /**
     * This method start program and wait two positive numbers from user
     * after user input, this algorithm calculate sum of this numbers
     */
    public void run() {
        /* Loop, reading for positive numbers and adding them. */
        while (true) {
            String n1 = readLine("Enter first number: ");
            String n2 = readLine("Enter second number: ");
            String result = addNumericStrings(n1, n2);
            String expected = new BigInteger(n1).add(new BigInteger(n2)).toString();
            println(n1 + " + " + n2 + " = " + (result.equals(expected) ? ANSI_GREEN : ANSI_RED) + result + ANSI_RESET);
            println();
        }
    }

    /**
     * Given two string representations of nonnegative integers, adds the
     * numbers represented by those strings and returns the result.
     *
     * @param n1 The first number.
     * @param n2 The second number.
     * @return A String representation of n1 + n2
     */
    private String addNumericStrings(String n1, String n2) {
        List<String> digits = new ArrayList<>();
        int divisionRemainder = 0;
        int indexOfLastDigit = 0;
        while (indexOfLastDigit < getMaxNumberLength(n1, n2)) {
            int sumOfLastDigits = sumOfLastDigitAtIndex(n1, n2, ++indexOfLastDigit);
            sumOfLastDigits += divisionRemainder;
            divisionRemainder = sumOfLastDigits / 10; // save remainder to variable
            digits.add(0, String.valueOf(sumOfLastDigits % 10));
        }

        if (divisionRemainder != 0) // if we have remainder add it to first position
            digits.add(0, String.valueOf(divisionRemainder));

        return String.join("", digits);
    }

    /**
     * Find max number length
     *
     * @param n1 first number
     * @param n2 second number
     * @return max length of two numbers
     */
    private int getMaxNumberLength(String n1, String n2) {
        return Math.max(n1.length(), n2.length());
    }

    /**
     * Add last digits of two numbers using index of digit position from the right side
     *
     * @param n1    first number
     * @param n2    second number
     * @param index of digit position from the right side
     * @return sum of the rightmost digits
     */
    private int sumOfLastDigitAtIndex(String n1, String n2, int index) {
        int lastDigitNumber1 = getDigitOnReverseIndex(n1, index);
        int lastDigitNumber2 = getDigitOnReverseIndex(n2, index);
        return lastDigitNumber1 + lastDigitNumber2;
    }

    /**
     * Return 0 when out of index
     *
     * @param n     number
     * @param index position of digit position from the right side
     * @return rightmost digit on index position of current number
     */
    private int getDigitOnReverseIndex(String n, int index) {
        return index <= n.length() ? n.charAt(n.length() - index) - '0' : 0;
    }
}