package com.implemica.model.calculator.until;

import com.implemica.controller.util.HistoryParser;
import com.implemica.model.calculator.Calculator;
import com.implemica.model.calculator.Container;
import com.implemica.model.dto.ResponseDto;
import com.implemica.model.exceptions.CalculatorException;
import com.implemica.model.exceptions.ExceptionType;
import com.implemica.model.history.History;
import com.implemica.model.operations.operation.Number;
import com.implemica.model.operations.operation.SimpleOperation;
import com.implemica.model.operations.operation.SpecialOperation;
import com.implemica.model.operations.simple.Divide;
import com.implemica.model.operations.simple.Minus;
import com.implemica.model.operations.simple.Multiply;
import com.implemica.model.operations.simple.Plus;
import com.implemica.model.operations.special.*;
import com.implemica.controller.util.Validator;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBuilder {

   private Calculator calculator = new Calculator();

   private BigDecimal result;

   private BigDecimal operand;

   private String builtOperand;

   private Validator validator = new Validator();

   private HistoryParser historyParser = new HistoryParser();

   /* operations */

   private static Plus plus;

   private static Minus minus;

   private static Multiply multiply;

   private static Divide divide;

   /* special */

   private static Percent percent = new Percent();

   private static SquareRoot squareRoot = new SquareRoot();

   private static Square square = new Square();

   private static DivideBy divideBy = new DivideBy();

   private static Negate negate = new Negate();

   static {

      squareRoot.setFirstPartHistory("√(");
      squareRoot.setSecondPartHistory(")");

      percent.setFirstPartHistory("");
      percent.setSecondPartHistory("");

      square.setFirstPartHistory("sqr(");
      square.setSecondPartHistory(")");

      divideBy.setFirstPartHistory("1/(");
      divideBy.setSecondPartHistory(")");

      negate.setFirstPartHistory("negate(");
      negate.setSecondPartHistory(")");
   }

   public void doExceptionsTest(String pattern, ExceptionType expectedExceptionType) {
      try{
         doTest(pattern, null, 0, null, null);
      } catch(CalculatorException e) {
         assertEquals(expectedExceptionType, e.getExceptionType());
      }
   }

   public void doTest(String pattern, String history, int historySize, String result, String operand) throws CalculatorException {
      initializeSimpleOperation();
      calculator = new Calculator();
      this.result = BigDecimal.ZERO;
      this.operand = BigDecimal.ZERO;
      this.builtOperand = "0";
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
            case "SQR":
               executeSpecialOperation(square);
               break;
            case "1/x":
               executeSpecialOperation(divideBy);
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

   public void doBoundaryTest(String pattern, String expected) throws CalculatorException {
      doTest(pattern, null, 0, null, null);

      if(expected != null) {
         assertEquals(new BigDecimal(expected), result);
      }
   }

   public void checkBuildOperand(String pattern, String expected) throws CalculatorException {
      doTest(pattern, "", 0, null, null);
      assertEquals(expected, builtOperand);
   }

   private void checkBySymbols(String pattern) throws CalculatorException {
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
               executeSimpleOperation(plusFactory());
               break;
            case '-':
               executeSimpleOperation(minusFactory());
               break;
            case '×':
               executeSimpleOperation(multiplyFactory());
               break;
            case '÷':
               executeSimpleOperation(divideFactory());
               break;
            case '=':
               equals();
               break;
            case '%':
               executeSpecialOperation(percent);
               break;
            case '√':
               executeSpecialOperation(squareRoot);
               break;
            case '<':
               executeBackSpace();
               break;
            case 'n':
               executeSpecialOperation(negate);
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

   private void executeBackSpace() {
      ResponseDto response = calculator.backspace();
      if(response.getOperand() != null) {
         builtOperand = validator.builtOperand(response.getOperand(), response.isSeparated());
      }
   }

   private void executeSeparate() {
      ResponseDto response = calculator.separateOperand();
      builtOperand = validator.builtOperand(response.getOperand(), response.isSeparated());
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

   private void buildOperand(Number number) {
      ResponseDto response = calculator.buildOperand(number);
      operand = response.getOperand();
      builtOperand = validator.builtOperand(response.getOperand(), response.isSeparated());
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

   private void initializeSimpleOperation() {
      plus = new Plus();
      minus = new Minus();
      multiply = new Multiply();
      divide = new Divide();

      plus.setCharacter("+");
      multiply.setCharacter("×");
      minus.setCharacter("-");
      divide.setCharacter("÷");
   }

   private Plus plusFactory() {
      Plus plus = new Plus();
      plus.setCharacter("+");

      return plus;
   }

   private Minus minusFactory() {
      Minus minus = new Minus();
      minus.setCharacter("-");

      return minus;
   }

   private Divide divideFactory() {
      Divide divide = new Divide();
      divide.setCharacter("÷");

      return divide;
   }

   private Multiply multiplyFactory() {
      Multiply multiply = new Multiply();
      multiply.setCharacter("×");

      return multiply;
   }
}
