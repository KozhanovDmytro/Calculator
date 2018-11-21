package com.implemica.model.operations.operation;

import com.implemica.model.exceptions.CalculatorException;

import java.math.BigDecimal;

public abstract class Operation {

   public static final int MAX_SCALE = 20000;

   public abstract BigDecimal calculate(BigDecimal result) throws CalculatorException;

}
