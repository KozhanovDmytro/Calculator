package com.implemica.model.operations.simple;

import com.implemica.model.operations.Operation;

import java.math.BigDecimal;

public class Plus extends Operation {

    @Override
    public BigDecimal calculate(BigDecimal result) {
        return operand.add(result);
    }

    @Override
    public StringBuilder buildHistory(StringBuilder history) {
        return history.append(" + ").append(operand.toString());
    }
}
