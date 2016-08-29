package com.epam.calculator.filters;

public class OutputFilter implements Filter {

    @Override
    public String doFilter(String expression) {

        if (expression.startsWith(".")) {
            return "0" + expression;
        } else if (expression.endsWith(".")) {
            return expression + "0";
        }

        return expression;
    }
}
