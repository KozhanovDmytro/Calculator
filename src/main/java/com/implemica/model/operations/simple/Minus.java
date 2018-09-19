package com.implemica.model.operations.simple;

import com.implemica.model.operations.Operation;

import java.math.BigDecimal;

public class Minus extends Operation {

    public Minus(){
        character = "-";
    }

    @Override
    public BigDecimal calculate(BigDecimal result) {
        return result.subtract(operand);
    }
}
