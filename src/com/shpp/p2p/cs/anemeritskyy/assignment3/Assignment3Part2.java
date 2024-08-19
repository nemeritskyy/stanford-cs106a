package com.shpp.p2p.cs.anemeritskyy.assignment3;

import com.shpp.cs.a.console.TextProgram;

/**
 * Collatz conjecture
 */
public class Assignment3Part2 extends TextProgram {
    @Override
    public void run() {
        int input = readInt("Enter a number: ");
        collatzConjecture(input);
    }

    /**
     * Check when result is not 1, then we use for
     * even number n / 2
     * odd number 3n + 1
     *
     * @param input - data inputted by user
     */
    private void collatzConjecture(int input) {
        int result = input; // intermediate calculation
        while (result > 1) {
            if (result % 2 == 0) {
                print(result + " is even so I take half: ");
                result /= 2;
            } else {
                print(result + " is odd so I make 3n + 1: ");
                result = result * 3 + 1;
            }
            println(result);
        }
    }
}