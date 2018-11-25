package com.implemica.model.operations.operation;

import com.implemica.model.history.OperandHistory;
import com.implemica.model.validation.Validator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public abstract class SimpleOperation extends Operation {

   protected BigDecimal operand = BigDecimal.ZERO;

   protected BigDecimal initialOperand = BigDecimal.ZERO;

   protected String character;

   private OperandHistory operandHistory = new OperandHistory();

   private boolean separated;

   private boolean isShowOperand;

   /*constants*/
   private static final char SPACE = ' ';
   private static final String NOTHING = "";
   private static final String SEPARATOR = ".";

   private Validator validator = new Validator();

   public StringBuilder buildHistory() {
      StringBuilder result;
      if(isShowOperand) {
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
         isShowOperand = true;
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

   public void setOperandFromMemory(BigDecimal number) {
      operand = number;
      initialOperand = number;
      isShowOperand = true;
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

   public BigDecimal getOperand() {
      return operand;
   }

   public void setOperand(BigDecimal operand) {
      this.operand = operand;
   }

   public void setInitialOperand(BigDecimal initialOperand) {
      this.initialOperand = initialOperand;
   }

   public OperandHistory getOperandHistory() {
      return operandHistory;
   }

   public boolean isSeparated() {
      return separated;
   }

   public void setSeparated(boolean separated) {
      this.separated = separated;
   }

   public boolean isShowOperand() {
      return isShowOperand;
   }

   public void setShowOperand(boolean showOperand) {
      this.isShowOperand = showOperand;
   }
}
