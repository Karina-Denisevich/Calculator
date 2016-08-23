package com.epam.calculator;

import com.epam.calculator.console.Reader;
import com.epam.calculator.console.Writer;

public class App {

    public static void main(String[] args) {

        Writer writer = new Writer();
        Reader reader = new Reader();

        writer.printWelcomeMessage();

        String expression = reader.readExpression();
    }
}
