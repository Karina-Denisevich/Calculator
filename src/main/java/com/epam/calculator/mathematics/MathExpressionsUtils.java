package com.epam.calculator.mathematics;

import org.apache.commons.lang3.math.NumberUtils;

public final class MathExpressionsUtils {

    private MathExpressionsUtils() {
    }

    public static int findOperationSignPosition(String expression) {

        for (int i = 1; i < expression.length() - 1; i++) {

            if (!(NumberUtils.isNumber(expression.substring(i, i + 1))
                    || expression.substring(i, i + 1).equals("."))) {
                if (isNumber(expression.substring(0, i))
                        && isNumber(expression.substring(i + 1, expression.length()))) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static boolean isNumber(String number) {

        return (NumberUtils.isNumber(number) || (number.startsWith("+")
                && NumberUtils.isNumber(number.substring(1, number.length()))));

    }
}
