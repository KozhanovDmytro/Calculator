package com.implemica.model.validation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

   private static Validator validator;

   @BeforeAll
   public static void init(){
      validator = new Validator();
   }

   @Test
   void showComfortableNumber() {
      checkCN("0000000000", "0");
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

      checkCN("0.0000000000000001", "1,e-16");
      checkCN("0.00000000000000001", "1,e-17");
      checkCN("0.000000000000000001", "1,e-18");
      checkCN("0.0000000000000000001", "1,e-19");
      checkCN("0.00000000000000000001", "1,e-20");
      checkCN("0.000000000000000000001", "1,e-21");
      checkCN("0.0000000000000000000001", "1,e-22");
      checkCN("0.00000000000000000000001", "1,e-23");
      checkCN("0.000000000000000000000001", "1,e-24");

      checkCN("10000000000.1", "10 000 000 000,1");
      checkCN("10000000000.1456897654", "10 000 000 000,1456897654");
   }

   @Test
   void showNumberForSystem() {

   }

   @Test
   void showComfortableNumberSeparated(){
      checkCNS("1", "1,");
      checkCNS("1000", "1 000,");
      checkCNS("1000000", "1 000 000,");
   }

   private void checkCN(String number, String expected){
      assertEquals(expected, validator.showNumber(new BigDecimal(number)));
   }

   private void checkCNS(String number, String expected){
      assertEquals(expected, validator.showNumber(new BigDecimal(number), true));
   }
}