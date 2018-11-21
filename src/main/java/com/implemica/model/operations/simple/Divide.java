package com.implemica.model.operations.simple;

import com.implemica.model.exceptions.CalculatorException;
import com.implemica.model.exceptions.ExceptionType;
import com.implemica.model.operations.operation.SimpleOperation;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Divide extends SimpleOperation {

   public Divide() {
      super();
      character = "÷";
   }

   @Override
   public BigDecimal calculate(BigDecimal result) throws CalculatorException {
      if (result.compareTo(BigDecimal.ZERO) == 0 && operand.compareTo(BigDecimal.ZERO) == 0 && isShowOperand()) {
         throw new CalculatorException(ExceptionType.UNDEFINED_RESULT);
      }

      if (operand.compareTo(BigDecimal.ZERO) == 0 && isShowOperand()) {
         throw new CalculatorException(ExceptionType.DIVIDE_BY_ZERO);
      }

      if (isShowOperand() || !operand.equals(BigDecimal.ZERO)) {
         return result.divide(operand, MAX_SCALE, RoundingMode.HALF_UP);
      } else {
         return result;
      }
   }
}
