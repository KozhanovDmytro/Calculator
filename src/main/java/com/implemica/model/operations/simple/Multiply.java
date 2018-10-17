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
        MathContext context = MathContext.UNLIMITED;
        if(result.scale() > 16 || operand.scale() > 16)
            context = MathContext.DECIMAL64;

        if(this.isShowOperand() || !operand.equals(BigDecimal.ZERO))
            return result.multiply(operand, context);
        else return result;
    }
}
