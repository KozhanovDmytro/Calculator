package com.implemica.model.operations.special;

import com.implemica.model.interfaces.SpecialOperation;

import java.math.BigDecimal;
import java.math.MathContext;

public class SquareRoot implements SpecialOperation {

   @Override
   public BigDecimal calculate(BigDecimal result) {
      MathContext mc = MathContext.DECIMAL64;
      return result.sqrt(mc);
   }

   @Override
   public String buildHistory(String history) {
      return "√(" + history + ")";
   }
}
