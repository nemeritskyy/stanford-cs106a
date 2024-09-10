package com.shpp.p2p.cs.anemeritskyy.assignment10;

/**
 * This program calculate expression getting from args, also support variables
 * for ex. args = {"1 + a * 2", "a = 2"}
 * this is a simple program of calculator and support only -, +, /, *, ^ operations without brackets
 * for simplify running use class Run in this package
 */
public class Assignment10Part1 {
    public static void main(String[] args) {
        Calculator calculator = new Calculator(args);
        calculator.showResult();
    }
}