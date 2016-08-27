package com.epam.calculator;

import com.epam.calculator.mathematics.*;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Calculator {

    private Map<Character, Mathematics> firstPriorityOperations;
    private Map<Character, Mathematics> secondPriorityOperations;
    private Map<Character, Mathematics> thirdPriorityOperations;
    private final String OPERATION_SIGNS = "^*/+-";

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

    public String calculate(String expression) {
        StringBuffer expressionCopy = new StringBuffer(expression);

        return calculateSimpleExpression(openBrackets(expressionCopy));
    }

    private StringBuffer openBrackets(StringBuffer expression) {

        if (expression.length() != 0) {
            Pattern pattern = Pattern.compile("\\(([^\\(\\)])+\\)");
            Matcher matcher = pattern.matcher(expression);

            while (matcher.find()) {
                StringBuffer simpleExpression = new StringBuffer(matcher.group().substring(1, matcher.group().length() - 1));

                int startIndex = expression.indexOf(matcher.group());
                int endIndex = startIndex + matcher.group().length();
                expression.replace(startIndex, endIndex, calculateSimpleExpression(simpleExpression));

                if (expression.length() != 0) {
                    matcher = pattern.matcher(expression);
                }
            }

            if (expression.indexOf("(") != -1 && expression.indexOf(")") != -1) {
                System.out.println("Bad format of the expression!");
                System.exit(1);
            }
        }

        return expression;
    }

    private String calculateSimpleExpression(StringBuffer expression) {

        expression = calculateByPriority(expression, firstPriorityOperations);
        expression = calculateByPriority(expression, secondPriorityOperations);
        expression = calculateByPriority(expression, thirdPriorityOperations);

        System.out.println("answer = " + expression);
        return expression.toString();
    }

    private StringBuffer calculateByPriority(StringBuffer expression,
                                             Map<Character, Mathematics> operations) {

        List<String> opSigns = operations.entrySet().stream().map(entry -> "\\" + entry.getKey()
                + "|").collect(Collectors.toList());

        if (expression.length() != 0) {
            Pattern pattern = Pattern.compile("(^(\\+|\\-))?[^" + OPERATION_SIGNS + "]+(" + opSigns + ")(\\+|\\-)?[^" + OPERATION_SIGNS + "]+");
            Matcher matcher = pattern.matcher(expression);

            while (matcher.find()) {
//                System.out.println(matcher.group()); // Each step
                Double a = 0.0;
                Double b = 0.0;
                Character opSign = null;

                for (Character ch : operations.keySet()) {

                    if (matcher.group().contains(ch.toString())) {
                        opSign = ch;
                        break;
                    }
                }

                int signIndex = 0;
                for (int i = 1; i < matcher.group().length() - 1; i++) {
                    if (NumberUtils.isNumber(matcher.group().substring(i - 1, i))
                            && !NumberUtils.isNumber(matcher.group().substring(i, i + 1))
                            && !matcher.group().substring(i, i + 1).equals(".")) {
                        signIndex = i;
                        break;
                    }
                }

                try {
                    a = Double.parseDouble(matcher.group().substring(0, signIndex));
                    b = Double.parseDouble(matcher.group().substring(signIndex + 1, matcher.group().length()));
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
                Mathematics maths = operations.get(opSign);

                int startIndex = expression.indexOf(matcher.group());
                int endIndex = startIndex + matcher.group().length();
                expression.replace(startIndex, endIndex, (maths.compute(a, b)).toString());

                if (expression.length() != 0) {
                    matcher = pattern.matcher(expression);
                }
            }

            operations.keySet().stream().filter(ch -> expression.indexOf(ch.toString()) != -1
                    && !NumberUtils.isNumber(expression.toString())).forEach(ch -> {
                System.out.println("Bad format of the expression!");
                System.exit(1);
            });
        }

        return expression;
    }
}
//2^5-4*-2/14-4.2*5-14.34/12.3^2 = 11.476643721141041
//-5.002/-4^-1*18*-2+-3 = -723.288
//2^(2(3/(2-5(4-5)))*2.3) = 3.921562439759093