package com.epam.calculator.filters;

public class OutputFilter implements Filter {

    @Override
    public String doFilter(String expression) {
        return expression;
    }
}
