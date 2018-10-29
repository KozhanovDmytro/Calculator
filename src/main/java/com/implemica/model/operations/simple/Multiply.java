package com.implemica.model.operations.simple;

import com.implemica.model.operations.SimpleOperation;

import java.math.BigDecimal;

public class Multiply extends SimpleOperation {

    public Multiply(String character) {
        super(character);
    }

    @Override
    public BigDecimal calculate(BigDecimal result) {
        if(isShowOperand() || !operand.equals(BigDecimal.ZERO))
            return result.multiply(operand);
        else return result;
    }
}
