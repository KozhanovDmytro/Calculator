package com.implemica.model.calculator;

import com.implemica.model.exceptions.CalculatorException;
import com.implemica.model.operations.Default;
import com.implemica.model.operations.operation.Number;
import com.implemica.model.operations.operation.SimpleOperation;
import com.implemica.model.operations.simple.Divide;
import com.implemica.model.operations.simple.Minus;
import com.implemica.model.operations.simple.Multiply;
import com.implemica.model.operations.simple.Plus;
import com.implemica.controller.util.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.implemica.model.operations.operation.Number.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContainerTest {

   private Container container;

   private Validator validator = new Validator();

   @BeforeEach
   void init() {
      container = new Container();
   }

   @Test
   void minusOperations() throws CalculatorException {
      calculate(new Default(), new Number[] {ONE, ZERO});

      calculate(new Minus(), new Number[] {ONE});
      calculate(new Minus(), new Number[] {TWO});
      calculate(new Minus(), new Number[] {THREE});
      calculate(new Minus(), new Number[] {FOUR});

      checkResult("0");
   }

   @Test
   void plusOperations() throws CalculatorException {
      calculate(new Default(), new Number[] {ONE, ZERO});

      calculate(new Plus(), new Number[] {ONE});
      calculate(new Plus(), new Number[] {TWO});
      calculate(new Plus(), new Number[] {THREE});
      calculate(new Plus(), new Number[] {FOUR});

      checkResult("20");
   }

   @Test
   void multiplyOperations() throws CalculatorException {
      calculate(new Default(), new Number[] {TWO});

      calculate(new Multiply(), new Number[] {THREE});
      calculate(new Multiply(), new Number[] {FOUR});
      calculate(new Multiply(), new Number[] {FIVE});

      checkResult("120");
   }

   @Test
   void divideOperations() throws CalculatorException {
      calculate(new Default(), new Number[] {NINE, ZERO});

      calculate(new Divide(), new Number[] {THREE});
      calculate(new Divide(), new Number[] {ONE, ZERO});

      checkResult("3");
   }

   private void calculate(SimpleOperation operation, Number[] numbers) throws CalculatorException {
      for (Number number : numbers) {
         operation.buildOperand(number);
      }
      container.setOperation(operation);
      container.calculate();
   }

   private void checkResult(String number) {
      assertEquals(number, validator.showNumber(container.getResult()));
   }

}