package com.implemica.model.operations.operation;

import com.implemica.model.exceptions.CalculatorException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.LinkedList;

/**
 * Class contains type of simple operation, operand and his history.
 *
 * @see BigDecimal
 *
 * @author Dmytro Kozhanov
 */
public abstract class SimpleOperation {

   /** Operand. */
   protected BigDecimal operand = BigDecimal.ZERO;

   /** Operand that was set for the first time - the original. */
   protected BigDecimal initialOperand = BigDecimal.ZERO;

   /** Character of operation for history. */
   protected String character;

   /** History for operand. */
   private LinkedList<SpecialOperation> operandHistory = new LinkedList<>();

   /** The flag which indicate whether the last character of operand comma or not. */
   private boolean separated;

   /** The flag which indicate whether show operand in history or not.  */
   private boolean isShowOperand;

   /*constants*/
   /** Space. */
   private static final char SPACE = ' ';

   /** Empty string. */
   private static final String NOTHING = "";

   /** Comma. */
   private static final String SEPARATOR = ".";

   /** Max scale. */
   public static final int MAX_SCALE = 20000;

   /** Max precision for build operand. */
   private static int MAX_PRECISION_FOR_BUILD_OPERAND = 16;

   /**
    * Function which have to override to change result according to some kind of logic.
    *
    * @param result initial result.
    * @return changed result.
    * @throws CalculatorException if something was thrown.
    */
   public abstract BigDecimal calculate(BigDecimal result) throws CalculatorException;

   /**
    * Function for build operand.
    *
    * @param number number to add to operand.
    */
   public void buildOperand(Number number) {
      if (!isOverflow()) {
         operand = new BigDecimal(operand.toPlainString() + (separated ? SEPARATOR : NOTHING) + number.translate());
         initialOperand = operand;

         separated = false;
         isShowOperand = true;
      }

   }

   /**
    *  Remove the last character in operand.
    */
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

   /**
    *  Function sets operand from memory.
    */
   public void setOperandFromMemory(BigDecimal number) {
      operand = number;
      initialOperand = number;
      isShowOperand = true;
   }

   /** Gets current character of simple operation for history. */
   public String getCharacter() {
      return character.equals(NOTHING) ? NOTHING : SPACE + character + SPACE;
   }

   /**
    * Function for checking whether operand is full for build operand
    * or not. The MAX number for build operand is {@link #MAX_PRECISION_FOR_BUILD_OPERAND}
    *
    */
   private boolean isOverflow() {
      int quantityCharsOfIntegerNumber;
      if (operand.toBigInteger().compareTo(BigInteger.ZERO) == 0) {
         quantityCharsOfIntegerNumber = 0;
      } else {
         quantityCharsOfIntegerNumber = quantityCharsOfIntegerNumber(operand);
      }

      return quantityCharsOfIntegerNumber + operand.scale() >= MAX_PRECISION_FOR_BUILD_OPERAND;
   }

   /**
    * Function calculates quantity of digit in number.
    * @param number wanted number to calculate
    * @return quantity of digit in number
    */
   private int quantityCharsOfIntegerNumber(BigDecimal number) {
      return number.setScale(0, RoundingMode.DOWN).precision();
   }

   /* accessors */

   public BigDecimal getOperand() {
      return operand;
   }

   public void setOperand(BigDecimal operand) {
      this.operand = operand;
   }

   public void setInitialOperand(BigDecimal initialOperand) {
      this.initialOperand = initialOperand;
   }

   public BigDecimal getInitialOperand() {
      return initialOperand;
   }

   public LinkedList<SpecialOperation> getOperandHistory() {
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

   public void setCharacter(String character) {
      this.character = character;
   }
}
