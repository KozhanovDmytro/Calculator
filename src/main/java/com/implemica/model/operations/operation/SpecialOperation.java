package com.implemica.model.operations.operation;

import com.implemica.model.exceptions.CalculatorException;

import java.math.BigDecimal;

public abstract class SpecialOperation {

   protected final int MAX_SCALE = 20000;

   /**
    * Function which have to override to change operand according to some kind of logic.
    *
    * @param operand initial operand.
    * @return changed operand.
    * @throws CalculatorException if something was thrown.
    */
   public abstract BigDecimal calculate(BigDecimal operand) throws CalculatorException;

   /**
    * Function which involved in create history for operand.
    *
    * @see StringBuilder
    * @return history.
    */
   public abstract StringBuilder buildHistory(StringBuilder history);

}
