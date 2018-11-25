package com.implemica.model.history;

import com.implemica.model.operations.Default;
import com.implemica.model.operations.operation.SimpleOperation;

import java.util.Deque;
import java.util.LinkedList;

/**
 * The class contains history of {@link SimpleOperation}.
 *
 * For make a history in string format use {@link #buildHistory()} function
 * as a result will be displayed like that example:
 *
 *       5 + 3 - 7 * 2 / 1
 *
 *
 * @see SimpleOperation
 * @see Deque
 * @see LinkedList
 *
 * @author Dmytro Kozhanov
 */
public class MainHistory implements Cloneable {

   /** Store a {@link Deque} of {@link SimpleOperation}*/
   private Deque<SimpleOperation> operations;

   /**
    * Constructor.
    */
   public MainHistory() {
      operations = new LinkedList<>();
   }

   /**
    * The private constructor needed for create clone.
    *
    * @see this#clone()
    * @param operations the {@link Deque} of {@link SimpleOperation}
    */
   private MainHistory(LinkedList<SimpleOperation> operations) {
      this.operations = operations;
   }

   /**
    * Add an instance of {@link SimpleOperation}.
    *
    * Note! The first operation which would be add must be {@link Default} operation. For
    * that use {@link #firstAdd(Default, SimpleOperation)}
    *
    * @param operation element to add.
    */
   public void add(SimpleOperation operation) {
      operations.add(operation);
   }

   /**
    * Function for add the instance of {@link SimpleOperation} firstly
    * with the {@link Default} operation together.
    *
    * @param firstOperation first operation to add - {@link Default}
    * @param secondOperation second operation to add - {@link SimpleOperation}
    */
   public void firstAdd(Default firstOperation, SimpleOperation secondOperation) {
      if(operations.size() == 0) {
         operations.add(firstOperation);
         operations.add(secondOperation);
      }
   }

   /**
    * Clear history.
    */
   public void clear() {
      operations.clear();
   }

   /**
    * @return size of history
    */
   public int size() {
      return operations.size();
   }

   /**
    * Change last {@link SimpleOperation} in history to
    * another {@link SimpleOperation}.
    *
    * @param operation another operation which would be replaced.
    */
   public void changeLast(SimpleOperation operation) {
      operations.removeLast();
      add(operation);
   }

   /**
    * This function build history.
    *
    * @return the instance of {@link String} which gets a string
    *  representation of history.
    */
   public String buildHistory() {
      StringBuilder result = new StringBuilder();
      for (SimpleOperation a : operations) {
         result.append(a.buildHistory());
      }
      return result.toString();
   }

   /**
    * Function hides last operation. It means that function {@link #buildHistory()} ignore this
    * operation.
    */
   public void hideLast() {
      if (operations.size() > 0) {
         operations.getLast().setShowOperand(false);
      }
   }

   /**
    * Function for make a clone of this history.
    * @return cloned history.
    */
   @Override public MainHistory clone() {
      return new MainHistory(new LinkedList<>(operations));
   }
}
