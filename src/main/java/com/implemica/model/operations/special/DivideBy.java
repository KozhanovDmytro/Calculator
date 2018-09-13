package com.implemica.model.operations.special;

import com.implemica.model.operations.Operation;

import java.math.BigDecimal;
import java.math.BigInteger;

public class DivideBy extends Operation {

   @Override
   public BigDecimal calculate(BigDecimal result) {
      return new BigDecimal(BigInteger.ONE).divide(result);
   }

   @Override
   public StringBuilder buildHistory(StringBuilder history) {
      return new StringBuilder().append("1/(").append(history).append(")");
   }
}
