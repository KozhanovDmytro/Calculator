package com.implemica.model;

import com.implemica.model.calculator.Calculator;
import com.implemica.model.dto.ResponseDto;
import com.implemica.model.numerals.numbers.Number;
import com.implemica.model.operations.simple.Divide;
import com.implemica.model.operations.simple.Minus;
import com.implemica.model.operations.simple.Plus;
import com.implemica.model.operations.special.Square;

public class ModelDemo {

   private static Number a = Number.ONE;
   private static Number b = Number.TWO;
   private static Number c = Number.ZERO;
   private static Number d = Number.NINE;

   public static void main(String[] args) {
      Calculator calculator = new Calculator();

      calculator.buildOperand(a);
      calculator.executeSimpleOperation(new Plus());
      calculator.buildOperand(b);
      calculator.executeSpecialOperation(new Square());
      calculator.equalsOperation();
      calculator.executeSimpleOperation(new Divide());
      calculator.buildOperand(c);
      calculator.executeSimpleOperation(new Minus());
      calculator.buildOperand(d);

      ResponseDto response = calculator.equalsOperation();
      System.out.println(response.getResult());

   }
}
