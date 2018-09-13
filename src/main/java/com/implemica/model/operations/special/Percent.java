package com.implemica.model.operations.special;

import java.math.BigDecimal;

public class Percent {
   public BigDecimal calculate(BigDecimal result, BigDecimal operand){
      return result.multiply(operand).divide(new BigDecimal(100));
   }
}
