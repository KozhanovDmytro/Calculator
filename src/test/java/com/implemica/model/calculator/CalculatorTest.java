package com.implemica.model.calculator;

import com.implemica.model.calculator.until.TestBuilder;
import com.implemica.model.exceptions.OverflowException;
import com.implemica.model.exceptions.UndefinedResultException;
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
      builder.doTest("+7=", "", 0, "7", "7");
      builder.doTest("-7", "0 - 7 ", 2, "0", "7");
      builder.doTest("-7=", "", 0, "-7", "7");
      builder.doTest("*4", "0 * 4 ", 2, "0", "4");
      builder.doTest("*4=", "", 0, "0", "4");
      builder.doTest("/8", "0 / 8 ", 2, "0", "8");
      builder.doTest("/8=", "", 0, "0", "8");
   }

   @Test
   void manyEquals() {
      builder.doTest("1+3===", "", 0, "10", null);
      builder.doTest("289-102===", "", 0, "-17", null);
      builder.doTest("2*3===", "", 0, "54", null);
      builder.doTest("188/2===", "", 0, "23,5", null);
   }

   @Test
   void equalsTests() {
      builder.doTest("2+3=+++", "5 + ", 2, "5", null);
      builder.doTest("239*4=+-*/", "956 / ", 2, "956", null);
      builder.doTest("10+=", "", 0, "20", null);
      builder.doTest("10+=+=", "", 0, "40", null);
      builder.doTest("10+=+=+=", "", 0, "80", null);
      builder.doTest("1+2=4=", "", 0, "6", null);

      builder.doTest("5+3=", "", 0, "8", "3");
      builder.doTest("5+3=r", "1/(8) ", 1, "8", "0,125");
      builder.doTest("5+3=r=", "", 0, "3,125", "0,125");
      builder.doTest("5+3=r==", "", 0, "6,125", null);
      builder.doTest("5+3=r===", "", 0, "9,125", null);
   }

   @Test
   void otherTests() {
      builder.doTest("7+3=1+", "1 + ", 2, "1", null);
      builder.doTest("2+3=4===", "", 0, "13", null);
      builder.doTest("4√", SQRT + "(4) ", 1, null, "2");
      builder.doTest("4√=", "", 0, "2", "2");
      builder.doTest("8q", "sqr(8) ", 1, "0", "64");
      builder.doTest("8q=", "", 0, "64", "64");
      builder.doTest("2r", "1/(2) ", 1, "0", "0,5");
      builder.doTest("2r=", "", 0, "0,5", null);
   }

   @Test
   void percentTest() {
      builder.doTest("2%", "0 ", 1, "0", "0");
      builder.doTest("200+2%", "200 + 4 ", 2, "200", null);
      builder.doTest("200+2%=", "", 0, "204", null);
      builder.doTest("200+2%=%", "416.16 ", 1,  null, "416,16");
      builder.doTest("199+1=", "", 0, "200", "1");
      builder.doTest("199+1=%", "400 ", 1, "200", "400");
      builder.doTest("199+1=%%", "800 ", 1, "200", "800");
      builder.doTest("199+1=%%%", "1600 ", 1, "200", "1 600");
      builder.doTest("5+%", "5 + 0.25 ", 2, "5", "0,25");
      builder.doTest("2qqq", "sqr(sqr(sqr(2))) ", 1, "0", "256");
   }

   @Test
   void backspaceTest() {
      builder.doTest("2qqq<<<<<<<<=", "", 0, "256", null);
      builder.doTest("70/7=<<<", "", 0, "10", null);
      builder.doTest("1234567890<<<<<<<<<<<<<<<<<<<<<=", "", 0, "0", null);
      builder.doTest("<<<<2√q=", "", 0, "2", null);
      builder.doTest("2+3=<<<<", "", 0, "5", null);
   }

   @Test
   void clear() {
      builder.doTest("2+2*2-6*3-10c", "", 0, "0", "0");
      builder.doTest("2+5/2ccccccccc", "", 0, "0", "0");
      builder.doTest("5qqqqqq√√qqqqq√qqqcc", "", 0, "0", "0");

      builder.doTest("85e", "", 0, "0", "0");
      builder.doTest("8+2e", "8 + ", 2, "8", "0");
      builder.doTest("6-e", "6 - ", 2, "6", "0");
   }

   @Test
   void specialOperations() {
      builder.doTest("200+4%rrq√", "200 + " + SQRT + "(sqr(1/(1/(8)))) ", 2, "200", "8");
      builder.doTest("5+qq", "5 + sqr(sqr(5)) ", 2, "5", "625");
      builder.doTest("5+qq=", "", 0, "630", "625");
      builder.doTest("2+3=q", "sqr(5) ", 1, "5", "25");

      builder.doTest("2√q√q√q", null, 0, null, "2");
      builder.doTest("3rrrrrr", null, 0, null, "3");
      builder.doTest("1/3=r", null, 0, null, "3");
   }

   @Test
   void hiddenOperand() {
      builder.doTest("2+=", "", 0, "4", null);
      builder.doTest("4+n", "4 + negate(4) ", 2, "4", "-4");
      builder.doTest("4+n=", "", 0, "0", null);
   }

   @Test
   void testRoundingMode() {
      builder.doTest("0.0000000000000001+1=", "", 0, "1", null);
      builder.doTest("1/3*3=", "", 0, "1", null);
      builder.doTest("1/3*3-1", "1 / 3 * 3 - 1 ", 4, "1", null);
      builder.doTest("0.0111111111111111*0.1==", "", 0, "1,11111111111111e-4", null);
      builder.doTest("0.0011111111111111*0.1=", "", 0, "1,1111111111111e-4", null);
      builder.doTest("0.0011111111111111*0.1==", "", 0, "1,1111111111111e-5", null);
      builder.doTest("1/3*3-1=", "", 0, "0", null);
      builder.doTest("2.0000000000000001+1========", "", 0, "10", null);
      builder.doTest("0.1*================", "", 0, "1,e-17", null);
      builder.doTest("0.9*=================================================================", "", 0, "9,550049507968252e-4", null);

      builder.doTest("0.0000000000000001+=", "", 0, "0,0000000000000002", null);
      builder.doTest("0.0000000000000001-=", "", 0, "0", null);
      builder.doTest("1/7*7-1=", "", 0, "0", null);
      builder.doTest("1/7*1000000000000000*7-1000000000000000=", "", 0, "0", null);
      builder.doTest("1/3/7/11/13/17*1000000000000000*3*7*11*13*17-1000000000000000=", "", 0, "0", null);
   }

   @Test
   void buildOperandTest(){

   }

   @Test
   void historyTest() {
      builder.doTest("0.0000000000000001+", "0,0000000000000001 + ", 2, null, null);
      builder.doTest("0.0000000000000001+1", "0,0000000000000001 + 1 ", 2, null, null);
      builder.doTest("0.0000000000000001+1=", "", 0, null, null);
      builder.doTest("0.0000000000000001+1=+", "1 + ", 2, null, null);
      builder.doTest("0.0000000000000001+1=+=+", "2 + ", 2, null, null);
      builder.doTest("0.0000000000000001+1=+=+=+", "4 + ", 2, null, null);
      builder.doTest("0.0000000000000001+1=+=+=+=+", "8,000000000000001 + ", 2, null, null);

      builder.doTest("2+5q", "2 + sqr(5) ", 2, null, null);
      builder.doTest("1.00001√√√", "√(√(√(1,00001))) ", 1, null, null);
   }

   @Test
   void memoryTest() {

   }
}