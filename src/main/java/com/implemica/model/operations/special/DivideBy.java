package com.implemica.model.operations.special;

import com.implemica.model.interfaces.SpecialOperation;
import com.implemica.model.operations.Operation;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public class DivideBy implements SpecialOperation {

   @Override
   public BigDecimal calculate(BigDecimal result) {
      return new BigDecimal(BigInteger.ONE).divide(result, MathContext.DECIMAL64);
   }

   @Override
   public String buildHistory(String history) {
      return "1/(" + history + ")";
   }
}
