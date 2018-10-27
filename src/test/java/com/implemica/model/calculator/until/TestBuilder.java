package com.implemica.model.calculator.until;

import com.implemica.model.calculator.Calculator;
import com.implemica.model.dto.ResponseDto;
import com.implemica.model.exceptions.ExceptionType;
import com.implemica.model.exceptions.InvalidInputException;
import com.implemica.model.exceptions.OverflowException;
import com.implemica.model.exceptions.UndefinedResultException;
import com.implemica.model.interfaces.History;
import com.implemica.model.interfaces.SpecialOperation;
import com.implemica.model.numerals.Arabic;
import com.implemica.model.numerals.numbers.Number;
import com.implemica.model.operations.SimpleOperation;
import com.implemica.model.operations.simple.Divide;
import com.implemica.model.operations.simple.Minus;
import com.implemica.model.operations.simple.Multiply;
import com.implemica.model.operations.simple.Plus;
import com.implemica.model.operations.special.*;
import com.implemica.model.validation.Validator;
import lombok.Getter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestBuilder {

   @Getter
   private Calculator calculator;

   private String result;

   private String operand;

   private String memory;

   private String history;

   private boolean isSeparatedOperand;

   private Validator validator = new Validator();

   private ExceptionType exceptionType = ExceptionType.NOTHING;

   public TestBuilder() {
      calculator = new Calculator(new Arabic());
   }

   public void doExceptionsTest(String pattern, ExceptionType exceptionType) {
      Exception expected = new Exception();
      switch (exceptionType){
         case OVERFLOW:
            expected = new OverflowException();
            break;
         case UNDEFINED_RESULT:
            expected = new UndefinedResultException();
            break;
         case DIVIDE_BY_ZERO:
            expected = new ArithmeticException();
            break;
         case INVALID_INPUT:
            expected = new InvalidInputException();
            break;
      }

      assertThrows(expected.getClass(), () -> doTest(pattern, null, 0, null, null));
   }

   public void doTest(String pattern, String history, int historySize, String result, String operand) throws OverflowException, UndefinedResultException, InvalidInputException {
      calculator = new Calculator(new Arabic());
      this.result = "0";
      this.operand = "0";
      String[] actions = pattern.split(" ");
      for (String action : actions) {
         switch (action) {
            case "C":
               clear();
               break;
            case "CE":
               clearEntry();
               break;
            case "MC":
               memoryClear();
               break;
            case "MR":
               memoryRecall();
               break;
            case "M+":
               addMemory();
               break;
            case "M-":
               subtractMemory();
               break;
            default:
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

      checkException();

   }

   public void checkBuildOperand(String pattern, String expected) throws OverflowException, InvalidInputException, UndefinedResultException {
      doTest(pattern, "", 0, null, expected);
   }

   private void checkBySymbols(String pattern) throws OverflowException, InvalidInputException, UndefinedResultException {
      for (char action : pattern.toCharArray()) {
         switch (action) {
            case '0':
               buildOperand(Number.ZERO);
               break;
            case '1':
               buildOperand(Number.ONE);
               break;
            case '2':
               buildOperand(Number.TWO);
               break;
            case '3':
               buildOperand(Number.THREE);
               break;
            case '4':
               buildOperand(Number.FOUR);
               break;
            case '5':
               buildOperand(Number.FIVE);
               break;
            case '6':
               buildOperand(Number.SIX);
               break;
            case '7':
               buildOperand(Number.SEVEN);
               break;
            case '8':
               buildOperand(Number.EIGHT);
               break;
            case '9':
               buildOperand(Number.NINE);
               break;
            case '.':
               executeSeparate();
               break;
            case '+':
               executeSimpleOperation(new Plus());
               break;
            case '-':
               executeSimpleOperation(new Minus());
               break;
            case '*':
               executeSimpleOperation(new Multiply());
               break;
            case '/':
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
            case 'q':
               executeSpecialOperation(new Square());
               break;
            case 'r':
               executeSpecialOperation(new DivideBy());
               break;
            case '<':
               executeBackSpace();
               break;
            case 'n':
               executeSpecialOperation(new Negate());
               break;
         }
      }
   }

   private void executeSimpleOperation(SimpleOperation operation) throws OverflowException, InvalidInputException, UndefinedResultException {
      ResponseDto response = calculator.executeSimpleOperation(operation);
      parseDto(response);
      checkException();
   }

   private void executeSpecialOperation(SpecialOperation operation) throws OverflowException, InvalidInputException, UndefinedResultException {
      ResponseDto response = calculator.executeSpecialOperation(operation);
      parseDto(response);
      checkException();
   }

   private void clear(){
      ResponseDto response = calculator.clear();
      parseDto(response);
   }

   private void memoryClear() {
      calculator.getContainer().getMemory().clear();
      memory = validator.showNumber(calculator.getContainer().getMemory().recall());
   }

   private void memoryRecall() {
      parseDto(calculator.getMemory());
   }

   private void addMemory() {
      memory = validator.showNumber(calculator.addMemory());
   }

   private void subtractMemory() {
      memory = validator.showNumber(calculator.subtractMemory());
   }

   private void clearEntry(){
      ResponseDto response = calculator.clearEntry();
      parseDto(response);
   }

   private void executeBackSpace() {
      ResponseDto response = calculator.backspace();
      operand = validator.builtOperand(response.getOperand(), response.isSeparated());
   }

   private void executeSeparate() {
      ResponseDto response = calculator.separateOperand();
      operand = validator.builtOperand(response.getOperand(), response.isSeparated());
   }

   private void equals() {
      ResponseDto response = calculator.equalsOperation();
      parseDto(response);
   }

   private void checkResult(String expected) {
      assertEquals(expected, result);
   }

   private void checkOperand(String expected) {
      assertEquals(expected, operand);
   }

   private void buildOperand(Number number) {
      ResponseDto response = calculator.buildOperand(number);
      operand = validator.builtOperand(response.getOperand(), response.isSeparated());
   }

   private void checkHistory(String expectedHistory, int size) {
      History history = calculator.getContainer().getHistory();

      assertEquals(size, history.size());
      assertEquals(expectedHistory, history.buildHistory());
   }

   private void checkException() throws OverflowException, UndefinedResultException, InvalidInputException {
      switch (exceptionType){
         case OVERFLOW:
            throw new OverflowException();
         case UNDEFINED_RESULT:
            throw new UndefinedResultException();
         case DIVIDE_BY_ZERO:
            throw new ArithmeticException();
         case INVALID_INPUT:
            throw new InvalidInputException();
      }
   }
   private void parseDto(ResponseDto response) {
      if(response.getExceptionType() != null) {
         exceptionType = response.getExceptionType();
      }

      if(response.getResult() != null) {
         result = validator.showNumber(response.getResult());
      }

      if(response.getOperand() != null) {
         operand = validator.showNumber(response.getOperand().stripTrailingZeros());
      }

      if(response.getHistory() != null) {
        history = response.getHistory().buildHistory();
      }

      isSeparatedOperand = response.isSeparated();
   }
}
