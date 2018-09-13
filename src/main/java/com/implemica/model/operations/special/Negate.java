package com.implemica.model.operations.special;

import com.implemica.model.operations.Operation;

import java.math.BigDecimal;

public class Negate extends Operation {

   @Override
   public BigDecimal calculate(BigDecimal result) {
      return result.negate();
   }

   @Override
   public StringBuilder buildHistory(StringBuilder history) {
      return new StringBuilder().append("negate(").append(history).append(")");
   }
}
