package com.implemica.model.operations;

import java.math.BigDecimal;

public class Equals extends Operation {

    private Operation lastOperation;

    public Equals(Operation lastOperation){
        this.lastOperation = lastOperation;
    }

    @Override
    public BigDecimal calculate(BigDecimal result) {
        if(operand.equals(BigDecimal.ZERO))
            return lastOperation.calculate(result);
        else
            return lastOperation.calculate(operand);
    }

    @Override
    public StringBuilder buildHistory(StringBuilder history) {
        return new StringBuilder();
    }
}
