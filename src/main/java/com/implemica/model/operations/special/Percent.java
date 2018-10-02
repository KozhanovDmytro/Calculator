package com.implemica.model.operations.special;

import com.implemica.model.interfaces.SpecialOperation;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.MathContext;

public class Percent implements SpecialOperation {

   @Setter
   private BigDecimal result;

   private String history;

   public Percent(){
      result = BigDecimal.ZERO;
   }

   public Percent(BigDecimal result){
      this.result = result;
   }

   @Override
   public BigDecimal calculate(BigDecimal operand) {
      if(operand.equals(BigDecimal.ZERO)){
         operand = new BigDecimal(result.toPlainString());
      }
      BigDecimal result = this.result.multiply(operand).divide(new BigDecimal(100), MathContext.DECIMAL128);
      history = result.toPlainString();
      return result;
   }

   @Override
   public String buildHistory(String history) {
      return this.history;
   }
}
