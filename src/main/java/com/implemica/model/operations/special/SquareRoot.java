package com.implemica.model.operations.special;

import com.implemica.model.exceptions.InvalidInputException;
import com.implemica.model.interfaces.SpecialOperation;

import java.math.BigDecimal;
import java.math.MathContext;

public class SquareRoot implements SpecialOperation {

   @Override
   public BigDecimal calculate(BigDecimal result) throws InvalidInputException {
      if(result.compareTo(BigDecimal.ZERO) < 0) {
         throw new InvalidInputException(result);
      }
      return result.sqrt(MathContext.DECIMAL64);
   }

   @Override
   public String buildHistory(String history) {
      return "√(" + history + ")";
   }
}
