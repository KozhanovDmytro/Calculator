package com.implemica.model;

import com.implemica.model.calculator.Calculator;
import com.implemica.model.dto.ResponseDto;
import com.implemica.model.numerals.numbers.Number;
import com.implemica.model.operations.simple.Divide;
import com.implemica.model.operations.simple.Minus;
import com.implemica.model.operations.simple.Plus;
import com.implemica.model.operations.special.Square;

public class ModelDemo {
   public static void main(String[] args) {
      Calculator calculator = new Calculator();

      calculator.buildOperand(Number.THREE);
      calculator.executeSimpleOperation(new Plus());
      calculator.buildOperand(Number.FOUR);
      calculator.executeSpecialOperation(new Square());
      calculator.equalsOperation();
      calculator.executeSimpleOperation(new Divide());
      calculator.buildOperand(Number.ONE);
      calculator.buildOperand(Number.ZERO);
      calculator.executeSimpleOperation(new Minus());
      calculator.buildOperand(Number.FIVE);

      ResponseDto response = calculator.equalsOperation();
      System.out.println(response.getResult());

   }
}
