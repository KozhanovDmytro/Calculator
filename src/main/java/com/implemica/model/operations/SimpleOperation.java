package com.implemica.model.operations;

import com.implemica.model.history.OperandHistoryImpl;
import com.implemica.model.interfaces.History;
import com.implemica.model.interfaces.Operation;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

@Getter
@Setter
public abstract class SimpleOperation implements Operation {

    protected BigDecimal operand;

    protected String stringOperand;

    protected String character;

    protected History localHistory;

    private boolean separated;

    private boolean showOperand;

    public SimpleOperation(){
        operand = new BigDecimal(BigInteger.ZERO, MathContext.DECIMAL64);
        stringOperand = operand.toPlainString();
        localHistory = new OperandHistoryImpl();
        showOperand = false;
    }

    public String buildHistory() {
        return " " + character + (isShowOperand() ? " " + buildLocalHistory() : "");
    }

    public void buildOperand(char number) {
        if(operand.toBigInteger().toString().length() + operand.scale() < 16) {
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
        return ((OperandHistoryImpl)localHistory).buildHistory(this.getStringOperand());
    }
}
