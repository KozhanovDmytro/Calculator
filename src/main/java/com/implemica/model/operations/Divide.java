package com.implemica.model.operations;

import java.math.BigDecimal;

public class Divide extends Operation {

    @Override
    public BigDecimal calculate(BigDecimal result) {
        return result.divide(operand);
    }

    @Override
    public StringBuilder buildHistory(StringBuilder history) {
        return history.append(" / ").append(operand.toString());
    }
}
