package com.implemica.model.exceptions;

import java.math.BigDecimal;

public class InvalidInputException extends Exception {

   public InvalidInputException() {
   }

   public InvalidInputException(BigDecimal invalidNumber) {
      super(String.format("This number is invalid. Number: %s", invalidNumber.toEngineeringString()));
   }
}
