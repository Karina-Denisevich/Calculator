package com.epam.calculator.filters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputFilter implements Filter {

    @Override
    public String doFilter(String expression) {
        StringBuffer expressionCopy = new StringBuffer(deleteDelimiters(expression));
        expressionCopy = putMultiplication(expressionCopy);

        return expressionCopy.toString();
    }

    private String deleteDelimiters(String expression) {

        return expression.replaceAll(" ", "");
    }

    private StringBuffer putMultiplication(StringBuffer expression) {
        Pattern pattern = Pattern.compile("(([\\d|\\w]|\\)){1}\\()|(\\)([\\d|\\w]|\\(){1})");
        Matcher matcher = pattern.matcher(expression);

        while (matcher.find()) {

            expression.insert(expression.indexOf(matcher.group()) + 1, '*');
        }

        return expression;
    }
}
