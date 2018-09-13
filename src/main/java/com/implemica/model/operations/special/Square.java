package com.implemica.model.operations.special;

import com.implemica.model.operations.Operation;

import java.math.BigDecimal;

public class Square extends Operation {
   @Override
   public BigDecimal calculate(BigDecimal result) {
      return result.pow(2);
   }

   @Override
   public StringBuilder buildHistory(StringBuilder history) {
      return new StringBuilder().append("sqr(").append(history).append(")");
   }
}
