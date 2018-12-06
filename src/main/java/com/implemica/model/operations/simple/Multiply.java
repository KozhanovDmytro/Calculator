package com.implemica.model.operations.simple;

import com.implemica.model.operations.operation.SimpleOperation;

import java.math.BigDecimal;

public class Multiply extends SimpleOperation {

   @Override
   public BigDecimal calculate(BigDecimal result) {
      if (isMadeOperand() || !operand.equals(BigDecimal.ZERO)) {
         return result.multiply(operand);
      } else {
         return result;
      }
   }
}
