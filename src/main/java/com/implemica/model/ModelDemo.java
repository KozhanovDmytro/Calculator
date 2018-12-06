package com.implemica.model;

import com.implemica.model.calculator.Calculator;
import com.implemica.model.exceptions.CalculatorException;
import com.implemica.model.operations.simple.Divide;
import com.implemica.model.operations.simple.Minus;
import com.implemica.model.operations.simple.Plus;
import com.implemica.model.operations.special.Square;

import java.math.BigDecimal;

/**
 * Class for demonstration model in console.
 */
public class ModelDemo {

   private static BigDecimal a = BigDecimal.ONE;
   private static BigDecimal b = BigDecimal.valueOf(2);
   private static BigDecimal c = BigDecimal.ZERO;
   private static BigDecimal d = BigDecimal.valueOf(9);

   public static void main(String[] args) {
      Calculator calculator = new Calculator();

      try {
         calculator.buildOperand(a);
         calculator.executeSimpleOperation(new Plus());
         calculator.buildOperand(b);
         calculator.executeSpecialOperation(new Square());
         calculator.equalsOperation();
         calculator.executeSimpleOperation(new Divide());
         calculator.buildOperand(c);
         calculator.executeSimpleOperation(new Minus());
         calculator.buildOperand(d);

         System.out.println("Result: " + calculator.equalsOperation().getResult());
      } catch (CalculatorException e) {
         System.out.println("Result: " + e.getExceptionType() + " is not allowed");
      }
   }
}
