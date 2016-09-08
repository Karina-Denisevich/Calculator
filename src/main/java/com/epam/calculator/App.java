package com.epam.calculator;

import com.epam.calculator.console.Reader;
import com.epam.calculator.console.Writer;
import com.epam.calculator.filters.InputFilter;
import com.epam.calculator.filters.OutputFilter;
import com.epam.calculator.utils.Validation;
import com.sun.corba.se.impl.naming.cosnaming.NamingUtils;
import org.apache.commons.lang3.math.NumberUtils;

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
        String answer;

        try {
            answer = new Calculator().calculate(expression);
            System.out.println("Answer = " + new OutputFilter().doFilter(answer));
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
