package com.implemica.model.calculator;

import com.implemica.model.calculator.until.TestBuilder;
import com.implemica.model.exceptions.ExceptionType;
import com.implemica.model.exceptions.InvalidInputException;
import com.implemica.model.exceptions.OverflowException;
import com.implemica.model.exceptions.UndefinedResultException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalculatorTest {

   private TestBuilder builder;

   private final String SQRT = "√";

   @BeforeEach
   void init() {
      builder = new TestBuilder();
   }

   @Test
   void manySimpleOperations() throws OverflowException, UndefinedResultException, InvalidInputException {
      builder.doTest("2+", "2 + ", 2, "2", null);

      builder.doTest("3+++++", "3 + ", 2, "3", null);
      builder.doTest("5-----", "5 - ", 2, "5", null);
      builder.doTest("8*****", "8 * ", 2, "8", null);
      builder.doTest("12/////", "12 / ", 2, "12", null);
   }

   @Test
   void simpleOperationBeforeEquals() throws OverflowException, UndefinedResultException, InvalidInputException {
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
   void manyEquals() throws OverflowException, UndefinedResultException, InvalidInputException {
      builder.doTest("1+3===", "", 0, "10", null);
      builder.doTest("289-102===", "", 0, "-17", null);
      builder.doTest("2*3===", "", 0, "54", null);
      builder.doTest("188/2===", "", 0, "23,5", null);
   }

   @Test
   void equalsTests() throws OverflowException, UndefinedResultException, InvalidInputException {
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

      builder.doTest("5+3=r===5=", "", 0, "9,125", null);
   }

   @Test
   void otherTests() throws OverflowException, UndefinedResultException, InvalidInputException {
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
   void percentTest() throws OverflowException, UndefinedResultException, InvalidInputException {
      builder.doTest("2%", "0 ", 1, "0", "0");
      builder.doTest("200+2%", "200 + 4 ", 2, "200", null);
      builder.doTest("200+2%=", "", 0, "204", null);
      builder.doTest("200+2%=%", "416.16 ", 1, null, "416,16");
      builder.doTest("199+1=", "", 0, "200", "1");
      builder.doTest("199+1=%", "400 ", 1, "200", "400");
      builder.doTest("199+1=%%", "800 ", 1, "200", "800");
      builder.doTest("199+1=%%%", "1600 ", 1, "200", "1 600");
      builder.doTest("5+%", "5 + 0.25 ", 2, "5", "0,25");
      builder.doTest("2qqq", "sqr(sqr(sqr(2))) ", 1, "0", "256");
   }

   @Test
   void backspaceTest() throws OverflowException, UndefinedResultException, InvalidInputException {
      builder.doTest("2qqq<<<<<<<<=", "", 0, "256", null);
      builder.doTest("70/7=<<<", "", 0, "10", null);
      builder.doTest("1234567890<<<<<<<<<<<<<<<<<<<<<=", "", 0, "0", null);
      builder.doTest("<<<<2√q=", "", 0, "2", null);
      builder.doTest("2+3=<<<<", "", 0, "5", null);
   }

   @Test
   void clear() throws OverflowException, UndefinedResultException, InvalidInputException {
      builder.doTest("2+2*2-6*3-10 C", "", 0, "0", "0");
      builder.doTest("2+5/2 C C C C C C C C C", "", 0, "0", "0");
      builder.doTest("5qqqqqq√√qqqqq√qqq C C", "", 0, "0", "0");

      builder.doTest("85 CE", "", 0, "0", "0");
      builder.doTest("8+2 CE", "8 + ", 2, "8", "0");
      builder.doTest("6- CE", "6 - ", 2, "6", "0");
   }

   @Test
   void specialOperations() throws OverflowException, UndefinedResultException, InvalidInputException {
      builder.doTest("200+4%rrq√", "200 + " + SQRT + "(sqr(1/(1/(8)))) ", 2, "200", "8");
      builder.doTest("5+qq", "5 + sqr(sqr(5)) ", 2, "5", "625");
      builder.doTest("5+qq=", "", 0, "630", "625");
      builder.doTest("2+3=q", "sqr(5) ", 1, "5", "25");

      builder.doTest("2√q√q√q", null, 0, null, "2");
      builder.doTest("3rrrrrr", null, 0, null, "3");
      builder.doTest("1/3=r", null, 0, null, "3");
      builder.doTest("1000000000000000-r", "1000000000000000 - 1/(1000000000000000) ", 2, null, "0,000000000000001");
      builder.doTest("1000000000000000*=-r", "1,e+30 - 1/(1,e+30) ", 2, null, "1,e-30");
      builder.doTest("1000000000000000*=====-r", "1,e+90 - 1/(1,e+90) ", 2, null, "1,e-90");
   }

   @Test
   void hiddenOperand() throws OverflowException, UndefinedResultException, InvalidInputException {
      builder.doTest("2+=", "", 0, "4", null);
      builder.doTest("4+n", "4 + negate(4) ", 2, "4", "-4");
      builder.doTest("4+n=", "", 0, "0", null);
   }

   @Test
   void testRoundingMode() throws OverflowException, UndefinedResultException, InvalidInputException {
      builder.doTest("0.0000000000000001+1=", "", 0, "1", null);
      builder.doTest("1/3*3=", "", 0, "1", null);
      builder.doTest("1/3*3-1", "1 / 3 * 3 - 1 ", 4, "1", null);
      builder.doTest("0.0111111111111111*0.1==", "", 0, "1,11111111111111e-4", null);
      builder.doTest("0.0011111111111111*0.1=", "", 0, "1,1111111111111e-4", null);
      builder.doTest("0.0011111111111111*0.1==", "", 0, "1,1111111111111e-5", null);
      builder.doTest("1/3*3-1=", "", 0, "0", null);
      builder.doTest("2.0000000000000001+1========", "", 0, "10", null);
      builder.doTest("0.1*================", "", 0, "1,e-17", null);
//      builder.doTest("0.9*=================================================================", "", 0, "9,550049507968252e-4", null);
      builder.doTest("9999999999999999+2=", "", 0, "1,e+16", null);
      builder.doTest("9999999999999999*2=", "", 0, "2,e+16", null);
      builder.doTest("9999999999999999*6=", "", 0, "5,999999999999999e+16", null);
      builder.doTest("9999999999999999*6==", "", 0, "3,6e+17", null);
      builder.doTest("9999999999999999*6===", "", 0, "2,16e+18", null);
      builder.doTest("9999999999999999*6====", "", 0, "1,296e+19", null);
      builder.doTest("9999999999999999*6=====", "", 0, "7,775999999999999e+19", null);
      builder.doTest("9999999999999999+6=", "", 0, "1,e+16", null);
      builder.doTest("9999999999999999+7=", "", 0, "1,000000000000001e+16", null);
      builder.doTest("9999999999999999r+1=", "", 0, "1", null);
      builder.doTest("9999999999999999r=", "", 0, "1,e-16", null);
      builder.doTest("9999999999999999r+=", "", 0, "2,e-16", null);
      builder.doTest("9999999999999999r+==", "", 0, "3,e-16", null);
      builder.doTest("9999999999999999r+===", "", 0, "4,e-16", null);
      builder.doTest("9999999999999999r+====", "", 0, "5,000000000000001e-16", null);
      builder.doTest("2.000000000000001+1=", "", 0, "3,000000000000001", null);
      builder.doTest("2.000000000000001+2=", "", 0, "4,000000000000001", null);
      builder.doTest("2.000000000000001+3=", "", 0, "5,000000000000001", null);
      builder.doTest("2.000000000000001+4=", "", 0, "6,000000000000001", null);
      builder.doTest("2.000000000000001+8=", "", 0, "10", null);

      builder.doTest("0.0000000000000001+=", "", 0, "0,0000000000000002", null);
      builder.doTest("0.0000000000000001-=", "", 0, "0", null);
      builder.doTest("1/7*7-1=", "", 0, "0", null);
//      builder.doTest("1/7*1000000000000000*7-1000000000000000=", "", 0, "0", null);
//      builder.doTest("1/3/7/11/13/17*1000000000000000*3*7*11*13*17-1000000000000000=", "", 0, "0", null);
      builder.doTest("5√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√", null, 0, "0", "1,000000000000001");

   }

   @Test
   void buildOperandTest() {

   }

   @Test
   void historyTest() throws OverflowException, UndefinedResultException, InvalidInputException {
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
   void memoryTest() throws OverflowException, UndefinedResultException, InvalidInputException {
      // takes from operand
      builder.doTest("4 M+ M+ MR ", "", 0, null, "8");
      builder.doTest("1 M+ M+ M+ M+ * MR =", "", 0, "4", null);
      builder.doTest("1 M- M- * MR =", "", 0, "-2", null);
      builder.doTest("1 M- M+ M- M+ MR =", "", 0, "0", null);
      builder.doTest("M+ M+ M+ 1 + MR =", "", 0, "1", null);
      builder.doTest("8n M+ ", null, 0, null, "-8");

      // takes from result
      builder.doTest("1+2+3+4+5+ M+ MR =", "", 0, "30", null);
      builder.doTest("2+2= M+ M+ M+ M+ MR + 0=", "", 0, "16", null);
      builder.doTest("0 + 20 M- M- M- M- M- =- MR =", "", 0, "120", null);


      builder.doTest("0,0000000000000001qqqqqq M+ √√√√ qqqq M- MR ", null, 0, "0", "0");
      builder.doTest("0,0000000000000001qqqqqq M+ √√√√ qqqq M- MR =", null, 0, "0", "0");

      builder.doTest("1000000000000000*1000000000000000*1000000000000000= M- C 1/7*1000000000000000*1000000000000000*1000000000000000*7= M+ C MR=", null, 0, "0", null);
   }

   @Test
   void checkExceptions() {
      // overflow
      builder.doExceptionsTest("1000000000000000qqqqqqqqqq", ExceptionType.OVERFLOW);
      builder.doExceptionsTest("0.0000000000000001qqqqqqqqqq", ExceptionType.OVERFLOW);

      // undefined
      builder.doExceptionsTest("0/0=", ExceptionType.UNDEFINED_RESULT);
      builder.doExceptionsTest("0/0+", ExceptionType.UNDEFINED_RESULT);

      // divide by zero
      builder.doExceptionsTest("1/0=", ExceptionType.DIVIDE_BY_ZERO);
      builder.doExceptionsTest("5/0=", ExceptionType.DIVIDE_BY_ZERO);
      builder.doExceptionsTest("0.0000000000000001/0=", ExceptionType.DIVIDE_BY_ZERO);
      builder.doExceptionsTest("r", ExceptionType.DIVIDE_BY_ZERO);
      builder.doExceptionsTest("0r", ExceptionType.DIVIDE_BY_ZERO);
      builder.doExceptionsTest("8/0+", ExceptionType.DIVIDE_BY_ZERO);
      builder.doExceptionsTest("8/0=", ExceptionType.DIVIDE_BY_ZERO);

      // invalid input
      builder.doExceptionsTest("5n√", ExceptionType.INVALID_INPUT);
      builder.doExceptionsTest("55n√", ExceptionType.INVALID_INPUT);
      builder.doExceptionsTest("50-45-500√=√", ExceptionType.INVALID_INPUT);
      builder.doExceptionsTest("0.4565468n√", ExceptionType.INVALID_INPUT);
      builder.doExceptionsTest("-0.57894=√", ExceptionType.INVALID_INPUT);



   }

   @Test
   void boundaryTest() throws OverflowException, UndefinedResultException, InvalidInputException {

      // this pattern create number by this short formula (MAX - 1).
      // number must be 99999999...
      // digit 9 must be 9999 times.
      String maxMinusOne = "1000000000000000*===================*================================*1000000000000000======" +
              "*10========= -1*10+9=";

      // this pattern provide to storage in memory this number : 0.9999999999999999999...
      // note! digit 9 must be 9999 times.
      String oneSubtractTheSmallestNumber = "1000000000000000*===================*================================*1000000000000000======" +
              "*10=========-1=r= M- C ";

      // this pattern create number by this short formula (-MAX - MIN).
      // number must be -99999999...
      // digit 9 must be 9999 times.
      String negateMaxSubtractMin = "1000000000000000*===================*================================*1000000000000000======" +
              "*10========= -1*10n-9= ";

      // 0.000000000000000...00001
      String theSmallestNumber = "1000000000000000*===================*================================*1000000000000000======" +
              "*10=========r*0.1= ";

      // 0.000000000000000...00002
      String doubleSmallestNumber = "1000000000000000*===================*================================*1000000000000000======" +
              "*10=========r*0.2= ";

      // right side
      // 999999999999999999999999(9).999999999...99
      builder.doTest(oneSubtractTheSmallestNumber + maxMinusOne + "+ MR =", null, 0, null, null);

      // 1e10000
      builder.doExceptionsTest(oneSubtractTheSmallestNumber + maxMinusOne + "+ MR = MC M+ C " + theSmallestNumber + " + MR =", ExceptionType.OVERFLOW);

      // 1000000000000000000(0).0000000000000000000...01
      builder.doExceptionsTest(oneSubtractTheSmallestNumber + maxMinusOne + "+ MR = MC M+ C " + doubleSmallestNumber + " + MR =", ExceptionType.OVERFLOW);

      // left side
      // -999999999999999999999999(9).999999999...99
      builder.doTest(oneSubtractTheSmallestNumber + negateMaxSubtractMin + " - MR =", null, 0, null, null);

      // -1e10000
      builder.doExceptionsTest(oneSubtractTheSmallestNumber + negateMaxSubtractMin + " - MR = MC M+ C " + theSmallestNumber + " - MR =", ExceptionType.OVERFLOW);

      // -1000000000000(0).000000000...01
      builder.doExceptionsTest(oneSubtractTheSmallestNumber + negateMaxSubtractMin + " - MR = MC M+ C " + doubleSmallestNumber + " - MR =", ExceptionType.OVERFLOW);
   }
}