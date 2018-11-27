package com.implemica.model.calculator;

import com.implemica.model.dto.ResponseDto;
import com.implemica.model.exceptions.CalculatorException;
import com.implemica.model.exceptions.ExceptionType;
import com.implemica.model.history.MainHistory;
import com.implemica.model.operations.Default;
import com.implemica.model.operations.Equals;
import com.implemica.model.operations.operation.Number;
import com.implemica.model.operations.operation.SimpleOperation;
import com.implemica.model.operations.operation.SpecialOperation;

import java.math.BigDecimal;

/**
 * The main class of Model.
 *
 * @see Container
 * @see ResponseDto
 * @see SimpleOperation
 * @see SpecialOperation
 *
 * @author Dmytro Kozhanov
 */
public class Calculator {

   /** The instance which contains current state of model. */
   private Container container = new Container();

   /** The enumeration represents the type of exception which was thrown. */
   private ExceptionType exceptionType = ExceptionType.NOTHING;

   /** The flag indicates whether the result is returned from the model or not.*/
   private boolean isShownResult;

   /**
    *  This interface contains a function where an exception expect.
    *  That's needed for catching one exception in different place.
    */
   private interface ExceptionSupplier {
      void calculate() throws CalculatorException;
   }

   /**
    * Function for executing {@link SimpleOperation} and return current state of model.
    *
    * @param operation specific simple operation.
    * @return current state of model.
    */
   public ResponseDto executeSimpleOperation(SimpleOperation operation) {
      if(exceptionType == ExceptionType.NOTHING) {
         exceptionType = calculate(() -> calculateSimpleOperation(operation));
      }

      return getCurrentStateForResult();
   }

   /**
    * The function which calculate specific {@link SimpleOperation} and
    * put result into {@link Container}.
    *
    * @param operation specific {@link SimpleOperation}
    * @see ExceptionType
    * @see SimpleOperation
    *
    * @throws CalculatorException The {@link Container} throws it
    * out if something does not satisfy the conditions described
    * in {@link ExceptionType}
    */
   private void calculateSimpleOperation(SimpleOperation operation) throws CalculatorException {
      if(isEquals()) {
         BigDecimal operand = container.getOperation().getOperand();
         Default initialOperation = new Default(operand);
         container.setOperation(initialOperation);
      }

      if(isNotEquals()) {
         container.calculate();
      }

      container.addToHistory(operation);
      container.setOperation(operation);
      container.setMakingOperand(true);
   }

   /**
    * @return boolean value if {@link Container} do not contain {@link Equals} operation.
    */
   private boolean isNotEquals() {
      return !(container.getOperation() instanceof Equals);
   }

   /**
    * The conditional returns boolean value if {@link Container} contains {@link Equals} operation and
    * conditional for showing operand.
    *
    * @return boolean value if {@link Container} contains {@link Equals} operation.
    */
   private boolean isEquals() {
      return container.getOperation() instanceof Equals && container.getOperation().isShowOperand();
   }

   /**
    * Function for executing {@link SpecialOperation} and returns current state of model.
    *
    * @param operation specific {@link SpecialOperation}.
    * @return current state of model.
    */
   public ResponseDto executeSpecialOperation(SpecialOperation operation) {
      if(exceptionType == ExceptionType.NOTHING) {
         exceptionType = calculate(() -> container.change(operation, isShownResult));
      }

      return getCurrentState();
   }

   /**
    * Function build operand and returns current state of model.
    *
    * @param number wanted number
    * @return current state of model
    */
   public ResponseDto buildOperand(Number number) {
      disableException();

      if (!container.isMakingOperand()) {
         clearEntry();
      }

      container.getOperation().buildOperand(number);

      ResponseDto response = getCurrentState();
      response.setSeparated(showIsSeparated());

      return response;
   }

   /**
    * Function for disable blocked some function when exception was thrown.
    */
   private void disableException() {
      if(exceptionType != ExceptionType.NOTHING) {
         exceptionType = ExceptionType.NOTHING;
         clear();
      }
   }

   /**
    * Function executes equals operation and returns current state of model.
    *
    * @return current state of model.
    */
   public ResponseDto equalsOperation() {
      if (container.isMakingOperand() && isShownResult) {
         container.getOperation().setOperand(container.getResult());
      } else if (container.getOperation() instanceof Equals) {
         prepareForDoEquals();
      }

      exceptionType = calculate(() -> container.calculate());

      Equals equals = new Equals(container.getOperation());
      if (exceptionType == ExceptionType.NOTHING) {
         container.getHistory().clear();
      }

      container.setOperation(equals);
      container.setMakingOperand(false);

      return getCurrentStateForResult();
   }

   /**
    * Function prepares {@link Container} for special conditional.
    */
   private void prepareForDoEquals() {
      if (!isShownResult) {
         container.setResult(container.getOperation().getOperand());
      }
   }

   /**
    * Function calculate which was transmitted through the instance of {@link ExceptionSupplier}
    *
    * @see ExceptionSupplier
    * @param exceptionSupplier instance of {@link ExceptionSupplier}
    * @return an exception type which was thrown in {@link ExceptionSupplier}
    */
   private ExceptionType calculate(ExceptionSupplier exceptionSupplier) {
      ExceptionType exceptionType = ExceptionType.NOTHING;

      try {
         exceptionSupplier.calculate();
      } catch (CalculatorException e) {
         exceptionType = e.getExceptionType();
      }

      // that's need for history when exception was appear.
      // that scope clears current state and sends cloned history.
      if (exceptionType != ExceptionType.NOTHING) {
         MainHistory tempHistory = container.getHistory().clone();
         clear();
         container.setHistory(tempHistory);
      }

      return exceptionType;
   }

   /**
    * Function update flag which represents whether there is a comma
    * at the end of the number or not and returns current state.
    *
    * @return current state of model.
    */
   public ResponseDto separateOperand() {
      if (container.getOperation().getOperand().scale() == 0) {
         container.getOperation().setSeparated(true);
      }

      ResponseDto response = new ResponseDto();
      response.setOperand(showOperand());
      response.setSeparated(showIsSeparated());
      response.setExceptionType(exceptionType);

      return response;
   }

   /**
    * Function clear the last character of number. If that's comma that function
    * update a flag. Function returns current state of model.
    *
    * @return current state of model.
    */
   public ResponseDto backspace() {
      exceptionType = ExceptionType.NOTHING;
      ResponseDto response = new ResponseDto();

      if (container.isMakingOperand()) {
         container.getOperation().removeLast();
         response.setOperand(showOperand());
         response.setSeparated(showIsSeparated());
      }

      response.setExceptionType(exceptionType);
      return response;
   }

   /**
    * Do some operation for clearing current state of model. Do that
    * clears history, sets result value zero, create {@link Default} operation.
    *
    * @return current state of model.
    */
   public ResponseDto clear() {
      container.getHistory().clear();
      container.setResult(BigDecimal.ZERO);
      container.setOperation(new Default());
      container.setMakingOperand(true);
      exceptionType = ExceptionType.NOTHING;
      isShownResult = false;

      return getCurrentStateForResult();
   }

   /**
    * Clear current operand which stores in {@link Container#operation} and hide
    * last operand in history.
    * @return current state of model.
    */
   public ResponseDto clearEntry() {
      disableException();
      container.getHistory().hideLast();
      container.getOperation().setOperand(BigDecimal.ZERO);
      container.setMakingOperand(true);
      container.getOperation().getOperandHistory().clear();

      return getCurrentState();
   }

   /**
    * Add to memory.
    *
    * @return recall from memory.
    */
   public BigDecimal addMemory() {
      container.getMemory().add(resolveMemory());
      return container.getMemory().recall();
   }

   /**
    * Subtract to memory.
    *
    * @return recall from memory.
    */
   public BigDecimal subtractMemory() {
      container.getMemory().subtract(resolveMemory());
      return container.getMemory().recall();
   }

   /**
    * Function resolve which value add to memory.
    *
    * @return BigDecimal number
    */
   private BigDecimal resolveMemory() {
      BigDecimal number;
      if (container.getOperation().isShowOperand()) {
         number = container.getOperation().getOperand();
      } else {
         number = container.getResult();
      }

      return number;
   }

   /**
    * Recall from memory.
    * @return current state.
    */
   public ResponseDto getMemory() {
      exceptionType = ExceptionType.NOTHING;
      BigDecimal value = container.getMemory().recall();
      container.getOperation().setOperandFromMemory(value);

      this.isShownResult = false;

      ResponseDto response = new ResponseDto();
      response.setOperand(showOperand());
      response.setExceptionType(exceptionType);

      return response;
   }

   /**
    * Clear memory.
    *
    * @return recall from memory.
    */
   public BigDecimal clearMemory() {
      container.getMemory().clear();
      return container.getMemory().recall();
   }

   /**
    * @return the result which {@link Container} contains.
    */
   private BigDecimal showResult() {
      isShownResult = true;
      return checkScale(container.getResult());
   }

   /**
    * Function correct scale for number if it more than {@link SimpleOperation#MAX_SCALE}
    *
    * @param number BigDecimal number
    * @return scaled number to {@link SimpleOperation#MAX_SCALE}
    */
   private BigDecimal checkScale(BigDecimal number) {
      if (number.scale() > SimpleOperation.MAX_SCALE) {
         number = number.stripTrailingZeros();
      }
      return number;
   }

   /**
    * @return the operand which {@link Container#operation} contains.
    */
   private BigDecimal showOperand() {
      isShownResult = false;
      return checkScale(container.getOperation().getOperand());
   }

   /**
    * @return flag if the last character of operand is comma.
    */
   private boolean showIsSeparated() {
      isShownResult = false;
      return container.getOperation().isSeparated();
   }

   /**
    * @return current history
    */
   private MainHistory showHistory() {
      return container.getHistory();
   }

   /**
    * @return default current state of model.
    */
   public ResponseDto getCurrentState() {
      ResponseDto response = new ResponseDto();
      response.setOperand(showOperand());
      response.setHistory(showHistory());
      response.setExceptionType(exceptionType);

      return response;
   }

   /**
    * @return current state with result.
    */
   private ResponseDto getCurrentStateForResult() {
      ResponseDto response = new ResponseDto();
      response.setResult(showResult());
      response.setHistory(showHistory());
      response.setExceptionType(exceptionType);

      return response;
   }
}