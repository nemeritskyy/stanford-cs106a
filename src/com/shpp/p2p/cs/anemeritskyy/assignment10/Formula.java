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
    public Map<String, Double> variables;

    /**
     * Constructor with adding leading zero if starts with unary
     *
     * @param formula   formula inputted by user
     * @param variables collection of variables
     */
    public Formula(String formula, Map<String, Double> variables) {
        this.formula = formula;
        this.variables = variables;
        if (this.formula.startsWith("-")) {
            this.formula = 0 + this.formula;
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
