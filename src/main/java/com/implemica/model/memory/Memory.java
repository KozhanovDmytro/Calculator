package com.implemica.model.memory;

import java.math.BigDecimal;

/**
 * This class contains a memory.
 *
 * @see BigDecimal
 *
 * @author Dmytro Kozhanov
 */
public class Memory {

   /** Store a memory. */
   private BigDecimal value = BigDecimal.ZERO;

   /**
    * Add to memory.
    * @param operand number to add.
    */
   public void add(BigDecimal operand) {
      value = value.add(operand);
   }

   /**
    * Subtract from memory.
    * @param operand number to subtract.
    */
   public void subtract(BigDecimal operand) {
      value = value.subtract(operand);
   }

   /**
    * Recall memory.
    * @return memory
    */
   public BigDecimal recall() {
      return value;
   }

   /**
    * Clear memory.
    */
   public void clear() {
      value = BigDecimal.ZERO;
   }
}
