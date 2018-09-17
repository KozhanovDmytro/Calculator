package com.implemica.model.operations.special;

import com.implemica.model.interfaces.SpecialOperation;

import java.math.BigDecimal;

public class Percent implements SpecialOperation {
//   public BigDecimal calculate(BigDecimal result, BigDecimal operand){
//      return result.multiply(operand).divide(new BigDecimal(100));
//   }

   private BigDecimal result;

   private String history;

   public Percent(BigDecimal result){
      this.result = result;
   }

   @Override
   public BigDecimal calculate(BigDecimal operand) {
      if(operand.equals(BigDecimal.ZERO)){
         operand = new BigDecimal(result.toPlainString());
      }
      BigDecimal result = this.result.multiply(operand).divide(new BigDecimal(100));
      history = result.toPlainString();
      return result;
   }

   @Override
   public StringBuilder buildHistory(StringBuilder history) {
      return new StringBuilder(history);
   }
}
