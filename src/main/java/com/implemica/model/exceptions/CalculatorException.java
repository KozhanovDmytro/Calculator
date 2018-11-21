package com.implemica.model.exceptions;

import lombok.Getter;

public class CalculatorException extends Exception {

   @Getter
   private ExceptionType exceptionType;

   public CalculatorException(ExceptionType exceptionType) {
      if(exceptionType == ExceptionType.NOTHING) {
         throw new IllegalArgumentException();
      }

      this.exceptionType = exceptionType;
   }
}
