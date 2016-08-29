package com.epam.calculator.utils;

public final class Patterns {

    public static final String INPUT_FILTER_PATTERN = "((\\d|\\)|\\.){1}\\()|(\\)(\\d|\\(|\\.){1})";
    public static final String EXPRESSION_IN_BRACKETS_PATTERN = "\\(([^\\(\\)])+\\)";
    public static final String VALID_EXPRESSION_PATTERN = "(\\d|\\.|\\(|\\)|\\+|-|\\/|" +
            "\\*|\\^|\\s)+";
    public static final String OPERATION_SIGNS = "^*/+-";

    private Patterns() {
    }
}