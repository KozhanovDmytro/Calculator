package com.implemica.model.operations;

import java.math.BigDecimal;

public class Default extends Operation {

    public Default() {
        super();
    }
    public Default(BigDecimal operand){
        this.operand = operand;
    }

    @Override
    public BigDecimal calculate(BigDecimal result) {
        return this.operand;
    }

    @Override
    public String buildHistory() {
        return operand.toString();
    }
}
