package com.implemica.model.operations;

import java.math.BigDecimal;

public class Default extends Operation {

    public Default() {
        super();
    }

    @Override
    public BigDecimal calculate(BigDecimal result) {
        return this.operand;
    }

    @Override
    public StringBuilder buildHistory(StringBuilder history) {
        return history.append(operand.toString());
    }
}
