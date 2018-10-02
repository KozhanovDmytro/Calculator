package com.implemica.model.validation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

   private static Validator validator;

   @BeforeAll
   public static void init(){
      validator = new Validator();
   }

   @Test
   void showComfortableNumber() {

      checkCN("0", "0");
      checkCN("00", "0");
      checkCN("0000000", "0");
      checkCN("0000000000000000", "0");
      checkCN("00000000000000000", "0");

      checkCN("1", "1");
      checkCN("10", "10");
      checkCN("100", "100");
      checkCN("1000", "1 000");
      checkCN("10000", "10 000");
      checkCN("100000", "100 000");
      checkCN("1000000", "1 000 000");
      checkCN("10000000", "10 000 000");
      checkCN("100000000", "100 000 000");
      checkCN("1000000000", "1 000 000 000");
      checkCN("10000000000", "10 000 000 000");
      checkCN("100000000000", "100 000 000 000");
      checkCN("1000000000000", "1 000 000 000 000");
      checkCN("10000000000000", "10 000 000 000 000");
      checkCN("100000000000000", "100 000 000 000 000");
      checkCN("1000000000000000", "1 000 000 000 000 000");

      checkCN("10000000000000000", "1,e+16");
      checkCN("100000000000000000", "1,e+17");
      checkCN("1000000000000000000", "1,e+18");

      checkCN("0.0", "0,0");
      checkCN("0.00", "0,00");
      checkCN("0.000", "0,000");
      checkCN("0.0000", "0,0000");
      checkCN("0.00000", "0,00000");
      checkCN("0.000000", "0,000000");
      checkCN("0.0000000", "0,0000000");
      checkCN("0.00000000", "0,00000000");
      checkCN("0.000000000", "0,000000000");
      checkCN("0.0000000000", "0,0000000000");
      checkCN("0.00000000000", "0,00000000000");
      checkCN("0.000000000000", "0,000000000000");
      checkCN("0.0000000000000", "0,0000000000000");
      checkCN("0.00000000000000", "0,00000000000000");
      checkCN("0.000000000000000", "0,000000000000000");
      checkCN("0.0000000000000000", "0,0000000000000000");

      checkCN("0.1", "0,1");
      checkCN("0.01", "0,01");
      checkCN("0.001", "0,001");
      checkCN("0.0001", "0,0001");
      checkCN("0.00001", "0,00001");
      checkCN("0.000001", "0,000001");
      checkCN("0.0000001", "0,0000001");
      checkCN("0.00000001", "0,00000001");
      checkCN("0.000000001", "0,000000001");
      checkCN("0.0000000001", "0,0000000001");
      checkCN("0.00000000001", "0,00000000001");
      checkCN("0.000000000001", "0,000000000001");
      checkCN("0.0000000000001", "0,0000000000001");
      checkCN("0.00000000000001", "0,00000000000001");
      checkCN("0.000000000000001", "0,000000000000001");
      checkCN("0.0000000000000001", "0,0000000000000001");

//      TODO do not forget about this.
//      checkCN("0.00000000000000001", "1,e-17");
//      checkCN("0.000000000000000001", "1,e-18");
//      checkCN("0.0000000000000000001", "1,e-19");
//      checkCN("0.00000000000000000001", "1,e-20");
//      checkCN("0.000000000000000000001", "1,e-21");
//      checkCN("0.0000000000000000000001", "1,e-22");
//      checkCN("0.00000000000000000000001", "1,e-23");
//      checkCN("0.000000000000000000000001", "1,e-24");

      checkCN("1.000000000000000", "1,000000000000000");
      checkCN("10.00000000000000", "10,00000000000000");
      checkCN("100.0000000000000", "100,0000000000000");
      checkCN("1000.000000000000", "1 000,000000000000");
      checkCN("10000.00000000000", "10 000,00000000000");
      checkCN("100000.0000000000", "100 000,0000000000");
      checkCN("1000000.000000000", "1 000 000,000000000");
      checkCN("10000000.00000000", "10 000 000,00000000");
      checkCN("100000000.0000000", "100 000 000,0000000");
      checkCN("1000000000.000000", "1 000 000 000,000000");
      checkCN("10000000000.00000", "10 000 000 000,00000");
      checkCN("100000000000.0000", "100 000 000 000,0000");
      checkCN("1000000000000.000", "1 000 000 000 000,000");
      checkCN("10000000000000.00", "10 000 000 000 000,00");
      checkCN("100000000000000.0", "100 000 000 000 000,0");
      checkCN("1000000000000000.", "1 000 000 000 000 000");


      checkCN("10000000000.1", "10 000 000 000,1");
      checkCN("10000000000.14568", "10 000 000 000,14568");
   }

   @Test
   void showComfortableNumberSeparated(){
      checkCNS("1", "1,");
      checkCNS("1000", "1 000,");
      checkCNS("1000000", "1 000 000,");
   }

   private void checkCN(String number, String expected){
      assertEquals(expected, validator.showNumber(new BigDecimal(number, MathContext.DECIMAL64), false));
   }

   private void checkCNS(String number, String expected){
      assertEquals(expected, validator.showNumber(new BigDecimal(number, MathContext.DECIMAL64), true));
   }
}