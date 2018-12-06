package com.implemica.model.operations.simple;

import com.implemica.model.operations.operation.SimpleOperation;

import java.math.BigDecimal;

public class Minus extends SimpleOperation {

   /**
    * This function subtract result by operand.
    *
    * @param result initial result.
    * @return subtracted number
    */
   @Override public BigDecimal calculate(BigDecimal result) {
      return result.subtract(operand);
   }
}
