package com.implemica.model.calculator.until;

import com.implemica.model.calculator.Calculator;
import com.implemica.model.interfaces.History;
import com.implemica.model.interfaces.SpecialOperation;
import com.implemica.model.numerals.Arabic;
import com.implemica.model.numerals.numbers.Number;
import com.implemica.model.operations.SimpleOperation;
import com.implemica.model.operations.simple.Divide;
import com.implemica.model.operations.simple.Minus;
import com.implemica.model.operations.simple.Multiply;
import com.implemica.model.operations.simple.Plus;
import com.implemica.model.operations.special.DivideBy;
import com.implemica.model.operations.special.Percent;
import com.implemica.model.operations.special.Square;
import com.implemica.model.operations.special.SquareRoot;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBuilder {

   private Calculator calculator;

   public TestBuilder() {
      calculator = new Calculator(new Arabic());
   }

   public void doTest(String pattern, String history, int historySize, String result, String operand) {
      /**
       *
       */
      calculator = new Calculator(new Arabic());
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
            case 'p':
               executeSpecialOperation(new Percent());
               break;
            case 'q':
               executeSpecialOperation(new SquareRoot());
               break;
            case 's':
               executeSpecialOperation(new Square());
               break;
            case 'f':
               executeSpecialOperation(new DivideBy());
               break;
            case '<':
               executeBackSpace();
               break;
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

   private void executeSimpleOperation(SimpleOperation operation) {
      calculator.executeSimpleOperation(operation);
      calculator.showResult();
   }

   private void executeSpecialOperation(SpecialOperation operation) {
      calculator.executeSpecialOperation(operation);
      calculator.showOperand();
   }

   private void executeBackSpace() {
      calculator.backspace();
   }

   private void equals() {
      calculator.equalsOperation();
      calculator.showResult();
   }

   private void checkResult(String expected) {
      assertEquals(expected, calculator.getContainer().getResult().toString());
   }

   private void checkOperand(String expected) {
      assertEquals(expected, calculator.getContainer().getOperation().getOperand().toString());
   }

   private void buildOperand(Number number) {
      calculator.buildOperand(number);
      calculator.showOperand();
   }

   private void checkHistory(String expectedHistory, int size) {
      History history = calculator.getContainer().getHistory();

      assertEquals(size, history.size());
      assertEquals(expectedHistory, history.buildHistory());
   }
}
