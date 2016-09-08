package com.epam.calculator;

import com.epam.calculator.mathematics.*;
import com.epam.calculator.utils.Patterns;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.epam.calculator.utils.Patterns.FIRST_NUMBER_PATTERN;
import static com.epam.calculator.utils.Patterns.OPERATION_SIGNS;
import static com.epam.calculator.utils.Patterns.SECOND_NUMBER_PATTERN;

public class Calculator {

    private Map<Character, Mathematics> firstPriorityOperations;
    private Map<Character, Mathematics> secondPriorityOperations;
    private Map<Character, Mathematics> thirdPriorityOperations;

    public Calculator() {
        firstPriorityOperations = new LinkedHashMap<>();
        secondPriorityOperations = new LinkedHashMap<>();
        thirdPriorityOperations = new LinkedHashMap<>();
        {
            firstPriorityOperations.put('^', new Exponentiation());
            secondPriorityOperations.put('/', new Division());
            secondPriorityOperations.put('*', new Multiplication());
            thirdPriorityOperations.put('+', new Addition());
            thirdPriorityOperations.put('-', new Subtraction());
        }
    }

    public String calculate(String expression) throws IllegalArgumentException {

        return calculateSimpleExpression(openBrackets(new StringBuilder(expression)));
    }

    private StringBuilder openBrackets(StringBuilder expression) throws IllegalArgumentException {

        if (expression.length() != 0) {
            Pattern pattern = Pattern.compile(Patterns.EXPRESSION_IN_BRACKETS_PATTERN);
            Matcher matcher = pattern.matcher(expression);

            while (matcher.find()) {
                StringBuilder simpleExpression = new StringBuilder(matcher.group()
                        .substring(1, matcher.group().length() - 1));

                expression = replaceOnSimplifiedPart(expression, matcher.group(),
                        calculateSimpleExpression(simpleExpression));

                if (expression.length() > 0) {
                    matcher = pattern.matcher(expression);
                }
            }

            if (expression.indexOf("(") != -1 || expression.indexOf(")") != -1) {
                throw new IllegalArgumentException("Invalid format of the expression! " +
                        "Brackets placed incorrectly.");
            }
        }
        return expression;
    }

    private String calculateSimpleExpression(StringBuilder expression) throws IllegalArgumentException {

        expression = calculateByPriority(expression, firstPriorityOperations);
        expression = calculateByPriority(expression, secondPriorityOperations);
        expression = calculateByPriority(expression, thirdPriorityOperations);

        if (MathExpressionsUtils.isNumber(expression.toString())) {
            return expression.toString();
        } else {
            throw new IllegalArgumentException("Invalid format of the expression!");
        }
    }

    private StringBuilder calculateByPriority(StringBuilder expression,
                                              Map<Character, Mathematics> operations)
            throws IllegalArgumentException {

        List<String> opSignsPriority = operations.keySet().stream().map(key -> "\\" + key
                + "|").collect(Collectors.toList());

        if (expression.length() != 0) {

            Pattern pattern = Pattern.compile(FIRST_NUMBER_PATTERN + '(' + opSignsPriority + ')'
                    + SECOND_NUMBER_PATTERN);
            Matcher matcher = pattern.matcher(expression);

            while (matcher.find()) {
                String matcherGroup;
                // 4*-2^1 - expression is -2^1, but 4-2^1 - expression is 2^1
                if (matcher.group().startsWith("/") || matcher.group().startsWith("*")) {
                    matcherGroup = matcher.group().substring(1, matcher.group().length());
                } else {
                    matcherGroup = matcher.group();
                }
//                System.out.println("step = " + matcherGroup); // Each step

                int signIndex = MathExpressionsUtils.findOperationSignPosition(matcherGroup);
                char opSign = OPERATION_SIGNS.charAt(0);

                try {
                    opSign = matcherGroup.charAt(signIndex);
                } catch (IllegalArgumentException ex) {
                    throw new IllegalArgumentException(ex.getMessage());
                }

                Double a = 0.0;
                Double b = 0.0;
                try {
                    a = Double.parseDouble(matcherGroup.substring(0, signIndex));
                    b = Double.parseDouble(matcherGroup.substring(signIndex + 1,
                            matcherGroup.length()));
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException("Parse exception: " + ex);
                }

                Mathematics maths = operations.get(opSign);
                String result = "";

                try {
                    result = maths.compute(a, b).toString();
                } catch (ArithmeticException ex) {
                    throw new IllegalArgumentException(ex);
                }
                replaceOnSimplifiedPart(expression, matcherGroup, result);

                if (expression.length() != 0) {
                    matcher = pattern.matcher(expression);
                }
            }

            operations.keySet().stream().filter(ch -> expression.indexOf(ch.toString()) != -1
                    && !MathExpressionsUtils.isNumber(expression.toString())).forEach(ch -> {
                throw new IllegalArgumentException("Invalid format of the expression!");
            });
        }

        return expression;
    }

    private StringBuilder replaceOnSimplifiedPart(StringBuilder expression,
                                                  String target, String replacement) {

        int startIndex = expression.indexOf(target);
        int endIndex = startIndex + target.length();
        expression.replace(startIndex, endIndex, replacement);

        return expression;
    }
}
//2^5-4*-2/14-4.2*5-14.34/12.3^2 = 11.476643721141041
//-5.002/-4^-1*18*-2+-3 = -723.288
//2^(2(3/(2-5(4-5)))*2.3) = 3.921562439759093
