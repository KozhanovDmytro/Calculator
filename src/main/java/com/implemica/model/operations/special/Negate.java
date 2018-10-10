package com.implemica.model.operations.special;

import com.implemica.model.interfaces.SpecialOperation;

import java.math.BigDecimal;
import java.math.MathContext;

public class Negate implements SpecialOperation {

   @Override
   public BigDecimal calculate(BigDecimal operand) {
      return operand.negate(MathContext.UNLIMITED);
   }

   @Override
   public String buildHistory(String history) {
      return "negate(" + history + ")";
   }
}
