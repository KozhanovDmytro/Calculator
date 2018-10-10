package com.implemica.model.operations.simple;

import com.implemica.model.operations.SimpleOperation;

import java.math.BigDecimal;
import java.math.MathContext;

public class Plus extends SimpleOperation {

    public Plus(){
        super();
        character = "+";
    }

    @Override
    public BigDecimal calculate(BigDecimal result) {
        return operand.add(result, MathContext.UNLIMITED);
    }
}
