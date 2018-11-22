package com.implemica.model.calculator;

import com.implemica.model.dto.ResponseDto;
import com.implemica.model.exceptions.*;
import com.implemica.model.history.MainHistory;
import com.implemica.model.interfaces.Numeral;
import com.implemica.model.operations.operation.Operation;
import com.implemica.model.operations.operation.SpecialOperation;
import com.implemica.model.numerals.Arabic;
import com.implemica.model.numerals.numbers.Number;
import com.implemica.model.operations.Default;
import com.implemica.model.operations.Equals;
import com.implemica.model.operations.operation.SimpleOperation;
import com.implemica.model.operations.special.Percent;

import java.math.BigDecimal;

public class Calculator {

   private Container container = new Container();

   private Numeral numeral = new Arabic(); // todo delete it.

   private ExceptionType exceptionType = ExceptionType.NOTHING;

   private boolean isShownResult;

   private interface ExceptionSupplier {
      void calculate() throws CalculatorException;
   }

   public ResponseDto executeSimpleOperation(SimpleOperation operation) {
      if(exceptionType == ExceptionType.NOTHING) {
         exceptionType = calculate(() -> calculateSimpleOperation(operation));
      }

      ResponseDto response = new ResponseDto();
      response.setResult(showResult());
      response.setHistory(showHistory());
      response.setExceptionType(exceptionType);

      return response;
   }

   private void calculateSimpleOperation(SimpleOperation operation) throws CalculatorException {
      if (!(container.getOperation() instanceof Equals)) {
         container.calculate();
      } else if (container.getOperation().isShowOperand()) {
         container.setOperation(new Default(container.getOperation().getOperand()));
         container.calculate();
      }

      if (container.getOperation().isShowOperand()) {
         container.getHistory().add(operation);
      } else if (container.getHistory().size() == 0) {
         container.getHistory().add(new Default(container.getResult()));
         container.getHistory().add(operation);
      } else {
         container.getHistory().changeLast(operation);
      }

      container.setOperation(operation);
      container.setMakingOperand(true);
   }

   public ResponseDto executeSpecialOperation(SpecialOperation operation) {
      if(exceptionType == ExceptionType.NOTHING) {
         exceptionType = calculate(() -> container.change(operation, isShownResult));
      }

      ResponseDto response = new ResponseDto();
      response.setOperand(showOperand());
      response.setHistory(showHistory());
      response.setExceptionType(exceptionType);

      return response;
   }

   public ResponseDto buildOperand(Number number) {
      exceptionType = ExceptionType.NOTHING;
      if (!container.isMakingOperand()) {
         clearEntry();
      }

      container.getOperation().buildOperand(numeral.translate(number));
      container.getOperation().setShowOperand(true);

      ResponseDto response = new ResponseDto();
      response.setOperand(showOperand());
      response.setHistory(showHistory());
      response.setSeparated(showBuiltOperand());
      response.setExceptionType(exceptionType);

      return response;
   }

   public ResponseDto equalsOperation() {
      if (container.isMakingOperand() && isShownResult) {
         container.getOperation().setOperand(container.getResult());
      } else if (container.getOperation() instanceof Equals) {
         doEqualsAfterEquals();
      }

      exceptionType = calculate(() -> container.calculate());

      Equals equals = new Equals(container.getOperation());
      if (exceptionType == ExceptionType.NOTHING) {
         container.getHistory().clear();
      }
      container.setOperation(equals);
      container.setMakingOperand(false);

      ResponseDto response = new ResponseDto();
      response.setResult(showResult());
      response.setHistory(showHistory());
      response.setExceptionType(exceptionType);

      return response;
   }

   private void doEqualsAfterEquals() {
      Equals currentEquals = (Equals) container.getOperation();
      if (!isShownResult) {
         container.setResult(container.getOperation().getOperand());
      } else if (currentEquals.getLastOperation() instanceof Default) {
         currentEquals.getLastOperation().setOperand(container.getResult());
      } else if (currentEquals.getLastOperation() instanceof Equals) {
         doEqualsAfterSpecialOperation(currentEquals);
      }
   }

   private void doEqualsAfterSpecialOperation(Equals equals) {
      if (((Equals) equals.getLastOperation()).getLastOperation() instanceof Default) {
         equals.setLastOperation(((Equals) equals.getLastOperation()).getLastOperation());
         equals.getLastOperation().setOperand(container.getResult());
      }
   }

   private ExceptionType calculate(ExceptionSupplier exceptionSupplier) {
      ExceptionType exceptionType = ExceptionType.NOTHING;

      try {
         exceptionSupplier.calculate();
      } catch (CalculatorException e) {
         exceptionType = e.getExceptionType();
      }

      // that's need for history when exception was appear.
      if (exceptionType != ExceptionType.NOTHING) {
         MainHistory tempHistory = container.getHistory().clone();
         clear();
         container.setHistory(tempHistory);
      }

      return exceptionType;
   }

   public ResponseDto separateOperand() {
      if (container.getOperation().getOperand().scale() == 0) {
         container.getOperation().setSeparated(true);
      }

      ResponseDto response = new ResponseDto();
      response.setOperand(showOperand());
      response.setSeparated(showBuiltOperand());
      response.setExceptionType(exceptionType);

      return response;
   }

   public ResponseDto backspace() {
      exceptionType = ExceptionType.NOTHING;
      ResponseDto response = new ResponseDto();
      response.setExceptionType(exceptionType);

      if (container.isMakingOperand()) {
         container.getOperation().removeLast();
         response.setOperand(showOperand());
         response.setSeparated(showBuiltOperand());
      }

      return response;
   }

   public ResponseDto clear() {
      container.getHistory().clear();
      container.setResult(BigDecimal.ZERO);
      container.setOperation(new Default());
      container.setMakingOperand(true);
      exceptionType = ExceptionType.NOTHING;

      ResponseDto response = new ResponseDto();
      response.setResult(showResult());
      response.setHistory(showHistory());
      response.setExceptionType(exceptionType);

      isShownResult = false;
      return response;
   }

   public ResponseDto clearEntry() {
      exceptionType = ExceptionType.NOTHING;
      container.getHistory().hideLast();
      container.getOperation().setOperand(BigDecimal.ZERO);
      container.setMakingOperand(true);
      container.getOperation().getOperandHistory().clear();

      ResponseDto response = new ResponseDto();
      response.setOperand(showOperand());
      response.setHistory(showHistory());
      response.setExceptionType(exceptionType);

      return response;
   }

   public BigDecimal addMemory() {
      if (container.getOperation().isShowOperand()) {
         container.getMemory().add(container.getOperation().getOperand());
      } else {
         container.getMemory().add(container.getResult());
      }

      return container.getMemory().recall();
   }

   public BigDecimal subtractMemory() {
      if (container.getOperation().isShowOperand()) {
         container.getMemory().subtract(container.getOperation().getOperand());
      } else {
         container.getMemory().subtract(container.getResult());
      }

      return container.getMemory().recall();
   }

   public ResponseDto getMemory() {
      exceptionType = ExceptionType.NOTHING;
      BigDecimal value = container.getMemory().recall();
      container.getOperation().setOperand(value);
      container.getOperation().setInitialOperand(value);
      container.getOperation().setShowOperand(true);

      this.isShownResult = false;

      ResponseDto response = new ResponseDto();
      response.setOperand(showOperand());
      response.setExceptionType(exceptionType);

      return response;
   }

   public BigDecimal clearMemory() {
      container.getMemory().clear();
      return container.getMemory().recall();
   }

   private BigDecimal showResult() {
      isShownResult = true;
      return checkScale(container.getResult());
   }

   private BigDecimal checkScale(BigDecimal number) {
      if (number.scale() > Operation.MAX_SCALE) {
         number = number.stripTrailingZeros();
      }
      return number;
   }

   private BigDecimal showOperand() {
      isShownResult = false;
      return checkScale(container.getOperation().getOperand());
   }

   private boolean showBuiltOperand() {
      isShownResult = false;
      return container.getOperation().isSeparated();
   }

   private MainHistory showHistory() {
      return container.getHistory();
   }

   public ResponseDto getCurrentState() {
      ResponseDto response = new ResponseDto();
      response.setOperand(showOperand());
      response.setHistory(showHistory());
      response.setExceptionType(exceptionType);

      return response;
   }
}