package com.implemica.model.exceptions;

import java.math.BigDecimal;

@Deprecated
public class OverflowException extends Exception {

   public OverflowException() {
   }

   public OverflowException(BigDecimal overflowedValue) {
      super(String.format("This value was overflowed. Number: %s", overflowedValue.toEngineeringString()));
   }
}
