package com.shpp.p2p.cs.anemeritskyy.assignment3;

import com.shpp.cs.a.console.TextProgram;

import java.util.Locale;

/**
 * Exponentiation
 */
public class Assignment3Part3 extends TextProgram {
    @Override
    public void run() {
        Locale.setDefault(new Locale("en", "US"));
        double base = readDouble("Enter a number: ");
        double exponent = readDouble("Enter an exponent: ");
        double result = raiseToPower(base, exponent);
        println("Result is: " + result);
    }

    /**
     * This method increase base to exponent, also for zero exponent needs to return 1
     *
     * @param base     - base whose need to increase to exponent
     * @param exponent - exponent inputted from user for increase base
     */
    private double raiseToPower(double base, double exponent) {
        if (exponent == 0) return 1;
        double result = base;
        boolean positivePow = isPositive(exponent);
        if (!positivePow)
            exponent *= -1;
        for (int i = 1; i < exponent; i++) {
            result *= base;
        }
        return positivePow ? result : 1 / result;
    }

    /**
     * If pow is negative check it and multiply it *(-1)
     *
     * @param pow - pow inputted by user
     * @return pow after checked
     */
    private boolean isPositive(double pow) {
        return pow > 0;
    }
}