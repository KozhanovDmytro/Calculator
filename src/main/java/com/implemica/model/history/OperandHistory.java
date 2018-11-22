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

   public int size() {
      return operations.size();
   }

   public void changeLast(SpecialOperation operation) {
      operations.removeLast();
      add(operation);
   }

   public StringBuilder buildHistory(StringBuilder operand) {
      StringBuilder result = operand;

      for (SpecialOperation sc : operations) {
         result = sc.buildHistory(result);
      }

      return result;
   }
}
