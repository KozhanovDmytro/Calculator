package com.implemica.model.history;

import com.implemica.model.interfaces.History;
import com.implemica.model.operations.operation.SimpleOperation;

import java.util.ArrayDeque;

public class MainHistory implements History<SimpleOperation>, Cloneable {

   private ArrayDeque<SimpleOperation> operations;

   public MainHistory() {
      operations = new ArrayDeque<>();
   }

   private MainHistory(ArrayDeque<SimpleOperation> operations) {
      this.operations = operations;
   }

   @Override
   public void add(SimpleOperation operation) {
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
   public void changeLast(SimpleOperation operation) {
      operations.removeLast();
      add(operation);
   }

   @Override
   public String buildHistory() {
      StringBuilder result = new StringBuilder();
      for (SimpleOperation a : operations) {
         result.append(a.buildHistory());
      }
      return result.toString();
   }

   @Override
   public void hideLast() {
      if (operations.size() > 0) {
         operations.getLast().setShowOperand(false);
      }
   }

   @Override
   public MainHistory clone() {
      MainHistory clone = new MainHistory(this.operations.clone());
      return clone;
   }
}
