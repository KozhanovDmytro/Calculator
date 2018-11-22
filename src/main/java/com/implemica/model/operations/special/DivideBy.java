package com.implemica.model.operations.special;

import com.implemica.model.exceptions.CalculatorException;
import com.implemica.model.exceptions.ExceptionType;
import com.implemica.model.operations.operation.SpecialOperation;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class DivideBy extends SpecialOperation {

   @Override
   public BigDecimal calculate(BigDecimal result) throws CalculatorException {
      if (result.compareTo(BigDecimal.ZERO) == 0) {
         throw new CalculatorException(ExceptionType.DIVIDE_BY_ZERO);
      }

      return new BigDecimal(BigInteger.ONE).divide(result, MAX_SCALE, RoundingMode.HALF_UP);
   }

   @Override
   public StringBuilder buildHistory(StringBuilder history) {
//      return "1/(" + history + ")";
      return new StringBuilder().append("1/(").append(history).append(")");
   }
}
