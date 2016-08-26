package com.epam.calculator.mathematics;

public class Addition implements Mathematics {

    @Override
    public Double compute(double a, double b) {
        return a + b;
    }
}
