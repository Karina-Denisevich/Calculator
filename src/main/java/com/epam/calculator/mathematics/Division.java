package com.epam.calculator.mathematics;

public class Division implements Mathematics {

    @Override
    public Double compute(double a, double b) throws ArithmeticException {

        if (b != 0) {
            return a / b;
        } else {
            throw new ArithmeticException("Division by zero!");
        }
    }
}
