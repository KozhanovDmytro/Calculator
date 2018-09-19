package com.implemica.model.operations.simple;

import com.implemica.model.operations.Operation;

import java.math.BigDecimal;

public class Multiply extends Operation {

    public Multiply(){
        super();
        character = "*";
    }

    @Override
    public BigDecimal calculate(BigDecimal result) {
        if(this.isShowOperand())
            return result.multiply(operand);
        else return result;
    }
}
