package com.implemica.model.numerals;

import com.implemica.model.interfaces.Numeral;
import com.implemica.model.numerals.numbers.Number;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArabicTest {

   private static Numeral arabic;

   @BeforeAll
   public static void init(){
      arabic = new Arabic();
   }

   @Test
   void translate() {
      check(Number.ZERO, '0');
      check(Number.ONE, '1');
      check(Number.TWO, '2');
      check(Number.THREE, '3');
      check(Number.FOUR, '4');
      check(Number.FIVE, '5');
      check(Number.SIX, '6');
      check(Number.SEVEN, '7');
      check(Number.EIGHT, '8');
      check(Number.NINE, '9');
   }

   private void check(Number number, char expected){
      assertEquals(expected, arabic.translate(number));
   }
}