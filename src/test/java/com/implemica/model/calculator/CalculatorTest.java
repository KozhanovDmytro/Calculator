package com.implemica.model.calculator;

import com.implemica.model.calculator.until.TestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalculatorTest {

   private TestBuilder builder;

   private final String SQRT = "√";

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

      builder.doTest("+7", "0 + 7 ", 2, "0", "7");
      builder.doTest("+7=", "", 0, "7", "0");
      builder.doTest("-7", "0 - 7 ", 2, "0", "7");
      builder.doTest("-7=", "", 0, "-7", "0");
      builder.doTest("*4", "0 * 4 ", 2, "0", "4");
      builder.doTest("*4=", "", 0, "0", "0");
      builder.doTest("/8", "0 / 8 ", 2, "0", "8");
      builder.doTest("/8=", "", 0, "0", "0");
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
      builder.doTest("4q", SQRT + "(4) ", 1, "0", "2");
      builder.doTest("4q=", "", 0, "2", "0");
      builder.doTest("8s", "sqr(8) ", 1, "0", "64");
      builder.doTest("8s=", "", 0, "64", "0");
      builder.doTest("2f", "1/(2) ", 1, "0", "0.5");
      builder.doTest("2f=", "", 0, "0.5", "0");
   }

   @Test
   void percentTest() {
      builder.doTest("2p", "0 ", 1, "0", "0");
      builder.doTest("200+2p", "200 + 4 ", 2, "200", null);
      builder.doTest("200+2p=", "", 0, "204", null);
      builder.doTest("200+2p=p", "416.16 ", 1,  null, "416.16");
      builder.doTest("199+1=", "", 0, "200", "0");
      builder.doTest("199+1=p", "400 ", 1, "200", "400");
      builder.doTest("199+1=pp", "800 ", 1, "200", "800");
      builder.doTest("199+1=ppp", "1600 ", 1, "200", "1600");
      builder.doTest("5+p", "5 + 0.25", 2, "5", "0.25");
   }

   @Test
   void specialOperations() {
      builder.doTest("200+4pffsq", "200 + " + SQRT + "(sqr(1/(1/(8)))) ", 2, "200", "8");
      builder.doTest("5+ss", "5 + sqr(sqr(5)) ", 2, "5", "625");
      builder.doTest("5+ss=", "", 0, "630", "0");
      builder.doTest("2+3=s", "sqr(5) ", 1, "5", "25");
   }

   @Test
   void hiddenOperand() {
      builder.doTest("2+=", "", 0, "4", "0");
      builder.doTest("4+n", "4 + negate(4) ", 2, "4", "-4");
      builder.doTest("4+n=", "", 0, "0", null);
   }
}