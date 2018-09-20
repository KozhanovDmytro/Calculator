package com.implemica.model.operations.special;

import com.implemica.model.interfaces.SpecialOperation;

import java.math.BigDecimal;

public class Negate implements SpecialOperation {

   @Override
   public BigDecimal calculate(BigDecimal operand) {
      return operand.negate();
   }

   @Override
   public String buildHistory(String history) {
      return "negate(" + history + ")";
   }
}
