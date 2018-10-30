package com.implemica.model.memory;

import com.implemica.model.interfaces.Memory;

import java.math.BigDecimal;
import java.math.MathContext;

public class MemoryImpl implements Memory {

   private BigDecimal value = BigDecimal.ZERO;

   @Override
   public void add(BigDecimal operand) {
      value = value.add(operand);
   }

   @Override
   public void subtract(BigDecimal operand) {
      value = value.subtract(operand);
   }

   @Override
   public BigDecimal recall() {
      return value;
   }

   @Override
   public void clear() {
      value = BigDecimal.ZERO;
   }
}
