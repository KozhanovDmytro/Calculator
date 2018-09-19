package com.implemica.model.operations;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class Equals extends Operation {

    @Getter
    @Setter
    private Operation lastOperation;

    @Setter
    private String result;

    public Equals(Operation lastOperation) {
        this.lastOperation = lastOperation;
    }

    @Override
    public BigDecimal calculate(BigDecimal result) {
        return lastOperation.calculate(result);
    }

    @Override
    public String buildHistory() {
        return result;
    }
}
