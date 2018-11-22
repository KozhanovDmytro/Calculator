package com.implemica.model.numerals;

import com.implemica.model.operations.operation.Number;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArabicTest {

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

   private void check(Number number, char expected) {
      assertEquals(expected, number.translate());
   }
}