package com.epam.calculator.mathematics;

import java.io.Serializable;

public class Expression implements Serializable {

    private Double firstOperand;
    private Double secondOperand;
    private char operationSign;

    public Expression() {
    }

    public Double getFirstOperand() {
        return firstOperand;
    }

    public void setFirstOperand(Double firstOperand) {
        this.firstOperand = firstOperand;
    }

    public Double getSecondOperand() {
        return secondOperand;
    }

    public void setSecondOperand(Double secondOperand) {
        this.secondOperand = secondOperand;
    }

    public char getOperationSign() {
        return operationSign;
    }

    public void setOperationSign(char operationSign) {
        this.operationSign = operationSign;
    }
}
