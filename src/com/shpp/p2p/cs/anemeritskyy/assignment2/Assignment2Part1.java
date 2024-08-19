package com.shpp.p2p.cs.anemeritskyy.assignment2;

import com.shpp.cs.a.console.TextProgram;

import java.util.InputMismatchException;

/**
 * this program get roots of a quadratic equation based on user input
 * result: get roots of a quadratic equation or linear equation
 */
public class Assignment2Part1 extends TextProgram {
    private double a, b, c, x1, x2, discriminant;
    private boolean isFullQuadratic = true;

    @Override
    public void run() {
        try {
            getInputData();
        } catch (InputMismatchException e) {
            isFullQuadratic = false;
            println("Your data is not valid, use only numbers");
        }
        if (isFullQuadratic) {
            quadraticEquation();
        } else if (a != 0) {
            linearEquation();
        }
    }

    /**
     * resolve and display result of quadratic equation on the basis of formulas
     */
    private void quadraticEquation() {
        calculateDiscriminant();
        calculationRoots();
        displayQuadraticResults();
    }

    /**
     * resolve and display result of linear equation on the basis of formulas
     */
    private void linearEquation() {
        if (b == 0 && c == 0) {
            displayResults(0);
            return;
        }

        if (b == 0) {
            double calculation = -(c / a);
            if (calculation > 0) {
                displayResults(Math.sqrt(calculation), -Math.sqrt(calculation));
            } else {
                displayResultsWithoutRoots();
            }
            return;
        }

        if (c == 0) {
            displayResults(0, -(b / a));
        }
    }

    /**
     * calculate discriminant based on formula
     * D = b^2−4ac
     */
    private void calculateDiscriminant() {
        discriminant = Math.pow(b, 2) - 4 * a * c;
    }

    /**
     * return: result depending on discriminant
     */
    private void displayQuadraticResults() {
        if (discriminant < 0)
            displayResultsWithoutRoots();

        if (discriminant > 0) {
            displayResults(x1, x2);
        } else if (!Double.isNaN(x1)) {
            displayResults(x1);
        }
    }

    private void displayResultsWithoutRoots() {
        println("There are no real roots");
    }

    private void displayResults(double root1, double root2) {
        println("There are two roots: " + root1 + " and " + root2);
    }

    private void displayResults(double root) {
        println("There is one root: " + root);
    }

    /**
     * getting roots based on formulas:
     * x1 = (-b+√D)/2a
     * x2 = (-b-√D)/2a
     */
    private void calculationRoots() {
        x1 = (-b + Math.sqrt(discriminant)) / (2 * a);
        x2 = (-b - Math.sqrt(discriminant)) / (2 * a);
    }


    /**
     * get data in command line from user
     * result: incoming data for solving quadratic equation
     */
    private void getInputData() {
        a = readDouble("Please enter a: ");

        // an exception when it is impossible to solve the equation
        if (a == 0) {
            println("This is not quadratic equations");
            isFullQuadratic = false;
            return;
        }

        b = readDouble("Please enter b: ");
        c = readDouble("Please enter c: ");

        // an exception when quadratic equations is not full
        if (b == 0 || c == 0) {
            isFullQuadratic = false;
        }
    }
}