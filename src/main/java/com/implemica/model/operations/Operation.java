package com.implemica.model.operations;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

@Getter
@Setter
public abstract class Operation {

    protected BigDecimal operand;

    protected String character;

    private boolean separated;

    private boolean showOperand;

    public Operation(){
        operand = new BigDecimal(BigInteger.ZERO, MathContext.DECIMAL64);
        showOperand = false;
    }

    public Operation(double number){
        this.operand = new BigDecimal(number);
        showOperand = false;
    }

    abstract public BigDecimal calculate(BigDecimal result);

    public String buildHistory() {
        return " " + character + (isShowOperand() ? " " + operand.toString() : "");
    }

    public void buildOperand(char number) {
        if(operand.toBigInteger().toString().length() + operand.scale() < 16) {
            String separator;
            if(this.isSeparated())
                separator = ".";
            else
                separator = "";
            operand = new BigDecimal(operand.toPlainString() + separator + number);

            this.setSeparated(false);
        }
    }

    public void removeLast(){
        if(operand.toString().length() > 1)
            operand = new BigDecimal(operand.toPlainString().substring(0, operand.toPlainString().length() - 1));
        else
            operand = new BigDecimal(BigInteger.ZERO);
    }

    @Override
    public String toString(){
        return this.getClass().getName();
    }

    // TODO method for edited operand. This function intended for making history.
}
