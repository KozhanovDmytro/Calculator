package com.implemica.model.calculator.until;

import com.implemica.model.calculator.Calculator;
import com.implemica.model.dto.ResponseDto;
import com.implemica.model.exceptions.ExceptionType;
import com.implemica.model.exceptions.TestException;
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
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBuilder {

   private Calculator calculator;

   private String result;

   private String operand;

   private String memory;

   private ExceptionType exceptionType;

   public TestBuilder() {
      calculator = new Calculator(new Arabic());
   }

   public void doTest(String pattern, String history, int historySize, String result, String operand) {
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

   }

   private void checkBySymbols(String pattern) {
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

   private void executeSimpleOperation(SimpleOperation operation) {
      ResponseDto response = calculator.executeSimpleOperation(operation);
      result = response.getResult();
      exceptionType = response.getExceptionType();

      if(exceptionType != ExceptionType.NOTHING){
         throw new TestException(exceptionType);
      }
   }

   private void executeSpecialOperation(SpecialOperation operation) {
      ResponseDto response = calculator.executeSpecialOperation(operation);
      operand = response.getOperand();
      exceptionType = response.getExceptionType();

      if(exceptionType != ExceptionType.NOTHING){
         throw new TestException(exceptionType);
      }
   }

   private void clear(){
      ResponseDto response = calculator.clear();
      result = response.getResult();
      operand = response.getOperand();
   }

   private void memoryClear() {
      calculator.getContainer().getMemory().clear();
   }

   private void memoryRecall() {
      operand = calculator.getMemory().getOperand();
   }

   private void addMemory() {
      memory = calculator.addMemory();
   }

   private void subtractMemory() {
      memory = calculator.subtractMemory();
   }

   private void clearEntry(){
      ResponseDto response = calculator.clearEntry();
      result = response.getResult();
      operand = response.getOperand();
   }

   private void executeBackSpace() {
      operand = calculator.backspace().getOperand();
   }

   private void executeSeparate() {
      operand = calculator.separateOperand().getOperand();
   }

   private void equals() {
      result = calculator.equalsOperation().getResult();
   }

   private void checkResult(String expected) {
      assertEquals(expected, result);
   }

   private void checkOperand(String expected) {
      assertEquals(expected, operand);
   }

   private void buildOperand(Number number) {
      operand = calculator.buildOperand(number).getOperand();
   }

   private void checkHistory(String expectedHistory, int size) {
      History history = calculator.getContainer().getHistory();

      assertEquals(size, history.size());
      assertEquals(expectedHistory, history.buildHistory());
   }
}
