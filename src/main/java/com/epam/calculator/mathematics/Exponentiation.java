package com.epam.calculator.mathematics;

public class Exponentiation implements Mathematics {

    @Override
    public Double compute(double a, double b) throws ArithmeticException {

        if (a < 0 && b % 1 != 0) {
            throw new ArithmeticException("Extracting of root of a negative number!");
        } else if (a == 0 && b < 0) {
            throw new ArithmeticException("Division by zero!");
        }

        return Math.pow(a, b);
    }
}
