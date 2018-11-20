package com.implemica.model.history;

import com.implemica.model.interfaces.History;
import com.implemica.model.interfaces.SpecialOperation;

import java.util.ArrayDeque;

public class OperandHistory implements History<SpecialOperation> {

   private ArrayDeque<SpecialOperation> operations;

   public OperandHistory() {
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
   public int size() {
      return operations.size();
   }

   @Override
   public void changeLast(SpecialOperation operation) {
      operations.removeLast();
      add(operation);
   }

   @Override
   @Deprecated
   public String buildHistory() {
      return buildHistory("");
   }

   public String buildHistory(String operand) {
      String result = operand;

      for (SpecialOperation sc : operations) {
         result = sc.buildHistory(result);
      }

      return result;
   }

   @Override
   @Deprecated
   public void hideLast() {}
}
