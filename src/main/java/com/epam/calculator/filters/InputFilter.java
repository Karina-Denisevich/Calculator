package com.epam.calculator.filters;

import com.epam.calculator.utils.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputFilter implements Filter {

    @Override
    public String doFilter(String expression) {
        StringBuffer expressionCopy = new StringBuffer(deleteSpaces(expression));
        expressionCopy = putMultiplication(expressionCopy);

        return expressionCopy.toString();
    }

    private String deleteSpaces(String expression) {
        return expression.replaceAll(" ", "");
    }

    private StringBuffer putMultiplication(StringBuffer expression) {
        Pattern pattern = Pattern.compile(Patterns.INPUT_FILTER_PATTERN);
        Matcher matcher = pattern.matcher(expression);

        while (matcher.find()) {
            expression.insert(expression.indexOf(matcher.group()) + 1, '*');
        }

        return expression;
    }
}
