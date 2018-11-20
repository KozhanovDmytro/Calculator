package com.implemica.model.operations.special;

import com.implemica.model.interfaces.SpecialOperation;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class DivideBy extends SpecialOperation {

   @Override
   public BigDecimal calculate(BigDecimal result) {
      return new BigDecimal(BigInteger.ONE).divide(result, MAX_SCALE, RoundingMode.HALF_UP);
   }

   @Override
   public String buildHistory(String history) {
      return "1/(" + history + ")";
   }
}
