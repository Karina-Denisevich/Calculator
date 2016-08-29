package com.epam.calculator.utils;

public class Validation {

    public boolean isExpressionValid(String expression) {

        if (expression.matches(Patterns.VALID_EXPRESSION_PATTERN)) {
            return true;
        } else {
            System.out.println("Expression is not valid!\n___________");
            return false;
        }
    }
}
