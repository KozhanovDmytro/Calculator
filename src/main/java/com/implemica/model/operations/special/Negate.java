package com.implemica.model.operations.special;

import com.implemica.model.operations.operation.SpecialOperation;

import java.math.BigDecimal;

public class Negate extends SpecialOperation {

   @Override
   public BigDecimal calculate(BigDecimal operand) {
      return operand.negate();
   }

   @Override
   public String buildHistory(String history) {
      return "negate(" + history + ")";
   }
}
