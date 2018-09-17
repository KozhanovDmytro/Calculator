package com.implemica.model.operations.special;

import com.implemica.model.interfaces.SpecialOperation;
import com.implemica.model.operations.Operation;

import java.math.BigDecimal;

public class Negate implements SpecialOperation {

   @Override
   public BigDecimal calculate(BigDecimal operand) {
      return operand.negate();
   }

   @Override
   public StringBuilder buildHistory(StringBuilder history) {
      return new StringBuilder().append("negate(").append(history).append(")");
   }
}
