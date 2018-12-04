package com.implemica.model.calculator;

import com.implemica.model.exceptions.CalculatorException;
import com.implemica.model.exceptions.ExceptionType;
import com.implemica.model.history.History;
import com.implemica.model.memory.Memory;
import com.implemica.model.operations.Default;
import com.implemica.model.operations.Equals;
import com.implemica.model.operations.operation.SimpleOperation;
import com.implemica.model.operations.operation.SpecialOperation;
import com.implemica.model.operations.special.Percent;

import java.math.BigDecimal;

/**
 * The class which stores current result, operation, history and memory.
 *
 * @see BigDecimal
 * @see SimpleOperation
 * @see History
 *
 * @author Dmytro Kozhanov
 */
public class Container {

   /** The result of all operations. */
   private BigDecimal result = BigDecimal.ZERO;

   /** Current operation. */
   private SimpleOperation operation = new Default();

   /** Current history. */
   private History history = new History();

   /** Memory. */
   private Memory memory = new Memory();

   /*constants*/

   /** The MAX number in Model. */
   private final BigDecimal MAX = new BigDecimal("1e10000");

   /** The MIN positive number in Model. */
   private final BigDecimal MIN = new BigDecimal("1e-10000");

   /** A number that represents as zero. */
   private final BigDecimal CLOSER_TO_ZERO = new BigDecimal("1e-19950");

   /**
    * This function calculate {@link #result}
    *
    * @see SimpleOperation
    * @see ExceptionType
    *
    * @throws CalculatorException throws current {@link SimpleOperation}
    */
   public void calculate() throws CalculatorException {
      result = this.operation.calculate(result);
      result = checkForZero(result);
      checkOverflow(result);
      addToHistoryDefaultOperation();
   }

   /**
    * The function calculate specific {@link SpecialOperation} and
    * put result into operand {@link Container#operation}.
    *
    * @see ExceptionType
    * @see SpecialOperation
    *
    * @param specialOperation specific {@link SpecialOperation}
    * @throws CalculatorException throws if something does not
    *  satisfy the conditions described in {@link ExceptionType}
    */
   void change(SpecialOperation specialOperation, boolean isResult) throws CalculatorException {
      if (specialOperation instanceof Percent) {
         ((Percent) specialOperation).setResult(result);
         operation.getOperandHistory().clear();
      }

      if (history.size() == 0 && operation instanceof Equals) {
         operation = new Default((Equals) operation, result);
      }

      BigDecimal newOperand;
      operation.getOperandHistory().addFirst(specialOperation);
      if (isResult && !operation.isMadeOperand()) {
         operation.setInitialOperand(result);
         newOperand = resolveSpecialOperation(specialOperation);

      } else {
         addToHistoryDefaultOperation();
         newOperand = specialOperation.calculate(getOperation().getOperand());

      }

      if(specialOperation instanceof Percent) {
         operation.setInitialOperand(newOperand);
      }

      operation.setMadeOperand(true);
      operation.setOperand(checkForZero(newOperand));
      checkOverflow(operation.getOperand());
   }

   /**
    * Function add to {@link History} new {@link SimpleOperation}.
    *
    * @param simpleOperation new operation which will add to history.
    */
   void addToHistory(SimpleOperation simpleOperation) {
      if (history.size() == 0) {
         history.firstAdd(new Default(result), simpleOperation);
      } else if (this.operation.isMadeOperand()) {
         history.add(simpleOperation);
      } else {
         history.changeLast(simpleOperation);
      }
   }

   /**
    * Function resolve special operation from result besides {@link Percent}
    *
    * @param operation special operation
    * @return calculated special operation
    * @throws CalculatorException throws if something does not
    *  satisfy the conditions described in {@link ExceptionType}
    */
   private BigDecimal resolveSpecialOperation(SpecialOperation operation) throws CalculatorException {
      BigDecimal number;
      if (operation instanceof Percent) {
         number = getOperation().getOperand();
      } else {
         number = result;
      }
      return operation.calculate(number);
   }

   /**
    * function add to history first operation - {@link Default}.
    */
   private void addToHistoryDefaultOperation() {
      if (operation instanceof Default && getHistory().size() == 0) {
         getHistory().add(operation);
         operation.setMadeOperand(true);
      }
   }

   /**
    * Function check number for out from this range:
    *      [-{@link #MAX} .. -{@link #MIN}] and 0 and [{@link #MIN} .. {@link #MAX}]
    *
    * @param number number for checking
    * @throws CalculatorException throw exception if number out of range.
    */
   private void checkOverflow(BigDecimal number) throws CalculatorException {
      if (outFromMaxRange(number) || outFromMinRange(number)) {
         throw new CalculatorException(ExceptionType.OVERFLOW);
      }
   }

   /**
    * Conditional for check that number more than {@link #MAX}.
    *
    * @param number number for checking.
    * @return result of conditional.
    */
   private boolean outFromMaxRange(BigDecimal number) {
      return number.abs().compareTo(MAX) >= 0;
   }

   /**
    * Conditional for check that number less than {@link #MIN} but more than {@link BigDecimal#ZERO}.
    *
    * @param number number for checking.
    * @return result of conditional.
    */
   private boolean outFromMinRange(BigDecimal number) {
      return number.abs().compareTo(BigDecimal.ZERO) > 0 && number.abs().compareTo(MIN) <= 0;
   }

   /**
    * Check number if it closer to zero. If it's true replace this number to {@link BigDecimal#ZERO}.
    * @param number number for checking.
    * @return checked number.
    */
   private BigDecimal checkForZero(BigDecimal number) {
      if (number.abs().compareTo(CLOSER_TO_ZERO) < 0) {
         return BigDecimal.ZERO;
      } else {
         return number;
      }
   }

   /**
    * Accessor for {@link #result}
    * @return {@link #result}
    */
   public BigDecimal getResult() {
      return result;
   }

   /**
    * Accessor for {@link #result}
    */
   public void setResult(BigDecimal result) {
      this.result = result;
   }

   /**
    * Accessor for {@link #operation}
    * @return {@link #operation}
    */
   public SimpleOperation getOperation() {
      return operation;
   }

   /**
    * Accessor for {@link #operation}
    */
   public void setOperation(SimpleOperation operation) {
      this.operation = operation;
   }

   /**
    * Accessor for {@link #history}
    * @return {@link #history}
    */
   public History getHistory() {
      return history;
   }

   /**
    * Accessor for {@link #memory}
    * @return {@link #memory}
    */
   Memory getMemory() {
      return memory;
   }
}