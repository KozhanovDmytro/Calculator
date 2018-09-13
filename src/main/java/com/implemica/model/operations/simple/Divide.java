package com.implemica.model.operations.simple;

import com.implemica.model.operations.Operation;

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
