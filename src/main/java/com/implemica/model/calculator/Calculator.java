package com.implemica.model.calculator;

import com.implemica.model.dto.ResponseDto;
import com.implemica.model.exceptions.ExceptionType;
import com.implemica.model.exceptions.InvalidInputException;
import com.implemica.model.exceptions.OverflowException;
import com.implemica.model.exceptions.UndefinedResultException;
import com.implemica.model.history.MainHistory;
import com.implemica.model.interfaces.Numeral;
import com.implemica.model.interfaces.SpecialOperation;
import com.implemica.model.numerals.numbers.Number;
import com.implemica.model.operations.Default;
import com.implemica.model.operations.Equals;
import com.implemica.model.operations.SimpleOperation;
import com.implemica.model.operations.special.Negate;
import com.implemica.model.operations.special.Percent;
import lombok.Getter;

import java.math.BigDecimal;

public class Calculator {

   @Getter
   private Container container;

   private Numeral numeral;

   @Getter
   private boolean isShownResult;

   public Calculator(Numeral numeral){
      this.numeral = numeral;
      container = new Container();
      container.setMadeOperand(true);
   }

   public ResponseDto executeSimpleOperation(SimpleOperation operation) {
      ExceptionType exceptionType = ExceptionType.NOTHING;

      try {
         if (!(container.getOperation() instanceof Equals)) {
               container.calculate();
         } else if(container.getOperation().isShowOperand()) {
            container.setOperation(new Default(container.getOperation().getOperand()));
            container.calculate();
         }
      } catch (OverflowException e) {
         exceptionType = ExceptionType.OVERFLOW;
      } catch (UndefinedResultException e) {
         exceptionType = ExceptionType.UNDEFINED_RESULT;
      } catch (ArithmeticException e) {
         exceptionType = ExceptionType.DIVIDE_BY_ZERO;
      } catch (InvalidInputException e) {
         exceptionType = ExceptionType.INVALID_INPUT;
      }

      if(container.getOperation().isShowOperand()){
         container.getHistory().add(operation);
      } else {
         if(container.getHistory().size() == 0){
            container.getHistory().add(new Default(container.getResult()));
            container.getHistory().add(operation);
         } else {
            container.getHistory().changeLast(operation);
         }
      }
      if(exceptionType != ExceptionType.NOTHING) {
         clear();
      }

      container.setOperation(operation);
      container.setMadeOperand(true);
      ResponseDto response = new ResponseDto();
      response.setResult(showResult());
      response.setHistory(showHistory());
      response.setExceptionType(exceptionType);

      return response;
   }

   public ResponseDto executeSpecialOperation(SpecialOperation operation) {
      ExceptionType exceptionType = ExceptionType.NOTHING;

      if(operation instanceof Percent) {
         ((Percent) operation).setResult(container.getResult());
      }
      if(container.getHistory().size() == 0 && container.getOperation() instanceof Equals){
         container.setOperation(new Default((Equals) container.getOperation(), container.getResult()));
      }
      try {
         container.change(operation, isShownResult);
         container.setMadeOperand(operation instanceof Negate);
      } catch (UndefinedResultException e) {
         exceptionType = ExceptionType.UNDEFINED_RESULT;
      } catch (OverflowException e) {
         exceptionType = ExceptionType.OVERFLOW;
      } catch(ArithmeticException e) {
         exceptionType = ExceptionType.DIVIDE_BY_ZERO;
      } catch (InvalidInputException e) {
         exceptionType = ExceptionType.INVALID_INPUT;
      }

      if(exceptionType != ExceptionType.NOTHING) {
         clear();
      }
      ResponseDto response = new ResponseDto();
      response.setOperand(showOperand());
      response.setHistory(showHistory());
      response.setExceptionType(exceptionType);

      return response;
   }

   public ResponseDto buildOperand(Number number){
      MainHistory history = null;
      if (container.isMadeOperand() || container.getOperation() instanceof Equals) {
         container.getOperation().buildOperand(numeral.translate(number));
         container.getOperation().setShowOperand(true);
      } else if(container.getOperation().getOperandHistory().size() > 0) {
         container.getOperation().getOperandHistory().clear();
         container.getOperation().setShowOperand(false);
         history = showHistory();
         container.getOperation().setShowOperand(true);
         container.getOperation().setOperand(BigDecimal.ZERO);
         container.getOperation().buildOperand(numeral.translate(number));
         container.setMadeOperand(true);
      }
      ResponseDto response = new ResponseDto();
      response.setOperand(showOperand());
      response.setHistory(history);
      response.setSeparated(showBuiltOperand());
      response.setExceptionType(ExceptionType.NOTHING);

      return response;
   }

   public ResponseDto equalsOperation() {
      if (container.isMadeOperand()) {
         if(isShownResult){
            container.getOperation().setOperand(container.getResult());
         }
      } else {
         if (container.getOperation() instanceof Equals) {
            Equals eq = (Equals) container.getOperation();
            if (!isShownResult) {
               container.setResult(container.getOperation().getOperand());
            } else if(eq.getLastOperation() instanceof Default) {
               eq.getLastOperation().setOperand(container.getResult());
            } else if(eq.getLastOperation() instanceof Equals) {
               if(((Equals) eq.getLastOperation()).getLastOperation() instanceof Default) {
                  eq.setLastOperation(((Equals) eq.getLastOperation()).getLastOperation());
                  eq.getLastOperation().setOperand(container.getResult());
               }
            }
         }
      }

      ExceptionType exceptionType = ExceptionType.NOTHING;

      try {
         container.calculate();
      } catch (OverflowException e) {
         clear();
         exceptionType = ExceptionType.OVERFLOW;
      } catch (UndefinedResultException e) {
         clear();
         exceptionType = ExceptionType.UNDEFINED_RESULT;
      } catch(ArithmeticException e) {
         clear();
         exceptionType = ExceptionType.DIVIDE_BY_ZERO;
      } catch (InvalidInputException e) {
         clear();
         exceptionType = ExceptionType.INVALID_INPUT;
      }
      Equals equals = new Equals(container.getOperation());
      container.getHistory().clear();
      container.setOperation(equals);

      container.setMadeOperand(false);
      ResponseDto response = new ResponseDto();
      response.setResult(showResult());
      response.setHistory(showHistory());
      response.setExceptionType(exceptionType);

      return response;
   }

   public ResponseDto separateOperand() {
      if(container.getOperation().getOperand().scale() == 0) {
         container.getOperation().setSeparated(true);
      }

      ResponseDto response = new ResponseDto();
      response.setOperand(showOperand());
      response.setSeparated(showBuiltOperand());
      response.setExceptionType(ExceptionType.NOTHING);

      return response;
   }

   public ResponseDto backspace(){
      ResponseDto response = new ResponseDto();
      response.setExceptionType(ExceptionType.NOTHING);

      if (container.isMadeOperand()) {
         container.getOperation().removeLast();
         response.setOperand(showOperand());
         response.setSeparated(showBuiltOperand());

      } else if(isShownResult) {
         response.setOperand(showResult());

      } else {
         response.setOperand(showOperand());
      }

      return response;
   }

   public ResponseDto clear(){
      container.getHistory().clear();
      container.setResult(BigDecimal.ZERO);
      container.setOperation(new Default());
      container.setMadeOperand(true);

      ResponseDto response = new ResponseDto();
      response.setResult(showResult());
      response.setHistory(showHistory());
      response.setExceptionType(ExceptionType.NOTHING);

      return response;
   }

   public ResponseDto clearEntry(){
      container.getHistory().hideLast();
      container.getOperation().setOperand(BigDecimal.ZERO);

      ResponseDto response = new ResponseDto();
      response.setOperand(showOperand());
      response.setHistory(showHistory());
      response.setExceptionType(ExceptionType.NOTHING);

      return response;
   }

   public BigDecimal addMemory(){
      if(container.getOperation().isShowOperand()) {
         container.getMemory().add(container.getOperation().getOperand());
      } else {
         container.getMemory().add(container.getResult());
      }

      return container.getMemory().recall();
   }

   public BigDecimal subtractMemory() {
      if(container.getOperation().isShowOperand()) {
         container.getMemory().subtract(container.getOperation().getOperand());
      } else {
         container.getMemory().subtract(container.getResult());
      }

      return container.getMemory().recall();
   }

   public ResponseDto getMemory() {
      BigDecimal value = container.getMemory().recall();
      if(value != null) {
         container.getOperation().setOperand(value);
         container.getOperation().setShowOperand(true);
      }
      this.isShownResult = false;

      ResponseDto response = new ResponseDto();
      response.setOperand(showOperand());
      response.setExceptionType(ExceptionType.NOTHING);

      return response;
   }

   private BigDecimal showResult(){
      isShownResult = true;
      return container.getResult().stripTrailingZeros();
   }

   private BigDecimal showOperand(){
      isShownResult = false;
      return container.getOperation().getOperand();
   }

   private boolean showBuiltOperand() {
      isShownResult = false;
      return container.getOperation().isSeparated();
   }

   private MainHistory showHistory(){
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