package com.implemica.model.operations.special;

import com.implemica.model.operations.operation.SpecialOperation;

import java.math.BigDecimal;
import java.math.MathContext;

public class Percent extends SpecialOperation {

   private BigDecimal result;

   @Override
   public BigDecimal calculate(BigDecimal operand) {
      if (operand.equals(BigDecimal.ZERO)) {
         operand = result;
      }
      return this.result.multiply(operand).divide(new BigDecimal(100), MathContext.DECIMAL64);
   }

   public void setResult(BigDecimal result) {
      this.result = result;
   }
}
