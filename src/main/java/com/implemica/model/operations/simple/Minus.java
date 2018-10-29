package com.implemica.model.operations.simple;

import com.implemica.model.operations.SimpleOperation;

import java.math.BigDecimal;

public class Minus extends SimpleOperation {

    public Minus(String character) {
        super(character);
    }

    @Override
    public BigDecimal calculate(BigDecimal result) {
        return result.subtract(operand);
    }
}
