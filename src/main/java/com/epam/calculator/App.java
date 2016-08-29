package com.epam.calculator;

import com.epam.calculator.console.Reader;
import com.epam.calculator.console.Writer;
import com.epam.calculator.filters.InputFilter;
import com.epam.calculator.utils.Validation;

public class App {

    public static void main(String[] args) {
        Writer writer = new Writer();
        Reader reader = new Reader();
        Validation validation = new Validation();

        writer.printWelcomeMessage();
        String expression;

        do {
            expression = reader.readExpression();
        } while (!validation.isExpressionValid(expression));

        expression = new InputFilter().doFilter(expression);
        String answer = "";

        try {
            answer = new Calculator().calculate(expression);
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            System.exit(1);
        }
        System.out.println("Answer = " + answer);
    }
}
