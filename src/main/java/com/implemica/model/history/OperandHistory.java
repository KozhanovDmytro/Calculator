package com.implemica.model.history;

import com.implemica.model.operations.operation.SpecialOperation;

import java.util.Deque;
import java.util.LinkedList;

/**
 * The class contains history of {@link SpecialOperation}.
 *
 * For make a history in string format use {@link #buildHistory(StringBuilder)} function
 * as a result will be displayed like that example:
 *
 *       1/(sqr(√(9)))
 *
 *
 * @see SpecialOperation
 * @see Deque
 * @see LinkedList
 *
 * @author Dmytro Kozhanov
 */
public class OperandHistory {

   /** Store a {@link Deque} of {@link SpecialOperation}*/
   private Deque<SpecialOperation> operations;

   /**
    * Constructor.
    */
   public OperandHistory() {
      operations = new LinkedList<>();
   }

   /**
    * Add an instance of {@link SpecialOperation}.
    *
    * @param operation element to add.
    */
   public void add(SpecialOperation operation) {
      operations.add(operation);
   }

   /**
    * Clear history.
    */
   public void clear() {
      operations.clear();
   }

   /**
    * This function build history.
    *
    * @return the instance of {@link String} which gets a string
    *  representation of history.
    */
   public StringBuilder buildHistory(StringBuilder operand) {
      for (SpecialOperation sc : operations) {
         sc.buildHistory(operand);
      }

      return operand;
   }
}
