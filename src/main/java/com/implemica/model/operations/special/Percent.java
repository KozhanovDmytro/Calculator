package com.implemica.model.operations.special;

import com.implemica.model.operations.operation.SpecialOperation;
import com.implemica.model.validation.Validator;

import java.math.BigDecimal;
import java.math.MathContext;

public class Percent extends SpecialOperation {


   private BigDecimal result;

   private String history;

   private Validator validator = new Validator();

   public Percent() {
      result = BigDecimal.ZERO;
   }

   @Override
   public BigDecimal calculate(BigDecimal operand) {
      if (operand.equals(BigDecimal.ZERO)) {
         operand = new BigDecimal(result.toPlainString());
      }
      BigDecimal result = this.result.multiply(operand).divide(new BigDecimal(100), MathContext.DECIMAL64);
      history = validator.showNumberForHistory(result);
      return result;
   }

   public void setResult(BigDecimal result) {
      this.result = result;
   }
}
