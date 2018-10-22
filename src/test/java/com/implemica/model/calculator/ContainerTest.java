package com.implemica.model.calculator;

import com.implemica.model.exceptions.InvalidInputException;
import com.implemica.model.exceptions.OverflowException;
import com.implemica.model.exceptions.UndefinedResultException;
import com.implemica.model.operations.Default;
import com.implemica.model.operations.SimpleOperation;
import com.implemica.model.operations.simple.Divide;
import com.implemica.model.operations.simple.Minus;
import com.implemica.model.operations.simple.Multiply;
import com.implemica.model.operations.simple.Plus;
import com.implemica.model.validation.Validator;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Ignore
class ContainerTest {

   private Container container;

   private Validator validator = new Validator();

   @BeforeEach
   void init(){
      container = new Container();
   }

   @Test
   void minusOperations() throws OverflowException, UndefinedResultException, InvalidInputException {
      calculate(new Default(), "10");
      calculate(new Minus(), "1");
      calculate(new Minus(), "2");
      calculate(new Minus(), "3");
      calculate(new Minus(), "4");

      checkResult("0");
   }

   @Test
   void plusOperations() throws OverflowException, UndefinedResultException, InvalidInputException {
      calculate(new Default(), "10");

      calculate(new Plus(), "1");
      calculate(new Plus(), "2");
      calculate(new Plus(), "3");
      calculate(new Plus(), "4");

      checkResult("20");
   }

   @Test
   void multiplyOperations() throws OverflowException, UndefinedResultException, InvalidInputException {
      calculate(new Default(), "2");

      calculate(new Multiply(), "3");
      calculate(new Multiply(), "4");
      calculate(new Multiply(), "5");

      checkResult("120");
   }

   @Test
   void divideOperations() throws OverflowException, UndefinedResultException, InvalidInputException {
      calculate(new Default(), "90");

      calculate(new Divide(), "3");
      calculate(new Divide(), "10");

      checkResult("3");
   }

   private void calculate(SimpleOperation operation, String number) throws OverflowException, UndefinedResultException, InvalidInputException {
      operation.setShowOperand(true);
      for (char c : number.toCharArray()) {
         operation.buildOperand(c);
      }
      container.setOperation(operation);
      container.calculate();
   }

   private void checkResult(String number){
      assertEquals(number, validator.showNumber(container.getResult()));
   }

}