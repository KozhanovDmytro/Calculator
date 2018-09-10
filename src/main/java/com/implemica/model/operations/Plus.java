package com.implemica.model.operations;

import java.math.BigDecimal;

public class Plus extends Operation {

    public Plus(double number){
        super(number);
    }

    public Plus() {
        super();
    }

    @Override
    public BigDecimal calculate(BigDecimal result) {
        return operand.add(result);
    }

    @Override
    public StringBuilder buildHistory(StringBuilder history) {
        return history.append(" + ").append(operand.toString());
    }
}
