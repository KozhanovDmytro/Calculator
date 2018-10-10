package com.implemica.model.exceptions;

import java.math.BigDecimal;

public class UndefinedResultException extends Exception {
   
   public UndefinedResultException() {
   }

   public UndefinedResultException(BigDecimal result, BigDecimal operand) {
      super(String.format("Result is undefined. Result : %s. Your operand is : %s",
              result.toString(), operand.toString()));
   }
}
