package com.implemica.model.operations;

import com.implemica.model.exceptions.UndefinedResultException;
import lombok.SneakyThrows;

import java.math.BigDecimal;

public class Default extends SimpleOperation {

    private Equals lastEquals;

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

    public Default(Equals lastEquals, BigDecimal operand) {
        this(operand);
        this.lastEquals = lastEquals;
    }

    @Override
    @SneakyThrows
    public BigDecimal calculate(BigDecimal result) {
        if(lastEquals != null) {
            return lastEquals.calculate(this.operand);
        } else {
            return this.operand;
        }
    }
}
