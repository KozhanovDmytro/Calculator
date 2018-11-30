package com.implemica.model.operations;

import com.implemica.model.exceptions.CalculatorException;
import com.implemica.model.operations.operation.SimpleOperation;

import java.math.BigDecimal;

/**
 * Special class which uses as first operation in history.
 *
 * @author Dmytro Kozhanov
 */
public class Default extends SimpleOperation {

   /** The last equals. */
   private Equals lastEquals;

   /**
    * Constructor.
    */
   public Default() {
      super();
   }

   /**
    * Constructor.
    * @param operand initial operand.
    */
   public Default(BigDecimal operand) {
      this();
      this.operand = operand;
      this.initialOperand = operand;
      this.setShowOperand(true);
   }

   /**
    * Constructor.
    *
    * @param lastEquals last equals.
    * @param operand initial operand.
    */
   public Default(Equals lastEquals, BigDecimal operand) {
      this(operand);
      this.lastEquals = lastEquals;
   }

   /**
    * This function is different than another implementation. Function just set operand to result.
    *
    * @param result initial result.
    * @return operand this sets to result.
    */
   @Override public BigDecimal calculate(BigDecimal result) throws CalculatorException {
      if (lastEquals != null) {
         return lastEquals.calculate(this.operand);
      } else {
         return this.operand;
      }
   }
}
