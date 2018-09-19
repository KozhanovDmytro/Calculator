package com.implemica.model.operations.simple;

import com.implemica.model.operations.Operation;

import java.math.BigDecimal;
import java.math.MathContext;

public class Divide extends Operation {

    public Divide(){
        super();
        character = "/";
    }

    @Override
    public BigDecimal calculate(BigDecimal result) {
        if(this.isShowOperand())
            return result.divide(operand, MathContext.DECIMAL64);
        else return result;
    }
}
