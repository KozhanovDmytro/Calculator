package com.implemica.model.operations;

import com.implemica.model.exceptions.CalculatorException;
import com.implemica.model.operations.operation.SimpleOperation;

import java.math.BigDecimal;

/**
 * Class for make Equals
 */
public class Equals extends SimpleOperation {

   /** Last operation before equals*/
   private SimpleOperation lastOperation;

   /**
    * Constructor.
    * @param lastOperation last operation.
    */
   public Equals(SimpleOperation lastOperation) {
      this.lastOperation = lastOperation;
      character = "";
   }

   @Override public BigDecimal calculate(BigDecimal result) throws CalculatorException {
      return lastOperation.calculate(result);
   }
}
