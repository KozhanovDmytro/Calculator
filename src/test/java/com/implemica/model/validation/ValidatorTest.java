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
   }

   @Test
   void showNumberForSystem() {

   }

   @Test
   void showComfortableNumberSeparated(){

   }

   private void checkCN(String number, String expected){
      assertEquals(expected, validator.showNumber(new BigDecimal(number)));
   }
}