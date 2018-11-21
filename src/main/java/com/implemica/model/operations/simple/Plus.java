package com.implemica.model.operations.simple;

import com.implemica.model.operations.operation.SimpleOperation;

import java.math.BigDecimal;

public class Plus extends SimpleOperation {

   public Plus() {
      super();
      character = "+";
   }

   @Override
   public BigDecimal calculate(BigDecimal result) {
      return operand.add(result);
   }
}
