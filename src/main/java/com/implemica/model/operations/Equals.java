package com.implemica.model.operations;

import com.implemica.model.exceptions.InvalidInputException;
import com.implemica.model.exceptions.UndefinedResultException;
import com.implemica.model.operations.operation.SimpleOperation;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class Equals extends SimpleOperation {

   @Getter
   @Setter
   private SimpleOperation lastOperation;

   @Setter
   private String result;

   public Equals(SimpleOperation lastOperation) {
      this.lastOperation = lastOperation;
      character = "";
   }

   @Override
   public BigDecimal calculate(BigDecimal result) throws UndefinedResultException, InvalidInputException {
      return lastOperation.calculate(result);
   }

   @Override
   public String buildHistory() {
      return result;
   }
}
