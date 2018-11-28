package com.implemica.model.operations.operation;

import com.implemica.model.exceptions.CalculatorException;

import java.math.BigDecimal;

public abstract class SpecialOperation {

   protected final int MAX_SCALE = 20000;

   protected String firstPartHistory;

   protected String secondPartHistory;

   /**
    * Function which have to override to change operand according to some kind of logic.
    *
    * @param operand initial operand.
    * @return changed operand.
    * @throws CalculatorException if something was thrown.
    */
   public abstract BigDecimal calculate(BigDecimal operand) throws CalculatorException;

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
