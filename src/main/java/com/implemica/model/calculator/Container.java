package com.implemica.model.calculator;

import com.implemica.model.exceptions.*;
import com.implemica.model.history.MainHistory;
import com.implemica.model.operations.Equals;
import com.implemica.model.operations.operation.SpecialOperation;
import com.implemica.model.memory.Memory;
import com.implemica.model.operations.Default;
import com.implemica.model.operations.operation.SimpleOperation;
import com.implemica.model.operations.special.Negate;
import com.implemica.model.operations.special.Percent;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Container {

   private BigDecimal result = BigDecimal.ZERO;

   private SimpleOperation operation = new Default();

   private MainHistory history = new MainHistory();

   private Memory memory = new Memory();

   private boolean makingOperand = true;

   /*constants*/

   private final BigDecimal MAX = new BigDecimal("1e10000");
   private final BigDecimal MIN = new BigDecimal("1e-10000");
   private final BigDecimal CLOSER_TO_ZERO = new BigDecimal("1e-19950");

   public void calculate() throws CalculatorException {
      result = this.operation.calculate(result);
      result = checkForZero(result);
      checkOverflow(result);
      addToHistoryDefaultOperation();
   }

   void change(SpecialOperation specialOperation, boolean isResult) throws CalculatorException {
      if (specialOperation instanceof Percent) {
         ((Percent) specialOperation).setResult(result);
      }

      if (history.size() == 0 && operation instanceof Equals) {
         operation = new Default((Equals) operation, result);
      }

      if (isResult && !operation.isShowOperand()) {
         operation.setInitialOperand(result);
         operation.getOperandHistory().add(specialOperation);
         operation.setOperand(resolveSpecialOperation(specialOperation));

      } else {
         addToHistoryDefaultOperation();
         operation.getOperandHistory().add(specialOperation);
         operation.setOperand(specialOperation.calculate(getOperation().getOperand()));

      }

      makingOperand = specialOperation instanceof Negate;
      operation.setShowOperand(true);
      operation.setOperand(checkForZero(operation.getOperand()));
      checkOverflow(operation.getOperand());
   }

   void addToHistory(SimpleOperation simpleOperation) {
      if (history.size() == 0) {
         history.firstAdd(new Default(result), simpleOperation);
      } else if (this.operation.isShowOperand()) {
         history.add(simpleOperation);
      } else {
         history.changeLast(simpleOperation);
      }
   }

   private BigDecimal resolveSpecialOperation(SpecialOperation operation) throws CalculatorException{
      BigDecimal operand;
      if (operation instanceof Percent) {
         operand = operation.calculate(getOperation().getOperand());
      } else {
         operand = operation.calculate(result);
      }
      return operand;
   }

   private void addToHistoryDefaultOperation() {
      if (operation instanceof Default && getHistory().size() == 0) {
         getHistory().add(operation);
         operation.setShowOperand(true);
      }
   }

   private void checkOverflow(BigDecimal number) throws CalculatorException {
      if (outFromMaxRange(number) || outFromMinRange(number)) {
         throw new CalculatorException(ExceptionType.OVERFLOW);
      }
   }

   private boolean outFromMaxRange(BigDecimal number) {
      return number.abs().compareTo(MAX) >= 0;
   }

   private boolean outFromMinRange(BigDecimal number) {
      return number.abs().compareTo(BigDecimal.ZERO) > 0 && number.abs().compareTo(MIN) <= 0;
   }

   private BigDecimal checkForZero(BigDecimal number) {
      if (number.abs().compareTo(CLOSER_TO_ZERO) < 0) {
         return BigDecimal.ZERO;
      } else {
         return number;
      }
   }
}