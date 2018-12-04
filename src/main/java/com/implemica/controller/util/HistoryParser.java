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

   /** Plus sign */
   private static final String PLUS_SIGN = " + ";

   /** Minus sign */
   private static final String MINUS_SIGN = " - ";

   /** Multiply sign */
   private static final String MULTIPLY_SIGN = " × ";

   /** Divide sign */
   private static final String DIVIDE_SIGN = " ÷ ";

   /** Square root sign */
   private static final String SQRT_SIGN = "√(";

   /** Square sign */
   private static final String SQR_SIGN = "sqr(";

   /** Divide by sign */
   private static final String DIVIDE_BY_SIGN = "1/(";

   /** Negate sign */
   private static final String NEGATE_SIGN = "negate(";

   /** Second part of history sign */
   private static final String SECOND_PART_OF_HISTORY = ")";

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

      if(simpleOperation.isMadeOperand()) {
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

   /**
    * Gets character for {@link SimpleOperation} which was involved in history.
    *
    * @param operation current simple operation.
    * @return character
    */
   private String getCharacter(SimpleOperation operation) {
      String result = "";

      if(operation instanceof Plus) {
         result = PLUS_SIGN;
      } else if(operation instanceof Minus) {
         result = MINUS_SIGN;
      } else if(operation instanceof Multiply) {
         result = MULTIPLY_SIGN;
      } else if(operation instanceof Divide) {
         result = DIVIDE_SIGN;
      }

      return result;
   }

   /**
    * Gets first part of {@link SpecialOperation}'s history.
    *
    * @param specialOperation current special operation
    * @return first part
    */
   private String getFirstCharacterForSpecialOperation(SpecialOperation specialOperation) {
      String result = "";

      if(specialOperation instanceof SquareRoot) {
         result = SQRT_SIGN;
      } else if (specialOperation instanceof Square) {
         result = SQR_SIGN;
      } else if (specialOperation instanceof DivideBy) {
         result = DIVIDE_BY_SIGN;
      } else if( specialOperation instanceof Negate) {
         result = NEGATE_SIGN;
      }

      return result;
   }

   /**
    * Gets first part of {@link SpecialOperation}'s history.
    *
    * @param specialOperation current special operation
    * @return second part
    */
   private String getSecondCharacterForSpecialOperation(SpecialOperation specialOperation) {
      String result = "";

      if (!(specialOperation instanceof Percent)) {
         result = SECOND_PART_OF_HISTORY;
      }

      return result;
   }
}
