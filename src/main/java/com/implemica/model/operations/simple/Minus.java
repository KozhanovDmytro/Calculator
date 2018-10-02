package com.implemica.model.operations.simple;

import com.implemica.model.operations.SimpleOperation;

import java.math.BigDecimal;
import java.math.MathContext;

public class Minus extends SimpleOperation {

    public Minus(){
        super();
        character = "-";
    }

    @Override
    public BigDecimal calculate(BigDecimal result) {
        return result.subtract(operand, MathContext.DECIMAL128);
    }
}
