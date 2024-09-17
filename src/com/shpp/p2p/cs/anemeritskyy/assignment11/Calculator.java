package com.shpp.p2p.cs.anemeritskyy.assignment11;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This program calculate expression getting from constructor, also support variables
 * for ex. args = {"1 + a * 2", "a = 2"}
 * this is a simple program of calculator and support only -, +, /, *, ^ operations without brackets
 * for simplify running use
 */
public class Calculator {
    /*
    Debug mode for showing details as formula after parsing, formula with replacement variables for fast checking
     */
    public static boolean DEBUG = false;
    /*
    Formula with replaces variables to numbers
     */
    private final Formula formula;
    /*
    The list of total operations in queue of theirs important
     */
    public final static List<Character> OPERATIONS = List.of('^', '/', '*', '-', '+');
    /*
    Result of current calculation
     */
    private double result;

    /**
     * Create an object of current calculation
     * also init included variables and add to the map
     *
     * @param inputtedData inputted by user data for ex. "-1 + -a * 2 * 3.5 - y", "a = 2", "y = -3"
     *                     where first element is formula and others is variables if it presents
     */
    public Calculator(String[] inputtedData) {
        Map<String, Double> variables;
        if (inputtedData.length != 0) {
            variables = new TreeMap<>((o1, o2) -> { // comparator for sorting by length from longest to small
                if (o1.length() > o2.length()) {
                    return -1;
                } else if (o1.length() < o2.length()) {
                    return 1;
                } else return o1.compareTo(o2);
            });

            for (int i = 1; i < inputtedData.length; i++) {
                String withoutSpaces = getStringWithoutSpaces(inputtedData[i]);
                String variable = getVariable(withoutSpaces);
                double value = getValue(withoutSpaces);
                variables.put(variable, value);
            }
        } else throw new ArithmeticException();

        this.formula = new Formula(getStringWithoutSpaces(inputtedData[0]), variables);
        calculate();
    }

    /**
     * Remove all spaces in line
     *
     * @param str inputted line
     * @return line without spaces
     */
    private String getStringWithoutSpaces(String str) {
        return str.replaceAll(" ", "");
    }

    /**
     * Parse variable value
     *
     * @param inputValue value before parse
     * @return value after parse
     */
    private double getValue(String inputValue) {
        return Double.parseDouble(inputValue.substring(inputValue.indexOf("=") + 1)); // Start after =
    }

    /**
     * Parse variable name
     *
     * @param inputVariable value before parse
     * @return value after parse
     */
    private String getVariable(String inputVariable) {
        return inputVariable.substring(0, inputVariable.indexOf("="));
    }

    /**
     * Calculate every operation while list size !1
     */
    public void calculate() {
        LinkedList<Object> scatteredFormula = Parser.scatterFormulaToElements(formula, formula.variables);
        if (DEBUG) {
            System.out.println("After parsing: " + scatteredFormula);
            System.err.println("Formula for checking: " + scatteredFormula.stream().map(Objects::toString).collect(Collectors.joining("")));
        }
        checkBrackets(scatteredFormula);
        scatteredFormula = checkTrigonometry(scatteredFormula);
        doOperations(scatteredFormula);
        result = (double) scatteredFormula.get(0);
    }

    /**
     * Calculate trigonometric functions
     *
     * @param scatteredFormula formula without brackets if it has trigonometric functions, calculate it
     * @return formula after calculation
     */
    private LinkedList<Object> checkTrigonometry(LinkedList<Object> scatteredFormula) {
        LinkedList<Object> calculatedTrigonometryFormula = new LinkedList<>();
        for (int i = 0; i < scatteredFormula.size(); i++) {
            if (scatteredFormula.get(i) instanceof Trigonometry) {
                double result;
                double trigonometryValue = (double) scatteredFormula.get(i + 1);
                switch (scatteredFormula.get(i)) {
                    case Trigonometry.sin -> result = Math.sin(trigonometryValue);
                    case Trigonometry.cos -> result = Math.cos(trigonometryValue);
                    case Trigonometry.tan -> result = Math.tan(trigonometryValue);
                    case Trigonometry.atan -> result = Math.atan(trigonometryValue);
                    case Trigonometry.log10 -> result = Math.log10(trigonometryValue);
                    case Trigonometry.log2 -> result = log2(trigonometryValue);
                    case Trigonometry.sqrt -> result = Math.sqrt(trigonometryValue);
                    default ->
                            throw new IllegalStateException("Unexpected trigonometric function: " + scatteredFormula.get(i));
                }
                calculatedTrigonometryFormula.add(result);
                i++;
            } else {
                calculatedTrigonometryFormula.add(scatteredFormula.get(i));
            }
        }
        return calculatedTrigonometryFormula;
    }

    /**
     * Calculate log base 2
     */
    private double log2(double trigonometryValue) {
        return Math.log(trigonometryValue) / Math.log(2);
    }

    /**
     * Calculate basic operations for inputted formula
     */
    private void doOperations(LinkedList<Object> scatteredFormula) {
        while (scatteredFormula.size() != 1) {
            for (Character operation : OPERATIONS) {
                while (scatteredFormula.contains(operation)) {
                    calculation(scatteredFormula, operation);
                }
            }
        }
    }

    /**
     * Calculate formula in brackets
     */
    private void checkBrackets(LinkedList<Object> scatteredFormula) {
        while (scatteredFormula.contains('(')) {
            int lastOpenIndex = scatteredFormula.lastIndexOf('(');
            int firstClosedIndex = lastOpenIndex + scatteredFormula.subList(lastOpenIndex, scatteredFormula.size()).indexOf(')');
            LinkedList<Object> formulaInBrackets = new LinkedList<>(scatteredFormula.subList((lastOpenIndex + 1), (firstClosedIndex)));
            formulaInBrackets = checkTrigonometry(formulaInBrackets);
            doOperations(formulaInBrackets);
            removeAdjacentValues(scatteredFormula, lastOpenIndex, firstClosedIndex);
            scatteredFormula.add(lastOpenIndex, formulaInBrackets.get(0));
        }
    }

    /**
     * Do every operation in important queue
     *
     * @param scatteredFormula list of all elements
     * @param operation        current operation
     */
    private void calculation(LinkedList<Object> scatteredFormula, char operation) {
        int indexOfOperator = scatteredFormula.indexOf(operation);
        double operand1 = (double) scatteredFormula.get(indexOfOperator - 1);
        double operand2 = (double) scatteredFormula.get(indexOfOperator + 1);
        double operationResult = 0;

        switch (operation) {
            case '^' -> operationResult = Math.pow(operand1, operand2);
            case '/' -> {
                if (operand2 == 0) throw new ArithmeticException();
                operationResult = operand1 / operand2;
            }
            case '*' -> operationResult = operand1 * operand2;
            case '-' -> operationResult = operand1 - operand2;
            case '+' -> operationResult = operand1 + operand2;
        }

        removeAdjacentValues(scatteredFormula, indexOfOperator - 1, indexOfOperator + 1);
        scatteredFormula.add(indexOfOperator - 1, operationResult);
    }

    /**
     * Remove elements in diapason
     *
     * @param scatteredFormula list of elements
     * @param removeFromIndex  index of element remove from
     * @param removeToIndex    index of element remove to include it
     */
    private void removeAdjacentValues(LinkedList<Object> scatteredFormula, int removeFromIndex, int removeToIndex) {
        scatteredFormula.subList(removeFromIndex, removeToIndex + 1).clear();
    }

    /**
     * Print result of calculation into console
     */
    public void showResult() {
        System.out.printf("""
                Formula: %s
                Variables: %s
                Result of calculation %s""", formula.getFormula(), formula.variables, result);
    }

    /**
     * Return result of calculation
     */
    public double getResult() {
        return result;
    }
}