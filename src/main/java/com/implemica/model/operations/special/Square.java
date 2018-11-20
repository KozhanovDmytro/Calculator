package com.implemica.model.operations.special;

import com.implemica.model.interfaces.SpecialOperation;

import java.math.BigDecimal;
import java.math.MathContext;

public class Square extends SpecialOperation {

   @Override
   public BigDecimal calculate(BigDecimal result) {
      return result.pow(2, MathContext.DECIMAL64);
   }

   @Override
   public String buildHistory(String history) {
      return "sqr(" + history + ")";
   }
}
