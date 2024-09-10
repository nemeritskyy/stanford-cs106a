package com.shpp.p2p.cs.anemeritskyy.assignment10;

import java.util.Map;

/**
 * This class is util for replacing variables to real number
 */
public class Formula {
    /**
     * Formula without variables
     */
    private String formula;

    /**
     * Constructor replace all variables to numbers
     *
     * @param formula   formula inputted by user
     * @param variables collection of variables
     */
    public Formula(String formula, Map<String, Double> variables) {
        this.formula = formula;
        for (Map.Entry<String, Double> variable : variables.entrySet()) {
            this.formula = this.formula.replaceAll(String.valueOf(variable.getKey()), String.valueOf(variable.getValue()));
        }
    }

    /**
     * Method for returning formula
     *
     * @return formula without variables
     */
    public String getFormula() {
        return formula;
    }

    @Override
    public String toString() {
        return "Formula{" +
                "formula='" + formula + '\'' +
                '}';
    }
}
