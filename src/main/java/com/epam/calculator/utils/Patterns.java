package com.epam.calculator.utils;

public final class Patterns {

    public static final String INPUT_FILTER_PATTERN = "((\\d|\\)|\\.){1}\\()|(\\)(\\d|\\(|\\.){1})";
    public static final String EXPRESSION_IN_BRACKETS_PATTERN = "\\(([^\\(\\)])+\\)";
    public static final String VALID_EXPRESSION_PATTERN = "(\\d|(\\dE(\\+|\\-)?\\d)|\\.|\\(|\\)" +
            "|\\+|-|\\/|\\*|\\^|\\s)+";
    public static final String OPERATION_SIGNS = "^*/+-";
    public static final String FIRST_NUMBER_PATTERN = "((\\*|/|^)(\\+|\\-))?(\\d|\\.)+(E(\\+|\\-)?(\\d)+)?";
    public static final String SECOND_NUMBER_PATTERN = "(\\+|\\-)?(\\d|\\.)+(E(\\+|\\-)?(\\d)+)?";

    private Patterns() {
    }
}