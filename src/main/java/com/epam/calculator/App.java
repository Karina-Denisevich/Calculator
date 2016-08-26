package com.epam.calculator;

import com.epam.calculator.console.Reader;
import com.epam.calculator.console.Writer;
import com.epam.calculator.filters.InputFilter;

public class App {

    public static void main(String[] args) {
        Writer writer = new Writer();
        Reader reader = new Reader();

        writer.printWelcomeMessage();

        String expression = reader.readExpression();

        expression = new InputFilter().doFilter(expression);

        new Calculator().calculate(expression);
    }
}
