package com.epam.calculator.console;

public class Writer {

    public void printWelcomeMessage() {
        System.out.println("It is a simple calculator that can add, multiple, divide,\n" +
                "subtract and raise to the power. Also you can use round brackets. \n" +
                "This calculator allows you to calculate the expression of this type: \n" +
                "2^(2(3/(2-5(4-5)))*2.3)\n" +
                "-5.002/-4^-1*18*-2+-3\n" +
                " 44.7E-4/100\n");
    }
}
