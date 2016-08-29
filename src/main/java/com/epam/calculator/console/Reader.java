package com.epam.calculator.console;

import java.util.Scanner;

public class Reader {

    public String readExpression() {
        Scanner scanner = new Scanner(System.in);
        String expression;

        do {
            System.out.println("Enter your expression :");
            expression = scanner.nextLine();

            if (expression.isEmpty()) {
                System.out.println("You entered an empty string!\n______________");
            }
        } while (expression.isEmpty());

        return expression;
    }
}
