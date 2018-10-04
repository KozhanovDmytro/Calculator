package com.implemica.model.calculator;

import com.implemica.model.dto.ResponseDto;
import com.implemica.model.exceptions.ExceptionType;
import com.implemica.model.exceptions.OverflowException;
import com.implemica.model.exceptions.UndefinedResultException;
import com.implemica.model.interfaces.Numeral;
import com.implemica.model.interfaces.SpecialOperation;
import com.implemica.model.numerals.numbers.Number;
import com.implemica.model.operations.Default;
import com.implemica.model.operations.Equals;
import com.implemica.model.operations.SimpleOperation;
import com.implemica.model.operations.special.Percent;
import com.implemica.model.validation.Validator;
import lombok.Getter;

import java.math.BigDecimal;

public class Calculator {

   @Getter
   private Container container;

   private Numeral numeral;

   private Validator validator = new Validator();

   @Getter
   private boolean isShownResult;

   public Calculator(Numeral numeral){
      this.numeral = numeral;
      container = new Container();
      container.setMadeOperand(true);
   }

   public ResponseDto executeSimpleOperation(SimpleOperation operation) {
      try {
         if (!(container.getOperation() instanceof Equals)) {
               container.calculate();
         } else if(container.getOperation().isShowOperand()) {
            container.setOperation(new Default(container.getOperation().getOperand()));
            container.calculate();
         }
      } catch (OverflowException e) {
         clear();
         return new ResponseDto(showResult(), null, showHistory(), null,
                 ExceptionType.OVERFLOW);
      } catch (UndefinedResultException e) {
         clear();
         return new ResponseDto(showResult(), null, showHistory(), null,
                 ExceptionType.UNDEFINED_RESULT);
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

      container.setOperation(operation);
      container.setMadeOperand(true);
      return new ResponseDto(showResult(), null, showHistory(), null, ExceptionType.NOTHING);
   }

   public ResponseDto executeSpecialOperation(SpecialOperation operation) {
      if(operation instanceof Percent) {
         ((Percent) operation).setResult(container.getResult());
      }
      if(container.getHistory().size() == 0 && !(container.getOperation() instanceof Default)){
         container.setOperation(new Default(container.getResult()));
      }
      ExceptionType exceptionType = ExceptionType.NOTHING;
      try {
         container.change(operation, isShownResult);
      } catch (UndefinedResultException e) {
         clear();
         exceptionType = ExceptionType.UNDEFINED_RESULT;
      } catch (OverflowException e) {
         clear();
         exceptionType = ExceptionType.OVERFLOW;
      }
      return new ResponseDto(null, showOperand(), showHistory(), null, exceptionType);
   }

   public ResponseDto buildOperand(Number number){
      if (container.isMadeOperand() || container.getOperation() instanceof Equals) {
         container.getOperation().buildOperand(numeral.translate(number));
         container.getOperation().setShowOperand(true);
      }
      return new ResponseDto(null, showOperand(), null, showBuiltOperand(), ExceptionType.NOTHING);
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
            }
            eq.getLastOperation().setOperand(eq.getLastOperation().getOperand());
         }
      }
      try {
         container.calculate();
      } catch (OverflowException e) {
         clear();
         return new ResponseDto(showResult(), null, showHistory(), null, ExceptionType.OVERFLOW);
      } catch (UndefinedResultException e) {
         clear();
         return new ResponseDto(showResult(), null, showHistory(), null, ExceptionType.UNDEFINED_RESULT);
      }
      Equals equals = new Equals(container.getOperation());
      container.getHistory().clear();
      container.setOperation(equals);

      container.setMadeOperand(false);
      return new ResponseDto(showResult(), null, showHistory(), null, ExceptionType.NOTHING);
   }

   public ResponseDto separateOperand() {
      container.getOperation().setSeparated(true);
      return new ResponseDto(null, showOperand(), null, null, ExceptionType.NOTHING);
   }

   public ResponseDto backspace(){
      if (container.isMadeOperand()) {
         container.getOperation().removeLast();
      }
      return new ResponseDto(null, null, null, showBuiltOperand(), ExceptionType.NOTHING);
   }

   public ResponseDto clear(){
      container.getHistory().clear();
      container.setResult(BigDecimal.ZERO);
      container.setOperation(new Default());
      container.setMadeOperand(true);

      return new ResponseDto(showResult(), showOperand(), showHistory(), null, ExceptionType.NOTHING);
   }

   public ResponseDto clearEntry(){
      container.getOperation().setOperand(BigDecimal.ZERO);

      return new ResponseDto(showResult(), showOperand(), showHistory(), null, ExceptionType.NOTHING);
   }

   private String showResult(){
      isShownResult = true;

      return validator.showNumber(container.getResult().stripTrailingZeros());
   }

   private String showOperand(){
      isShownResult = false;

      BigDecimal operand = container.getOperation().getOperand();

      return validator.showNumber(operand.stripTrailingZeros(), container.getOperation().isSeparated());
   }

   private String showBuiltOperand() {
      isShownResult = false;

      return validator.builtOperand(container.getOperation().getOperand(), container.getOperation().isSeparated());
   }

   private String showHistory(){
      return container.getHistory().buildHistory();
   }
}
