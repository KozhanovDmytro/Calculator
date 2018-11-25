package com.implemica.model.operations;

import com.implemica.model.exceptions.CalculatorException;
import com.implemica.model.operations.operation.SimpleOperation;

import java.math.BigDecimal;

public class Equals extends SimpleOperation {

   private SimpleOperation lastOperation;

   public Equals(SimpleOperation lastOperation) {
      this.lastOperation = lastOperation;
      character = "";
   }

   @Override
   public BigDecimal calculate(BigDecimal result) throws CalculatorException {
      return lastOperation.calculate(result);
   }

   @Override
   public StringBuilder buildHistory() {
      return new StringBuilder();
   }
}
