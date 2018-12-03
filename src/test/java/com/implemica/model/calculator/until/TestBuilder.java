package com.implemica.model.calculator.until;

import com.implemica.controller.util.HistoryParser;
import com.implemica.controller.util.Validator;
import com.implemica.model.calculator.Calculator;
import com.implemica.model.calculator.Container;
import com.implemica.model.dto.ResponseDto;
import com.implemica.model.exceptions.CalculatorException;
import com.implemica.model.exceptions.ExceptionType;
import com.implemica.model.history.History;
import com.implemica.model.operations.operation.SimpleOperation;
import com.implemica.model.operations.operation.SpecialOperation;
import com.implemica.model.operations.simple.Divide;
import com.implemica.model.operations.simple.Minus;
import com.implemica.model.operations.simple.Multiply;
import com.implemica.model.operations.simple.Plus;
import com.implemica.model.operations.special.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBuilder {

   private Calculator calculator = new Calculator();

   private BigDecimal result;

   private BigDecimal operand;

   private Validator validator = new Validator();

   private HistoryParser historyParser = new HistoryParser();

   public void doExceptionsTest(String pattern, ExceptionType expectedExceptionType) {
      try{
         doTest(pattern, null, 0, null, null);
      } catch(CalculatorException e) {
         assertEquals(expectedExceptionType, e.getExceptionType());
      }
   }

   public void doTest(String pattern, String history, int historySize, String result, String operand) throws CalculatorException {
      calculator = new Calculator();
      this.result = BigDecimal.ZERO;
      this.operand = BigDecimal.ZERO;
      String[] actions = pattern.split(" ");
      for (String action : actions) {
         if ("C".equals(action)) {
            clear();

         } else if ("CE".equals(action)) {
            clearEntry();

         } else if ("MC".equals(action)) {
            memoryClear();

         } else if ("MR".equals(action)) {
            memoryRecall();

         } else if ("M+".equals(action)) {
            addMemory();

         } else if ("M-".equals(action)) {
            subtractMemory();

         } else if ("SQR".equals(action)) {
            executeSpecialOperation(new Square());

         } else if ("1/x".equals(action)) {
            executeSpecialOperation(new DivideBy());

         } else if(action.matches("\\d+\\.?\\d*")) {
            buildOperand(new BigDecimal(action));
            calculator.buildOperand(new BigDecimal(action));

         } else {
            checkBySymbols(action);
         }
      }

      if (history != null) {
         checkHistory(history, historySize);
      }

      if (operand != null) {
         checkOperand(operand);
      }

      if (result != null) {
         checkResult(result);
      }
   }

   public void doBoundaryTest(String pattern, String expected) throws CalculatorException {
      doTest(pattern, null, 0, null, null);

      if(expected != null) {
         assertEquals(new BigDecimal(expected), result);
      }
   }

   private void checkBySymbols(String pattern) throws CalculatorException {
      for (char action : pattern.toCharArray()) {
         switch (action) {
            case '+':
               executeSimpleOperation(new Plus());
               break;
            case '-':
               executeSimpleOperation(new Minus());
               break;
            case '×':
               executeSimpleOperation(new Multiply());
               break;
            case '÷':
               executeSimpleOperation(new Divide());
               break;
            case '=':
               equals();
               break;
            case '%':
               executeSpecialOperation(new Percent());
               break;
            case '√':
               executeSpecialOperation(new SquareRoot());
               break;
            case 'n':
               executeSpecialOperation(new Negate());
               break;
         }
      }
   }

   private void executeSimpleOperation(SimpleOperation operation) throws CalculatorException {
      ResponseDto response = calculator.executeSimpleOperation(operation);
      parseDto(response);
   }

   private void executeSpecialOperation(SpecialOperation operation) throws CalculatorException {
      ResponseDto response = calculator.executeSpecialOperation(operation);
      parseDto(response);
   }

   private void clear(){
      ResponseDto response = calculator.clear();
      parseDto(response);
   }

   private void memoryClear() {
      BigDecimal memoryValue = calculator.clearMemory();
      validator.showNumber(memoryValue);
   }

   private void memoryRecall() {
      parseDto(calculator.getMemory());
   }

   private void addMemory() {
      calculator.addMemory();
   }

   private void subtractMemory() {
      calculator.subtractMemory();
   }

   private void clearEntry(){
      ResponseDto response = calculator.clearEntry();
      parseDto(response);
   }

   private void equals() throws CalculatorException {
      ResponseDto response = calculator.equalsOperation();
      parseDto(response);
   }

   private void checkResult(String expected) {
      assertEquals(expected, validator.showNumber(result));
   }

   private void checkOperand(String expected) {
      assertEquals(expected, validator.showNumber(operand.stripTrailingZeros()));
   }

   private void buildOperand(BigDecimal number) {
      ResponseDto response = calculator.buildOperand(number);
      operand = response.getOperand();
   }

   private void checkHistory(String expectedHistory, int size) {
      Container container = null;
      try {
         Field f = calculator.getClass().getDeclaredField("container");
         f.setAccessible(true);
         container = (Container) f.get(calculator);
      } catch (NoSuchFieldException | IllegalAccessException e) {
         e.printStackTrace();
      }

      assert container != null;

      History history = container.getHistory();

      assertEquals(size, history.size());
      assertEquals(expectedHistory, historyParser.parse(history));
   }

   private void parseDto(ResponseDto response) {
      if(response.getResult() != null) {
         result = response.getResult();
      }

      if(response.getOperand() != null) {
         operand = response.getOperand();
      }
   }
}
