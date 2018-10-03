package com.implemica.model.operations.simple;

import com.implemica.model.exceptions.UndefinedResultException;
import com.implemica.model.operations.SimpleOperation;

import java.math.BigDecimal;
import java.math.MathContext;

public class Divide extends SimpleOperation {

    public Divide(){
        super();
        character = "/";
    }

    @Override
    public BigDecimal calculate(BigDecimal result) throws UndefinedResultException {
        if(result.compareTo(BigDecimal.ZERO) == 0 &&
            operand.compareTo(BigDecimal.ZERO) == 0) {
            throw new UndefinedResultException(result, operand);
        }
        if(this.isShowOperand() || !operand.equals(BigDecimal.ZERO))
            return result.divide(operand, MathContext.DECIMAL128);
        else return result;
    }
}
