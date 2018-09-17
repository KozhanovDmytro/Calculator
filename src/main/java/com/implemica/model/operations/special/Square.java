package com.implemica.model.operations.special;

import com.implemica.model.interfaces.SpecialOperation;
import com.implemica.model.operations.Operation;

import java.math.BigDecimal;

public class Square implements SpecialOperation {

   @Override
   public BigDecimal calculate(BigDecimal result) {
      return result.pow(2);
   }

   @Override
   public StringBuilder buildHistory(StringBuilder history) {
      return new StringBuilder().append("sqr(").append(history).append(")");
   }
}
