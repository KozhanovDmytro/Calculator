package com.implemica.model;

import com.implemica.model.calculator.Calculator;
import com.implemica.model.dto.ResponseDto;
import com.implemica.model.exceptions.InvalidInputException;
import com.implemica.model.exceptions.UndefinedResultException;
import com.implemica.model.interfaces.SpecialOperation;
import com.implemica.model.numerals.Arabic;
import com.implemica.model.numerals.numbers.Number;
import com.implemica.model.operations.SimpleOperation;
import com.implemica.model.operations.simple.Divide;
import com.implemica.model.operations.simple.Minus;
import com.implemica.model.operations.simple.Plus;
import com.implemica.model.operations.special.Square;
import com.implemica.model.validation.Validator;

import java.math.BigDecimal;

public class ModelDemo {
   public static void main(String[] args) {

      Calculator calculator = new Calculator();
      Validator validator = new Validator();

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
      System.out.println(validator.showNumber(response.getResult()));

   }
}
