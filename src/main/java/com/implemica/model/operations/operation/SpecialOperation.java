package com.implemica.model.operations.operation;

import com.implemica.model.exceptions.CalculatorException;

import java.math.BigDecimal;

/**
 * Class contains type of special operation and has elements for build history.
 *
 * @see BigDecimal
 *
 * @author Dmytro Kozhanov
 */
public abstract class SpecialOperation {

   /** Max scale. */
   protected final int MAX_SCALE = 20000;

   /** Left part of history. */
   private String firstPartHistory;

   /** Right part of history. */
   private String secondPartHistory;

   /**
    * Function which have to override to change operand according to some kind of logic.
    *
    * @param operand initial operand.
    * @return changed operand.
    * @throws CalculatorException if something was thrown.
    */
   public abstract BigDecimal calculate(BigDecimal operand) throws CalculatorException;

   /* accessors. */

   public String getFirstPartHistory() {
      return firstPartHistory;
   }

   public void setFirstPartHistory(String firstPartHistory) {
      this.firstPartHistory = firstPartHistory;
   }

   public String getSecondPartHistory() {
      return secondPartHistory;
   }

   public void setSecondPartHistory(String secondPartHistory) {
      this.secondPartHistory = secondPartHistory;
   }
}
