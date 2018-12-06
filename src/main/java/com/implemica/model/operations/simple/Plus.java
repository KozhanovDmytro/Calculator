package com.implemica.model.operations.simple;

import com.implemica.model.operations.operation.SimpleOperation;

import java.math.BigDecimal;

public class Plus extends SimpleOperation {

   /**
    * This function add operand to result.
    *
    * @param result initial result.
    * @return added number.
    */
   @Override public BigDecimal calculate(BigDecimal result) {
      return operand.add(result);
   }
}
