package com.implemica.model.operations;

import com.implemica.model.history.OperandHistory;
import com.implemica.model.interfaces.History;
import com.implemica.model.interfaces.Operation;
import com.implemica.model.validation.Validator;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

public abstract class SimpleOperation implements Operation {

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

   public String buildHistory() {
      return getCharacter() + (isShowOperand() ? buildLocalHistory() + SPACE : NOTHING);
   }

   public void buildOperand(char number) {
      if (!isOverflow()) {
         operand = new BigDecimal(operand.toPlainString() + (separated ? SEPARATOR : NOTHING) + number);
         initialOperand = operand;
         this.setSeparated(false);
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

   private String buildLocalHistory() {
      return operandHistory.buildHistory(validator.showNumberForHistory(initialOperand));
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
