package com.implemica.model.operations.simple;

import com.implemica.model.operations.Operation;

import java.math.BigDecimal;

public class Plus extends Operation {

    public Plus(){
        super();
        character = "+";
    }

    @Override
    public BigDecimal calculate(BigDecimal result) {
        return operand.add(result);
    }
}
