package com.implemica.model.validation;

import com.implemica.model.calculator.until.TestBuilder;
import com.implemica.model.exceptions.InvalidInputException;
import com.implemica.model.exceptions.OverflowException;
import com.implemica.model.exceptions.UndefinedResultException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ValidatorTest {

   private static TestBuilder builder;

   @BeforeAll
   public static void init() {
      builder = new TestBuilder();
   }

   @Test
   void showComfortableNumber() throws OverflowException, InvalidInputException, UndefinedResultException {
      builder.checkBuildOperand("0", "0");
      builder.checkBuildOperand("00", "0");
      builder.checkBuildOperand("0000000000", "0");
      builder.checkBuildOperand("000000000000000000000000", "0");
      builder.checkBuildOperand("00000000000000000000000000000000000000000000000", "0");

      builder.checkBuildOperand("1", "1");
      builder.checkBuildOperand("10", "10");
      builder.checkBuildOperand("100", "100");
      builder.checkBuildOperand("1000", "1 000");
      builder.checkBuildOperand("10000", "10 000");
      builder.checkBuildOperand("100000", "100 000");
      builder.checkBuildOperand("1000000", "1 000 000");
      builder.checkBuildOperand("10000000", "10 000 000");
      builder.checkBuildOperand("100000000", "100 000 000");
      builder.checkBuildOperand("1000000000", "1 000 000 000");
      builder.checkBuildOperand("10000000000", "10 000 000 000");
      builder.checkBuildOperand("100000000000", "100 000 000 000");
      builder.checkBuildOperand("1000000000000", "1 000 000 000 000");
      builder.checkBuildOperand("10000000000000", "10 000 000 000 000");
      builder.checkBuildOperand("100000000000000", "100 000 000 000 000");
      builder.checkBuildOperand("1000000000000000", "1 000 000 000 000 000");

      builder.checkBuildOperand("0", "0");
      builder.checkBuildOperand("0.", "0,");
      builder.checkBuildOperand("0.0", "0,0");
      builder.checkBuildOperand("0.00", "0,00");
      builder.checkBuildOperand("0.000", "0,000");
      builder.checkBuildOperand("0.0000", "0,0000");
      builder.checkBuildOperand("0.00000", "0,00000");
      builder.checkBuildOperand("0.000000", "0,000000");
      builder.checkBuildOperand("0.0000000", "0,0000000");
      builder.checkBuildOperand("0.00000000", "0,00000000");
      builder.checkBuildOperand("0.000000000", "0,000000000");
      builder.checkBuildOperand("0.0000000000", "0,0000000000");
      builder.checkBuildOperand("0.00000000000", "0,00000000000");
      builder.checkBuildOperand("0.000000000000", "0,000000000000");
      builder.checkBuildOperand("0.0000000000000", "0,0000000000000");
      builder.checkBuildOperand("0.00000000000000", "0,00000000000000");
      builder.checkBuildOperand("0.000000000000000", "0,000000000000000");
      builder.checkBuildOperand("0.0000000000000000", "0,0000000000000000");

      builder.checkBuildOperand("0.1", "0,1");
      builder.checkBuildOperand("0.01", "0,01");
      builder.checkBuildOperand("0.001", "0,001");
      builder.checkBuildOperand("0.0001", "0,0001");
      builder.checkBuildOperand("0.00001", "0,00001");
      builder.checkBuildOperand("0.000001", "0,000001");
      builder.checkBuildOperand("0.0000001", "0,0000001");
      builder.checkBuildOperand("0.00000001", "0,00000001");
      builder.checkBuildOperand("0.000000001", "0,000000001");
      builder.checkBuildOperand("0.0000000001", "0,0000000001");
      builder.checkBuildOperand("0.00000000001", "0,00000000001");
      builder.checkBuildOperand("0.000000000001", "0,000000000001");
      builder.checkBuildOperand("0.0000000000001", "0,0000000000001");
      builder.checkBuildOperand("0.00000000000001", "0,00000000000001");
      builder.checkBuildOperand("0.000000000000001", "0,000000000000001");
      builder.checkBuildOperand("0.0000000000000001", "0,0000000000000001");

      builder.checkBuildOperand("1.0000000000000000", "1,000000000000000");
      builder.checkBuildOperand("10.000000000000000", "10,00000000000000");
      builder.checkBuildOperand("100.00000000000000", "100,0000000000000");
      builder.checkBuildOperand("1000.0000000000000", "1 000,000000000000");
      builder.checkBuildOperand("10000.000000000000", "10 000,00000000000");
      builder.checkBuildOperand("100000.00000000000", "100 000,0000000000");
      builder.checkBuildOperand("1000000.0000000000", "1 000 000,000000000");
      builder.checkBuildOperand("10000000.000000000", "10 000 000,00000000");
      builder.checkBuildOperand("100000000.00000000", "100 000 000,0000000");
      builder.checkBuildOperand("1000000000.0000000", "1 000 000 000,000000");
      builder.checkBuildOperand("10000000000.000000", "10 000 000 000,00000");
      builder.checkBuildOperand("100000000000.00000", "100 000 000 000,0000");
      builder.checkBuildOperand("1000000000000.0000", "1 000 000 000 000,000");
      builder.checkBuildOperand("10000000000000.000", "10 000 000 000 000,00");
      builder.checkBuildOperand("100000000000000.00", "100 000 000 000 000,0");
      builder.checkBuildOperand("1000000000000000.0", "1 000 000 000 000 000,");

      builder.checkBuildOperand("10000000000.1", "10 000 000 000,1");
      builder.checkBuildOperand("10000000000.14568", "10 000 000 000,14568");
      builder.checkBuildOperand("10000000000.100000000", "10 000 000 000,10000");
      builder.checkBuildOperand("10000000000.000000000001", "10 000 000 000,00000");
      builder.checkBuildOperand("10000000000.00001", "10 000 000 000,00001");
   }
}