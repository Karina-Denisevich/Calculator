package com.epam.calculator.mathematics;

public class Addition implements Mathematics {

    @Override
    public double compute(double a, double b) {
        return a + b;
    }
}
