package com.implemica.model.history;

import com.implemica.model.interfaces.OperandHistory;
import com.implemica.model.interfaces.SpecialOperation;

import java.math.BigDecimal;
import java.util.ArrayDeque;

public class OperandHistoryImpl implements OperandHistory {

   private ArrayDeque<SpecialOperation> operations;

   public OperandHistoryImpl() {
      operations = new ArrayDeque<>();
   }

   @Override
   public void add(SpecialOperation operation) {
      operations.add(operation);
   }

   @Override
   public void clear() {
      operations.clear();
   }

   @Override
   public String buildHistory(BigDecimal operand) {
      String result = operand.toString();

      for (SpecialOperation sc : operations) {
         result = sc.buildHistory(result);
      }

      return result;
   }
}
