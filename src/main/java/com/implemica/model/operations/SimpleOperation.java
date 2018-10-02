package com.implemica.model.operations;

import com.implemica.model.history.OperandHistory;
import com.implemica.model.interfaces.History;
import com.implemica.model.interfaces.Operation;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public abstract class SimpleOperation implements Operation {

    @Getter
    @Setter
    protected BigDecimal operand;

    @Getter
    @Setter
    protected String stringOperand;

    protected String character;

    @Getter
    protected History operandHistory;

    @Getter
    @Setter
    private boolean separated;

    @Getter
    @Setter
    private boolean showOperand;  //TODO Rename it.

    public SimpleOperation(){
        operand = new BigDecimal(BigInteger.ZERO, MathContext.DECIMAL64);
        stringOperand = operand.toPlainString();
        operandHistory = new OperandHistory();
        showOperand = false;
    }

    public String buildHistory() {
        return getCharacter() + (isShowOperand() ? buildLocalHistory() + " " : "");
    }

    public void buildOperand(char number) {
        if(operand.toBigInteger().toString().length() + operand.scale() <= 16) {
            String separator;
            if(this.isSeparated())
                separator = ".";
            else
                separator = "";
            operand = new BigDecimal(operand.toPlainString() + separator + number);
            stringOperand = operand.toPlainString();

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

    private String buildLocalHistory(){
        return ((OperandHistory) operandHistory).buildHistory(this.getStringOperand());
    }

    private String getCharacter(){
        return character.equals("") ? "" : character + " ";
    }
}
