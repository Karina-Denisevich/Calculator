package com.epam.calculator.mathematics;

public class Exponentiation implements Mathematics {

    @Override
    public double compute(double a, double b) {
        return Math.pow(a, b);
    }
}
