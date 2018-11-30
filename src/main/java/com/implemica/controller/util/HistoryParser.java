package com.implemica.controller.util;

import com.implemica.model.history.History;
import com.implemica.model.operations.operation.SimpleOperation;
import com.implemica.model.operations.operation.SpecialOperation;
import com.implemica.model.operations.simple.Divide;
import com.implemica.model.operations.simple.Minus;
import com.implemica.model.operations.simple.Multiply;
import com.implemica.model.operations.simple.Plus;
import com.implemica.model.operations.special.*;

import java.util.LinkedList;

/**
 * This class provide to parse an instance {@link History}.
 *
 * @see History
 * @see SimpleOperation
 * @see SpecialOperation
 * @see StringBuilder
 *
 * @author Dmytro Kozhanov
 */
public class HistoryParser {

   /** Validator needed for make comfortable number. */
   private Validator validator = new Validator();

   /**
    * This function intended for parse {@link History} object and return
    * as string object.
    *
    * @param history the instance will be parsed
    * @return string representational of history.
    */
   public String parse(History history) {
      StringBuilder result = new StringBuilder();

      for (SimpleOperation simpleOperation : history.getOperations()) {
         parseSpecialOperation(simpleOperation, result);
      }

      return result.toString();
   }

   /**
    * Parse {@link SimpleOperation}'s history.
    *
    * @param simpleOperation instance will parsed.
    * @param result instance where build history.
    */
   private void parseSpecialOperation(SimpleOperation simpleOperation, StringBuilder result) {
      result.append(getCharacter(simpleOperation));

      if(isPercent(simpleOperation.getOperandHistory())) {
         result.append(validator.showNumberForHistory(simpleOperation.getOperand()));
      } else {
         buildHistoryWithoutPercent(simpleOperation, result);
      }
   }

   /**
    * Build history without percent.
    *
    * @param simpleOperation instance will parsed.
    * @param result instance where build history.
    */
   private void buildHistoryWithoutPercent(SimpleOperation simpleOperation, StringBuilder result) {
      for (SpecialOperation specialOperation : simpleOperation.getOperandHistory()) {
         result.append(getFirstCharacterForSpecialOperation(specialOperation));
      }

      if(simpleOperation.isShowOperand()) {
         result.append(validator.showNumberForHistory(simpleOperation.getInitialOperand()));
      }

      for (SpecialOperation specialOperation : simpleOperation.getOperandHistory()) {
         result.append(getSecondCharacterForSpecialOperation(specialOperation));
      }
   }

   /**
    * @param operations special operation
    * @return whether SpecialOperation has {@link Percent} or not.
    */
   private boolean isPercent(LinkedList<SpecialOperation> operations) {
      if(operations.size() == 0) {
         return false;
      } else {
         return operations.getFirst() instanceof Percent;
      }
   }

   private String getCharacter(SimpleOperation operation) {
      String result = "";

      if(operation instanceof Plus) {
         result = " + ";
      } else if(operation instanceof Minus) {
         result = " - ";
      } else if(operation instanceof Multiply) {
         result = " × ";
      } else if(operation instanceof Divide) {
         result = " ÷ ";
      }

      return result;
   }

   private String getFirstCharacterForSpecialOperation(SpecialOperation specialOperation) {
      String result = "";

      if(specialOperation instanceof SquareRoot) {
         result = "√(";
      } else if (specialOperation instanceof Square) {
         result = "sqr(";
      } else if (specialOperation instanceof DivideBy) {
         result = "1/(";
      } else if( specialOperation instanceof Negate) {
         result = "negate(";
      }

      return result;
   }

   private String getSecondCharacterForSpecialOperation(SpecialOperation specialOperation) {
      String result = "";

      if (!(specialOperation instanceof Percent)) {
         result = ")";
      }

      return result;
   }
}
