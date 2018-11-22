package com.implemica.model.operations.operation;

import com.implemica.model.history.OperandHistory;
import com.implemica.model.validation.Validator;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public abstract class SimpleOperation extends Operation {

   @Getter
   @Setter
   protected BigDecimal operand = BigDecimal.ZERO;

   @Getter
   @Setter
   protected BigDecimal initialOperand = BigDecimal.ZERO;

   @Setter
   protected String character;

   @Getter
   protected OperandHistory operandHistory = new OperandHistory();

   @Getter
   @Setter
   protected boolean separated;

   @Getter
   @Setter
   protected boolean showOperand;

   /*constants*/
   private static final char SPACE = ' ';
   private static final String NOTHING = "";
   private static final String SEPARATOR = ".";

   private Validator validator = new Validator();

   public StringBuilder buildHistory() {
      StringBuilder result;
      if(isShowOperand()) {
         result = buildLocalHistory().append(SPACE);
      } else {
         result = new StringBuilder(NOTHING);
      }
      return result.insert(0, getCharacter());
   }

   public void buildOperand(char number) {
      if (!isOverflow()) {
         operand = new BigDecimal(operand.toPlainString() + (separated ? SEPARATOR : NOTHING) + number);
         initialOperand = operand;

         separated = false;
         showOperand = true;
      }

   }

   public void removeLast() {
      if (separated) {
         separated = false;
         return;
      }

      if (operand.toString().length() <= 1) {
         operand = BigDecimal.ZERO;
         initialOperand = BigDecimal.ZERO;
         return;
      }

      if (operand.scale() == 1) {
         separated = true;
      }

      operand = new BigDecimal(operand.toPlainString().substring(0, operand.toPlainString().length() - 1));
      initialOperand = operand;
   }

   private StringBuilder buildLocalHistory() {
      return operandHistory.buildHistory(new StringBuilder(validator.showNumberForHistory(initialOperand)));
   }

   private String getCharacter() {
      return character.equals(NOTHING) ? NOTHING : character + SPACE;
   }

   private boolean isOverflow() {
      int quantityCharsOfIntegerNumber;
      if (operand.toBigInteger().compareTo(BigInteger.ZERO) == 0) {
         quantityCharsOfIntegerNumber = 0;
      } else {
         quantityCharsOfIntegerNumber = quantityCharsOfIntegerNumber(operand);
      }

      return quantityCharsOfIntegerNumber + operand.scale() >= 16;
   }

   private int quantityCharsOfIntegerNumber(BigDecimal number) {
      return number.setScale(0, RoundingMode.DOWN).precision();
   }
}
