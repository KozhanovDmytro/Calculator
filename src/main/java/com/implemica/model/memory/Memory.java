package com.implemica.model.memory;

import java.math.BigDecimal;

public class Memory {

   private BigDecimal value = BigDecimal.ZERO;

   public void add(BigDecimal operand) {
      value = value.add(operand);
   }

   public void subtract(BigDecimal operand) {
      value = value.subtract(operand);
   }

   public BigDecimal recall() {
      return value;
   }

   public void clear() {
      value = BigDecimal.ZERO;
   }
}
