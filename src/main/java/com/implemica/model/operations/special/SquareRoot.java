package com.implemica.model.operations.special;

import com.implemica.model.exceptions.CalculatorException;
import com.implemica.model.exceptions.ExceptionType;
import com.implemica.model.operations.operation.SpecialOperation;

import java.math.BigDecimal;
import java.math.MathContext;

public class SquareRoot extends SpecialOperation {

   @Override
   public BigDecimal calculate(BigDecimal operand) throws CalculatorException {
      if (operand.compareTo(BigDecimal.ZERO) < 0) {
         throw new CalculatorException(ExceptionType.INVALID_INPUT);
      }

      return operand.sqrt(MathContext.DECIMAL64);
   }

   @Override
   public StringBuilder buildHistory(StringBuilder history) {
      return history.insert(0, "√(").append(")");
   }
}
