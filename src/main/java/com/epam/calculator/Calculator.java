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

import static com.epam.calculator.utils.Patterns.OPERATION_SIGNS;

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

        return calculateSimpleExpression(openBrackets(new StringBuffer(expression)));
    }

    private StringBuffer openBrackets(StringBuffer expression) throws IllegalArgumentException {

        if (expression.length() != 0) {
            Pattern pattern = Pattern.compile(Patterns.EXPRESSION_IN_BRACKETS_PATTERN);
            Matcher matcher = pattern.matcher(expression);

            while (matcher.find()) {
                StringBuffer simpleExpression = new StringBuffer(matcher.group()
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

    private String calculateSimpleExpression(StringBuffer expression) throws IllegalArgumentException {

        expression = calculateByPriority(expression, firstPriorityOperations);
        expression = calculateByPriority(expression, secondPriorityOperations);
        expression = calculateByPriority(expression, thirdPriorityOperations);

        return expression.toString();
    }

    private StringBuffer calculateByPriority(StringBuffer expression,
                                             Map<Character, Mathematics> operations)
            throws IllegalArgumentException {

        List<String> opSignsPriority = operations.keySet().stream().map(key -> "\\" + key
                + "|").collect(Collectors.toList());

        if (expression.length() != 0) {

            Pattern pattern = Pattern.compile("(^(\\+|\\-))?[^" + OPERATION_SIGNS + "]+("
                    + opSignsPriority + ")(\\+|\\-)?[^" + OPERATION_SIGNS + "]+");
            Matcher matcher = pattern.matcher(expression);

            while (matcher.find()) {
//                System.out.println(matcher.group()); // Each step
                int signIndex = findOperationSignPosition(matcher.group());
                char opSign = matcher.group().charAt(signIndex);

                Double a = 0.0;
                Double b = 0.0;
                try {
                    a = Double.parseDouble(matcher.group().substring(0, signIndex));
                    b = Double.parseDouble(matcher.group().substring(signIndex + 1,
                            matcher.group().length()));
                } catch (NumberFormatException ex) {
                    System.err.println("Parse exception: " + ex.getMessage());
                    System.exit(1);
                }

                Mathematics maths = operations.get(opSign);
                String result = "";

                try {
                    result = maths.compute(a, b).toString();
                } catch (ArithmeticException ex) {
                    System.err.println(ex.getMessage());
                    System.exit(1);
                }
                replaceOnSimplifiedPart(expression, matcher.group(), result);

                if (expression.length() != 0) {
                    matcher = pattern.matcher(expression);
                }
            }

            operations.keySet().stream().filter(ch -> expression.indexOf(ch.toString()) != -1
                    && !NumberUtils.isNumber(expression.toString())).forEach(ch -> {
                throw new IllegalArgumentException("Invalid format of the expression!");
            });
        }

        return expression;
    }

    private StringBuffer replaceOnSimplifiedPart(StringBuffer expression,
                                                 String target, String replacement) {

        int startIndex = expression.indexOf(target);
        int endIndex = startIndex + target.length();
        expression.replace(startIndex, endIndex, replacement);

        return expression;
    }

    private int findOperationSignPosition(String expression) {

        for (int i = 1; i < expression.length() - 1; i++) {
            if (NumberUtils.isNumber(expression.substring(i - 1, i))
                    && !NumberUtils.isNumber(expression.substring(i, i + 1))
                    && !expression.substring(i, i + 1).equals(".")) {
                return i;
            }
        }
        return -1;
    }
}
//2^5-4*-2/14-4.2*5-14.34/12.3^2 = 11.476643721141041
//-5.002/-4^-1*18*-2+-3 = -723.288
//2^(2(3/(2-5(4-5)))*2.3) = 3.921562439759093