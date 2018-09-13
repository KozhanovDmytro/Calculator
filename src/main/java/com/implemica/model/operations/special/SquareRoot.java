package com.implemica.model.operations.special;

import com.implemica.model.operations.Operation;

import java.math.BigDecimal;
import java.math.MathContext;

public class SquareRoot extends Operation {

   @Override
   public BigDecimal calculate(BigDecimal result) {
      MathContext mc = MathContext.DECIMAL128;
      return result.sqrt(mc);
   }

   @Override
   public StringBuilder buildHistory(StringBuilder history) {
      return new StringBuilder().append("sqrt(").append(history).append(")");
   }
}
