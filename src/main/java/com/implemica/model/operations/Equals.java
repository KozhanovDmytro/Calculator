package com.implemica.model.operations;

import java.math.BigDecimal;

public class Equals extends Operation {

    private Operation lastOperation;

    public Equals(Operation lastOperation){
        this.lastOperation = lastOperation;
        operand = lastOperation.operand;
    }

    @Override
    public BigDecimal calculate(BigDecimal result) {
        return lastOperation.calculate(result);
    }

    @Override
    public StringBuilder buildHistory(StringBuilder history) {
        return new StringBuilder();
    }
}
