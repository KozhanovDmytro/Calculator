package com.implemica.model.operations.special;

import com.implemica.model.interfaces.SpecialOperation;
import com.implemica.model.operations.Operation;

import java.math.BigDecimal;
import java.math.MathContext;

public class SquareRoot implements SpecialOperation {

   @Override
   public BigDecimal calculate(BigDecimal result) {
      MathContext mc = MathContext.DECIMAL64;
      return result.sqrt(mc);
   }

   @Override
   public StringBuilder buildHistory(StringBuilder history) {
      return new StringBuilder().append("√(").append(history).append(")");
   }
}
