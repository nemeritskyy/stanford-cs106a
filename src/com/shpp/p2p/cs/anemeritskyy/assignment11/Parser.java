package com.shpp.p2p.cs.anemeritskyy.assignment11;

import java.util.LinkedList;
import java.util.Map;

/**
 * This class is assistant for parsing inputted formula
 */
public class Parser {
    /**
     * Regex to check operators in line
     */
    private static final String REGEX = "[//^/*+-]";

    /**
     * Scatted every element from formula to personal cell in list, for number it pushed double, for operations as character
     *
     * @param inputtedFormula formula already with numbers without variables
     * @return list of parsed elements
     */
    public static LinkedList<Object> scatterFormulaToElements(Formula inputtedFormula, Map<String, Double> variables) {
        String formula = inputtedFormula.getFormula();
        LinkedList<Object> scatteredFormula = new LinkedList<>();
        boolean isPreviousNumber = false;
        StringBuilder currentElement = new StringBuilder();
        for (int i = 0; i < formula.length(); i++) {
            for (Trigonometry func : Trigonometry.values()) { // parse trigonometric functions
                if (formula.substring(i).startsWith(func.value)) {
                    scatteredFormula.add(func);
                    i += func.value.length();
                }
            }
            for (Map.Entry<String, Double> variable : variables.entrySet()) { // replace variables
                if (formula.substring(i).startsWith(variable.getKey())) {
                    formula = formula.substring(0, i) + formula.substring(i).replaceFirst(variable.getKey(), String.valueOf(variable.getValue()));
                }
            }
            if (formula.charAt(i) == '(') {
                scatteredFormula.add(formula.charAt(i));
                continue;
            }
            if (formula.charAt(i) == ')') {
                if (!currentElement.isEmpty())
                    scatteredFormula.add(Double.parseDouble(currentElement.toString()));
                scatteredFormula.add(formula.charAt(i));
                currentElement = new StringBuilder();
                continue;
            }
            if (matchesSymbolsOnIndex(i, formula) && isPreviousNumber) {
                if (!currentElement.isEmpty())
                    scatteredFormula.add(Double.parseDouble(currentElement.toString()));
                scatteredFormula.add(formula.charAt(i));
                isPreviousNumber = false;
            } else {
                if (!isPreviousNumber) {
                    currentElement = new StringBuilder();
                    isPreviousNumber = true;
                }
                currentElement.append(formula.charAt(i));
            }
        }

        if (!currentElement.isEmpty()) {
            if (isPreviousNumber) { // for last element
                scatteredFormula.add(Double.parseDouble(currentElement.toString()));
            } else {
                scatteredFormula.add(currentElement);
            }
        }
        return scatteredFormula;
    }

    /**
     * Check if symbol on index in formula is operation
     *
     * @param index   of checking element in formula
     * @param formula inputted formula
     * @return true if current element is operation
     */
    private static boolean matchesSymbolsOnIndex(int index, String formula) {
        return String.valueOf(formula.charAt(index)).matches(REGEX);
    }
}
