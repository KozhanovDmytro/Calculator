package com.implemica.model.operations.simple;

import com.implemica.model.exceptions.CalculatorException;
import com.implemica.model.exceptions.ExceptionType;
import com.implemica.model.operations.operation.SimpleOperation;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Divide extends SimpleOperation {

   /**
    * Function for dividing operand by result.
    *
    * Function do to divide operation if operand was made by user.
    *
    * ATTENTION! In that model can not dividing by zero. It's not allowed by math rules.
    * If it was eventually divided by zero that function throws {@link CalculatorException}
    * with special {@link ExceptionType}.
    *
    * @see ExceptionType
    *
    * @param result initial result.
    * @return result which was divided by operand
    * @throws CalculatorException if operand or result does not match by rules which described in {@link ExceptionType}
    */
   @Override public BigDecimal calculate(BigDecimal result) throws CalculatorException {
      if (result.compareTo(BigDecimal.ZERO) == 0 && operand.compareTo(BigDecimal.ZERO) == 0 && isMadeOperand()) {
         throw new CalculatorException(ExceptionType.UNDEFINED_RESULT);
      }

      if (operand.compareTo(BigDecimal.ZERO) == 0 && isMadeOperand()) {
         throw new CalculatorException(ExceptionType.DIVIDE_BY_ZERO);
      }

      if (isMadeOperand() || !operand.equals(BigDecimal.ZERO)) {
         return result.divide(operand, MAX_SCALE, RoundingMode.HALF_UP);
      } else {
         return result;
      }
   }
}
