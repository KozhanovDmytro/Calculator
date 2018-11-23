package com.implemica.model.history;

import com.implemica.model.operations.operation.SpecialOperation;

import java.util.ArrayDeque;

public class OperandHistory {

   private ArrayDeque<SpecialOperation> operations;

   public OperandHistory() {
      operations = new ArrayDeque<>();
   }

   public void add(SpecialOperation operation) {
      operations.add(operation);
   }

   public void clear() {
      operations.clear();
   }

   public StringBuilder buildHistory(StringBuilder operand) {
      for (SpecialOperation sc : operations) {
         sc.buildHistory(operand);
      }

      return operand;
   }
}
