package com.epam.calculator.mathematics;

public class Division implements Mathematics {

    @Override
    public double compute(double a, double b) {

        if (b != 0) {
            return a / b;
        } else {
            throw new IllegalArgumentException("Division by zero!");
        }
    }
}
