package com.implemica.model.operations.special;

import com.implemica.model.exceptions.CalculatorException;
import com.implemica.model.exceptions.ExceptionType;
import com.implemica.model.operations.operation.SpecialOperation;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class DivideBy extends SpecialOperation {

   @Override
   public BigDecimal calculate(BigDecimal operand) throws CalculatorException {
      if (operand.compareTo(BigDecimal.ZERO) == 0) {
         throw new CalculatorException(ExceptionType.DIVIDE_BY_ZERO);
      }

      return new BigDecimal(BigInteger.ONE).divide(operand, MAX_SCALE, RoundingMode.HALF_UP);
   }

   @Override
   public StringBuilder buildHistory(StringBuilder history) {
      return history.insert(0, "1/(").append(")");
   }
}
