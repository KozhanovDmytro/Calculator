package com.implemica.model.calculator;

import com.implemica.model.dto.ResponseDto;
import com.implemica.model.exceptions.CalculatorException;
import com.implemica.model.exceptions.ExceptionType;
import com.implemica.model.history.History;
import com.implemica.model.operations.Default;
import com.implemica.model.operations.Equals;
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

   /** The flag indicates whether the result is returned from the model or not.*/
   private boolean isShownResult;

   /**
    * Function for executing {@link SimpleOperation} and return current state of model.
    *
    * @param operation specific simple operation.
    * @return current state of model.
    */
   public ResponseDto executeSimpleOperation(SimpleOperation operation) throws CalculatorException {
      calculateSimpleOperation(operation);
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
      return container.getOperation() instanceof Equals && container.getOperation().isMadeOperand();
   }

   /**
    * Function for executing {@link SpecialOperation} and returns current state of model.
    *
    * @param operation specific {@link SpecialOperation}.
    * @return current state of model.
    */
   public ResponseDto executeSpecialOperation(SpecialOperation operation) throws CalculatorException {
      container.change(operation);
      return getCurrentState();
   }

   /**
    * Function build operand and returns current state of model.
    *
    * @param number wanted number
    * @return current state of model
    */
   public ResponseDto buildOperand(BigDecimal number) {
      if (!container.getOperation().isMadeOperand()) {
         clearEntry();
      }

      container.getOperation().buildOperand(number);
      isShownResult = false;

      return getCurrentState();
   }

   /**
    * Function executes equals operation and returns current state of model.
    *
    * @return current state of model.
    */
   public ResponseDto equalsOperation() throws CalculatorException {
      if (!container.getOperation().isMadeOperand()) {
         container.getOperation().setOperand(container.getResult());
      } else if (container.getOperation() instanceof Equals) {
         prepareForDoEquals();
      }

      container.calculate();
      container.getHistory().clear();
      container.setOperation(new Equals(container.getOperation()));

      return getCurrentStateForResult();
   }

   /**
    * Function prepares {@link Container} for special conditional.
    */
   private void prepareForDoEquals() {
      if (container.getOperation().isMadeOperand()) {
         container.setResult(container.getOperation().getOperand());
      }
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
      isShownResult = false;

      return getCurrentState();
   }

   /**
    * Clear current operand which stores in {@link Container#operation} and hide
    * last operand in history.
    * @return current state of model.
    */
   public ResponseDto clearEntry() {
      container.getHistory().hideLast();
      container.getOperation().setOperand(BigDecimal.ZERO);
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
      if (container.getOperation().isMadeOperand()) {
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
      BigDecimal value = container.getMemory().recall();
      container.getOperation().buildOperand(value);

      isShownResult = false;

      ResponseDto response = new ResponseDto();
      response.setOperand(showOperand());

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
    * @return current history
    */
   private History showHistory() {
      return container.getHistory();
   }

   /**
    * @return default current state of model.
    */
   public ResponseDto getCurrentState() {
      ResponseDto response = new ResponseDto();
      response.setOperand(showOperand());
      response.setHistory(showHistory());

      return response;
   }

   /**
    * @return current state with result.
    */
   private ResponseDto getCurrentStateForResult() {
      ResponseDto response = new ResponseDto();
      response.setResult(showResult());
      response.setHistory(showHistory());

      return response;
   }

   /**
    * Accessor for this#isShownResult.
    * @return this#isShownResult
    */
   public boolean isShownResult() {
      return isShownResult;
   }
}