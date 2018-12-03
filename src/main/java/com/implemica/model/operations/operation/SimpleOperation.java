package com.implemica.model.operations.operation;

import com.implemica.model.exceptions.CalculatorException;

import java.math.BigDecimal;
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

   /** History for operand. */
   private LinkedList<SpecialOperation> operandHistory = new LinkedList<>();

   /** The flag which indicate whether show operand in history or not.  */
   private boolean isShowOperand;

   /*constants*/

   /** Max scale. */
   public static final int MAX_SCALE = 20000;

   /**
    * Function which have to override to change result according to some kind of logic.
    *
    * @param result initial result.
    * @return changed result.
    * @throws CalculatorException if something was thrown.
    */
   public abstract BigDecimal calculate(BigDecimal result) throws CalculatorException;

   /**
    * Function build operand and returns current state of model.
    *
    * @param operand wanted number
    */
   public void buildOperand(BigDecimal operand) {
      this.operand = operand;
      this.initialOperand = operand;

      isShowOperand = true;
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

   public boolean isShowOperand() {
      return isShowOperand;
   }

   public void setShowOperand(boolean showOperand) {
      this.isShowOperand = showOperand;
   }
}
