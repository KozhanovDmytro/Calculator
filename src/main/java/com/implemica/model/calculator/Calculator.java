package com.implemica.model.calculator;

import com.implemica.model.dto.ResponseDto;
import com.implemica.model.exceptions.ExceptionType;
import com.implemica.model.exceptions.InvalidInputException;
import com.implemica.model.exceptions.OverflowException;
import com.implemica.model.exceptions.UndefinedResultException;
import com.implemica.model.history.MainHistory;
import com.implemica.model.interfaces.Numeral;
import com.implemica.model.interfaces.SpecialOperation;
import com.implemica.model.numerals.Arabic;
import com.implemica.model.numerals.numbers.Number;
import com.implemica.model.operations.Default;
import com.implemica.model.operations.Equals;
import com.implemica.model.operations.SimpleOperation;
import com.implemica.model.operations.special.Percent;
import lombok.Getter;

import java.math.BigDecimal;

public class Calculator {

   private Container container = new Container();

   private Numeral numeral = new Arabic();

   private boolean isShownResult;

   /*constants*/
   public static final int MAX_SCALE = 20000;

   private interface ExceptionSupplier {
      void calculate() throws Exception;
   }

   public ResponseDto executeSimpleOperation(SimpleOperation operation) {
      ExceptionType exceptionType = calculate(this::calculateSimpleOperation);

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

      ResponseDto response = new ResponseDto();
      response.setResult(showResult());
      response.setHistory(showHistory());
      response.setExceptionType(exceptionType);

      return response;
   }

   private void calculateSimpleOperation() throws OverflowException, UndefinedResultException, InvalidInputException {
      if (!(container.getOperation() instanceof Equals)) {
         container.calculate();
      } else if (container.getOperation().isShowOperand()) {
         container.setOperation(new Default(container.getOperation().getOperand()));
         container.calculate();
      }
   }

   public ResponseDto executeSpecialOperation(SpecialOperation operation) {
      if (operation instanceof Percent) {
         ((Percent) operation).setResult(container.getResult());
      }

      if (container.getHistory().size() == 0 && container.getOperation() instanceof Equals) {
         container.setOperation(new Default((Equals) container.getOperation(), container.getResult()));
      }

      ExceptionType exceptionType = calculate(() -> container.change(operation, isShownResult));

      ResponseDto response = new ResponseDto();
      response.setOperand(showOperand());
      response.setHistory(showHistory());
      response.setExceptionType(exceptionType);

      return response;
   }

   public ResponseDto buildOperand(Number number) {
      MainHistory history = null;
      if (container.isMakingOperand() || container.getOperation() instanceof Equals) {
         container.getOperation().buildOperand(numeral.translate(number));
         container.getOperation().setShowOperand(true);
      } else if (container.getOperation().getOperandHistory().size() > 0) {
         container.getOperation().getOperandHistory().clear();
         container.getOperation().setShowOperand(false);
         history = showHistory();
         container.getOperation().setOperand(BigDecimal.ZERO);
         container.getOperation().buildOperand(numeral.translate(number));
         container.getOperation().setShowOperand(true);
         container.setMakingOperand(true);
      }
      ResponseDto response = new ResponseDto();
      response.setOperand(showOperand());
      response.setHistory(history);
      response.setSeparated(showBuiltOperand());
      response.setExceptionType(ExceptionType.NOTHING);

      return response;
   }

   public ResponseDto equalsOperation() {
      if (container.isMakingOperand() && isShownResult) {
         container.getOperation().setOperand(container.getResult());
      } else if (container.getOperation() instanceof Equals) {
         doEqualsAfterEquals();
      }

      ExceptionType exceptionType = calculate(() -> container.calculate());

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
      } catch (OverflowException e) {
         exceptionType = ExceptionType.OVERFLOW;
      } catch (UndefinedResultException e) {
         exceptionType = ExceptionType.UNDEFINED_RESULT;
      } catch (ArithmeticException e) {
         exceptionType = ExceptionType.DIVIDE_BY_ZERO;
      } catch (InvalidInputException e) {
         exceptionType = ExceptionType.INVALID_INPUT;
      } catch (Exception e) {
         e.printStackTrace();
      }

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
      response.setExceptionType(ExceptionType.NOTHING);

      return response;
   }

   public ResponseDto backspace() {
      ResponseDto response = new ResponseDto();
      response.setExceptionType(ExceptionType.NOTHING);

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

      ResponseDto response = new ResponseDto();
      response.setResult(showResult());
      response.setHistory(showHistory());
      response.setExceptionType(ExceptionType.NOTHING);

      isShownResult = false;
      return response;
   }

   public ResponseDto clearEntry() {
      container.getHistory().hideLast();
      container.getOperation().setOperand(BigDecimal.ZERO);
      container.getOperation().setShowOperand(true);

      ResponseDto response = new ResponseDto();
      response.setOperand(showOperand());
      response.setExceptionType(ExceptionType.NOTHING);

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
      BigDecimal value = container.getMemory().recall();
      if (value != null) {
         container.getOperation().setOperand(value);
         container.getOperation().setInitialOperand(value);
         container.getOperation().setShowOperand(true);
      }
      this.isShownResult = false;

      ResponseDto response = new ResponseDto();
      response.setOperand(showOperand());
      response.setExceptionType(ExceptionType.NOTHING);

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
      if(number.scale() > MAX_SCALE) {
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
      response.setResult(showResult());
      response.setHistory(showHistory());
      response.setExceptionType(ExceptionType.NOTHING);

      return response;
   }
}