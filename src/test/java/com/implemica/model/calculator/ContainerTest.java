package com.implemica.model.calculator;

import com.implemica.model.operations.Default;
import com.implemica.model.operations.SimpleOperation;
import com.implemica.model.operations.simple.Divide;
import com.implemica.model.operations.simple.Minus;
import com.implemica.model.operations.simple.Multiply;
import com.implemica.model.operations.simple.Plus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ContainerTest {

   private Container container;

   @BeforeEach
   public void init(){
      container = new Container();
   }

   @Test
   public void minusOperations() {
      calculate(new Default(), "10");
      calculate(new Minus(), "1");
      calculate(new Minus(), "2");
      calculate(new Minus(), "3");
      calculate(new Minus(), "4");

      checkResult("0");
   }

   @Test
   public void plusOperations() {
      calculate(new Default(), "10");

      calculate(new Plus(), "1");
      calculate(new Plus(), "2");
      calculate(new Plus(), "3");
      calculate(new Plus(), "4");

      checkResult("20");
   }

   @Test
   public void multiplyOperations() {
      calculate(new Default(), "2");

      calculate(new Multiply(), "3");
      calculate(new Multiply(), "4");
      calculate(new Multiply(), "5");

      checkResult("120");
   }

   @Test
   public void divideOperations() {
      calculate(new Default(), "90");

      calculate(new Divide(), "3");
      calculate(new Divide(), "10");

      checkResult("3");
   }

   private void calculate(SimpleOperation operation, String number){
      operation.setShowOperand(true);
      for (char c : number.toCharArray()) {
         operation.buildOperand(c);
      }
      container.setOperation(operation);
      container.calculate();
   }

   private void checkResult(String number){
      assertEquals(new BigDecimal(number), container.getResult());
   }

}