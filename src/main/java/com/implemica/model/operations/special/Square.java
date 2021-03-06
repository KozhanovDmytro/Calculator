package com.implemica.model.operations.special;

import com.implemica.model.operations.operation.SpecialOperation;

import java.math.BigDecimal;
import java.math.MathContext;

public class Square extends SpecialOperation {

   @Override
   public BigDecimal calculate(BigDecimal operand) {
      return operand.pow(2, MathContext.DECIMAL64);
   }
}
