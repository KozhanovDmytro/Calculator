package com.implemica.model.operations;

import java.math.BigDecimal;

public class Default extends SimpleOperation {

    public Default() {
        super();
        this.character = "";
    }
    public Default(BigDecimal operand){
        this();
        this.operand = operand;
        this.stringOperand = operand.toString();
        this.setShowOperand(true);
    }

    @Override
    public BigDecimal calculate(BigDecimal result) {
        return this.operand;
    }
}
