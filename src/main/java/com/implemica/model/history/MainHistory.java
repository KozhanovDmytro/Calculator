package com.implemica.model.history;

import com.implemica.model.operations.Default;
import com.implemica.model.operations.operation.SimpleOperation;

import java.util.ArrayDeque;

public class MainHistory implements Cloneable {

   private ArrayDeque<SimpleOperation> operations;

   public MainHistory() {
      operations = new ArrayDeque<>();
   }

   private MainHistory(ArrayDeque<SimpleOperation> operations) {
      this.operations = operations;
   }

   public void add(SimpleOperation operation) {
      operations.add(operation);
   }

   public void firstAdd(Default firstOperation, SimpleOperation secondOperation) {
      operations.add(firstOperation);
      operations.add(secondOperation);
   }

   public void clear() {
      operations.clear();
   }

   public int size() {
      return operations.size();
   }

   public void changeLast(SimpleOperation operation) {
      operations.removeLast();
      add(operation);
   }

   public String buildHistory() {
      StringBuilder result = new StringBuilder();
      for (SimpleOperation a : operations) {
         result.append(a.buildHistory());
      }
      return result.toString();
   }

   public void hideLast() {
      if (operations.size() > 0) {
         operations.getLast().setShowOperand(false);
      }
   }

   @Override
   public MainHistory clone() {
      return new MainHistory(this.operations.clone());
   }
}
