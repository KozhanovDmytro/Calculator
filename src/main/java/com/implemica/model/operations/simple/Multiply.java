package com.implemica.model.operations.simple;

import com.implemica.model.operations.SimpleOperation;

import java.math.BigDecimal;
import java.math.MathContext;

public class Multiply extends SimpleOperation {

    public Multiply(){
        super();
        character = "*";
    }

    @Override
    public BigDecimal calculate(BigDecimal result) {
        if(this.isShowOperand() || !operand.equals(BigDecimal.ZERO))
            return result.multiply(operand, MathContext.DECIMAL128);
        else return result;
    }
}
