package com.implemica.model.calculator;

import com.implemica.model.calculator.until.TestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalculatorTest {

   private TestBuilder builder;

   @BeforeEach
   void init(){
      builder = new TestBuilder();
   }

   @Test
   void manySimpleOperations() {
      builder.doTest("2+", "2 + ", 2, "2", null);

      builder.doTest("3+++++", "3 + ", 2, "3", null);
      builder.doTest("5-----", "5 - ", 2, "5", null);
      builder.doTest("8*****", "8 * ", 2, "8", null);
      builder.doTest("12/////", "12 / ", 2, "12", null);
   }

   @Test
   void simpleOperationBeforeEquals() {
      builder.doTest("2++++=", "", 0, "4", null);
      builder.doTest("4----=", "", 0, "0", null);
      builder.doTest("8****=", "", 0, "64", null);
      builder.doTest("100/////=", "", 0, "1", null);
   }

   @Test
   void manyEquals() {
      builder.doTest("1+3===", "", 0, "10", null);
      builder.doTest("289-102===", "", 0, "-17", null);
      builder.doTest("2*3===", "", 0, "54", null);
      builder.doTest("188/2===", "", 0, "23.5", null);
   }

   @Test
   void EqualsTests() {
      builder.doTest("2+3=+++", "5 + ", 2, "5", null);
      builder.doTest("239*4=+-*/", "956 / ", 2, "956", null);
      builder.doTest("10+=", "", 0, "20", null);
      builder.doTest("10+=+=", "", 0, "40", null);
      builder.doTest("10+=+=+=", "", 0, "80", null);
   }

   @Test
   void otherTests() {
      builder.doTest("70/7=<<<", "", 0, "10", null);
      builder.doTest("7+3=1+", "1 + ", 2, "1", null);
      builder.doTest("2+3=4===", "", 0, "13", null);
   }

   @Test
   void percentTest() {
      builder.doTest("200+2p", "200 + 4 ", 2, "200", null);
      builder.doTest("200+2p=", "", 0, "204", null);
      builder.doTest("200+2p=p", "416.16 ", 1,  null, "416.16");
      builder.doTest("199+1=", "", 0, "200", null);
   }
}