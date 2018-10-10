package com.implemica.model.memory;

import com.implemica.model.interfaces.Memory;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.MathContext;

public class MemoryImpl implements Memory {

   private BigDecimal value = BigDecimal.ZERO;

   private boolean show;

   @Override
   public void add(BigDecimal operand) {
      value = value.add(operand, MathContext.UNLIMITED);
      show = true;
   }

   @Override
   public void subtract(BigDecimal operand) {
      value = value.subtract(operand, MathContext.UNLIMITED);
      show = true;
   }

   @Override
   public BigDecimal recall() {
      return show ? value : null;
   }

   @Override
   public void clear() {
      value = BigDecimal.ZERO;
      show = false;
   }
}
