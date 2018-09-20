package com.implemica.model.operations.special;

import com.implemica.model.interfaces.SpecialOperation;

import java.math.BigDecimal;

public class Square implements SpecialOperation {

   @Override
   public BigDecimal calculate(BigDecimal result) {
      return result.pow(2);
   }

   @Override
   public String buildHistory(String history) {
      return "sqr(" + history + ")";
   }
}
