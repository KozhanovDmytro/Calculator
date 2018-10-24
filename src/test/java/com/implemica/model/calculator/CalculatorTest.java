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

   @BeforeEach
   void init() {
      builder = new TestBuilder();
   }

   @Test
   void simpleOperationTests() throws OverflowException, UndefinedResultException, InvalidInputException {
      builder.doTest("2+", "2 + ", 2, "2", null);
      builder.doTest("3+++++", "3 + ", 2, "3", null);
      builder.doTest("5-----", "5 - ", 2, "5", null);
      builder.doTest("8*****", "8 * ", 2, "8", null);
      builder.doTest("12/////", "12 / ", 2, "12", null);
      builder.doTest("9/*-+-*/*-++-*+", "9 + ", 2, "9", null);
      builder.doTest("52--+/*-+-/*-+-/", "52 / ", 2, "52", null);
      builder.doTest("12+*/*-+-*//*/**/-+-*/", "12 / ", 2, "12", null);
      builder.doTest("2++++=", "", 0, "4", null);
      builder.doTest("4----=", "", 0, "0", null);
      builder.doTest("8****=", "", 0, "64", null);
      builder.doTest("100/////=", "", 0, "1", null);
   }

   @Test
   void plusOperationTest() throws OverflowException, InvalidInputException, UndefinedResultException {
      builder.doTest("2+2=", "", 0, "4", null);
      builder.doTest("7+3=", "", 0, "10", null);
      builder.doTest("1+8=", "", 0, "9", null);
      builder.doTest("5+6=", "", 0, "11", null);
      builder.doTest("3+9=", "", 0, "12", null);

      builder.doTest("0+0=", "", 0, "0", null);
      builder.doTest("1+0=", "", 0, "1", null);
      builder.doTest("8+0=", "", 0, "8", null);
      builder.doTest("0+5=", "", 0, "5", null);
      builder.doTest("0+3=", "", 0, "3", null);

      builder.doTest("2+5", "2 + 5 ", 2, "2", null);
      builder.doTest("5+3", "5 + 3 ", 2, "5", null);
      builder.doTest("2+3", "2 + 3 ", 2, "2", null);
      builder.doTest("2+8", "2 + 8 ", 2, "2", null);
      builder.doTest("1+2", "1 + 2 ", 2, "1", null);

      builder.doTest("0+0", "0 + 0 ", 2, "0", null);
      builder.doTest("1+0", "1 + 0 ", 2, "1", null);
      builder.doTest("5+0", "5 + 0 ", 2, "5", null);
      builder.doTest("0+2", "0 + 2 ", 2, "0", null);
      builder.doTest("0+6", "0 + 6 ", 2, "0", null);

      builder.doTest("0+", "0 + ", 2, "0", null);
      builder.doTest("2+", "2 + ", 2, "2", null);
      builder.doTest("3+", "3 + ", 2, "3", null);
      builder.doTest("9+", "9 + ", 2, "9", null);
      builder.doTest("8+", "8 + ", 2, "8", null);

      builder.doTest("+1", "0 + 1 ", 2, "0", null);
      builder.doTest("+5", "0 + 5 ", 2, "0", null);
      builder.doTest("+9", "0 + 9 ", 2, "0", null);
      builder.doTest("+7", "0 + 7 ", 2, "0", null);
      builder.doTest("+3", "0 + 3 ", 2, "0", null);

      builder.doTest("+", "0 + ", 2, "0", null);

      builder.doTest("1+=", "", 0, "2", null);
      builder.doTest("5+=", "", 0, "10", null);
      builder.doTest("3+=", "", 0, "6", null);
      builder.doTest("2+=", "", 0, "4", null);
      builder.doTest("7+=", "", 0, "14", null);

      builder.doTest("+7=", "", 0, "7", null);
      builder.doTest("+2=", "", 0, "2", null);
      builder.doTest("+5=", "", 0, "5", null);
      builder.doTest("+9=", "", 0, "9", null);
      builder.doTest("+1=", "", 0, "1", null);

      builder.doTest("1+5+", "1 + 5 + ", 3, "6", null);
      builder.doTest("2+9+", "2 + 9 + ", 3, "11", null);
      builder.doTest("3+8+", "3 + 8 + ", 3, "11", null);
      builder.doTest("4+7+", "4 + 7 + ", 3, "11", null);
      builder.doTest("5+6+", "5 + 6 + ", 3, "11", null);

      builder.doTest("2+3+=", "", 0, "10", null);
      builder.doTest("9+2+=", "", 0, "22", null);
      builder.doTest("5+7+=", "", 0, "24", null);
      builder.doTest("6+4+=", "", 0, "20", null);
      builder.doTest("7+6+=", "", 0, "26", null);

      builder.doTest("1+1+2=", "", 0, "4", null);
      builder.doTest("6+2+2=", "", 0, "10", null);
      builder.doTest("1+7+3=", "", 0, "11", null);
      builder.doTest("0+2+8=", "", 0, "10", null);
      builder.doTest("5+9+3=", "", 0, "17", null);

      builder.doTest("2+4=+5", "6 + 5 ", 2, "6", null);
      builder.doTest("3+3=+7", "6 + 7 ", 2, "6", null);
      builder.doTest("5+9=+6", "14 + 6 ", 2, "14", null);
      builder.doTest("8+5=+4", "13 + 4 ", 2, "13", null);
      builder.doTest("7+1=+3", "8 + 3 ", 2, "8", null);

      builder.doTest("7+5=+", "12 + ", 2, "12", null);
      builder.doTest("4+3=+", "7 + ", 2, "7", null);
      builder.doTest("2+2=+", "4 + ", 2, "4", null);
      builder.doTest("8+8=+", "16 + ", 2, "16", null);
      builder.doTest("6+1=+", "7 + ", 2, "7", null);

      builder.doTest("3+1=+=", "", 0, "8", null);
      builder.doTest("5+5=+=", "", 0, "20", null);
      builder.doTest("8+3=+=", "", 0, "22", null);
      builder.doTest("4+8=+=", "", 0, "24", null);
      builder.doTest("1+4=+=", "", 0, "10", null);

      builder.doTest("5n+3=", "", 0, "-2", null);
      builder.doTest("1n+4=", "", 0, "3", null);
      builder.doTest("6n+2=", "", 0, "-4", null);
      builder.doTest("8n+7=", "", 0, "-1", null);
      builder.doTest("7n+6=", "", 0, "-1", null);

      builder.doTest("4+4n=", "", 0, "0", null);
      builder.doTest("2+7n=", "", 0, "-5", null);
      builder.doTest("8+3n=", "", 0, "5", null);
      builder.doTest("2+6n=", "", 0, "-4", null);
      builder.doTest("4+7n=", "", 0, "-3", null);

      builder.doTest("0.1+1=", "", 0, "1,1", null);
      builder.doTest("2.5+5=", "", 0, "7,5", null);
      builder.doTest("8.2+9=", "", 0, "17,2", null);
      builder.doTest("4.3+4=", "", 0, "8,3", null);
      builder.doTest("7.5+6=", "", 0, "13,5", null);

      builder.doTest("7+1.2=", "", 0, "8,2", null);
      builder.doTest("2+3.5=", "", 0, "5,5", null);
      builder.doTest("4+5.6=", "", 0, "9,6", null);
      builder.doTest("5+7.8=", "", 0, "12,8", null);
      builder.doTest("3+9.1=", "", 0, "12,1", null);
   }

   @Test
   void subtractOperationTest() throws OverflowException, InvalidInputException, UndefinedResultException {
      builder.doTest("2-2=", "", 0, "0", null);
      builder.doTest("7-3=", "", 0, "4", null);
      builder.doTest("1-8=", "", 0, "-7", null);
      builder.doTest("5-6=", "", 0, "-1", null);
      builder.doTest("3-9=", "", 0, "-6", null);

      builder.doTest("0-0=", "", 0, "0", null);
      builder.doTest("1-0=", "", 0, "1", null);
      builder.doTest("8-0=", "", 0, "8", null);
      builder.doTest("0-5=", "", 0, "-5", null);
      builder.doTest("0-3=", "", 0, "-3", null);

      builder.doTest("2-5", "2 - 5 ", 2, "2", null);
      builder.doTest("5-3", "5 - 3 ", 2, "5", null);
      builder.doTest("2-3", "2 - 3 ", 2, "2", null);
      builder.doTest("2-8", "2 - 8 ", 2, "2", null);
      builder.doTest("1-2", "1 - 2 ", 2, "1", null);

      builder.doTest("0-0", "0 - 0 ", 2, "0", null);
      builder.doTest("1-0", "1 - 0 ", 2, "1", null);
      builder.doTest("5-0", "5 - 0 ", 2, "5", null);
      builder.doTest("0-2", "0 - 2 ", 2, "0", null);
      builder.doTest("0-6", "0 - 6 ", 2, "0", null);

      builder.doTest("0-", "0 - ", 2, "0", null);
      builder.doTest("2-", "2 - ", 2, "2", null);
      builder.doTest("3-", "3 - ", 2, "3", null);
      builder.doTest("9-", "9 - ", 2, "9", null);
      builder.doTest("8-", "8 - ", 2, "8", null);

      builder.doTest("-1", "0 - 1 ", 2, "0", null);
      builder.doTest("-5", "0 - 5 ", 2, "0", null);
      builder.doTest("-9", "0 - 9 ", 2, "0", null);
      builder.doTest("-7", "0 - 7 ", 2, "0", null);
      builder.doTest("-3", "0 - 3 ", 2, "0", null);

      builder.doTest("-", "0 - ", 2, "0", null);

      builder.doTest("1-=", "", 0, "0", null);
      builder.doTest("5-=", "", 0, "0", null);
      builder.doTest("3-=", "", 0, "0", null);
      builder.doTest("2-=", "", 0, "0", null);
      builder.doTest("7-=", "", 0, "0", null);

      builder.doTest("-7=", "", 0, "-7", null);
      builder.doTest("-2=", "", 0, "-2", null);
      builder.doTest("-5=", "", 0, "-5", null);
      builder.doTest("-9=", "", 0, "-9", null);
      builder.doTest("-1=", "", 0, "-1", null);

      builder.doTest("1-5-", "1 - 5 - ", 3, "-4", null);
      builder.doTest("2-9-", "2 - 9 - ", 3, "-7", null);
      builder.doTest("3-8-", "3 - 8 - ", 3, "-5", null);
      builder.doTest("4-7-", "4 - 7 - ", 3, "-3", null);
      builder.doTest("5-6-", "5 - 6 - ", 3, "-1", null);

      builder.doTest("2-3-=", "", 0, "0", null);
      builder.doTest("9-2-=", "", 0, "0", null);
      builder.doTest("5-7-=", "", 0, "0", null);
      builder.doTest("6-4-=", "", 0, "0", null);
      builder.doTest("7-6-=", "", 0, "0", null);

      builder.doTest("1-1-2=", "", 0, "-2", null);
      builder.doTest("6-2-2=", "", 0, "2", null);
      builder.doTest("1-7-3=", "", 0, "-9", null);
      builder.doTest("0-2-8=", "", 0, "-10", null);
      builder.doTest("5-9-3=", "", 0, "-7", null);

      builder.doTest("2-4=-5", "-2 - 5 ", 2, "-2", null);
      builder.doTest("3-3=-7", "0 - 7 ", 2, "0", null);
      builder.doTest("5-9=-6", "-4 - 6 ", 2, "-4", null);
      builder.doTest("8-5=-4", "3 - 4 ", 2, "3", null);
      builder.doTest("7-1=-3", "6 - 3 ", 2, "6", null);

      builder.doTest("7-5=-", "2 - ", 2, "2", null);
      builder.doTest("4-3=-", "1 - ", 2, "1", null);
      builder.doTest("2-2=-", "0 - ", 2, "0", null);
      builder.doTest("8-8=-", "0 - ", 2, "0", null);
      builder.doTest("6-1=-", "5 - ", 2, "5", null);

      builder.doTest("3-1=-=", "", 0, "0", null);
      builder.doTest("5-5=-=", "", 0, "0", null);
      builder.doTest("8-3=-=", "", 0, "0", null);
      builder.doTest("4-8=-=", "", 0, "0", null);
      builder.doTest("1-4=-=", "", 0, "0", null);

      builder.doTest("5n-3=", "", 0, "-8", null);
      builder.doTest("1n-4=", "", 0, "-5", null);
      builder.doTest("6n-2=", "", 0, "-8", null);
      builder.doTest("8n-7=", "", 0, "-15", null);
      builder.doTest("7n-6=", "", 0, "-13", null);

      builder.doTest("4-4n=", "", 0, "8", null);
      builder.doTest("2-7n=", "", 0, "9", null);
      builder.doTest("8-3n=", "", 0, "11", null);
      builder.doTest("2-6n=", "", 0, "8", null);
      builder.doTest("4-7n=", "", 0, "11", null);

      builder.doTest("0.1-1=", "", 0, "-0,9", null);
      builder.doTest("2.5-5=", "", 0, "-2,5", null);
      builder.doTest("8.2-9=", "", 0, "-0,8", null);
      builder.doTest("4.3-4=", "", 0, "0,3", null);
      builder.doTest("7.5-6=", "", 0, "1,5", null);

      builder.doTest("7-1.2=", "", 0, "5,8", null);
      builder.doTest("2-3.5=", "", 0, "-1,5", null);
      builder.doTest("4-5.6=", "", 0, "-1,6", null);
      builder.doTest("5-7.8=", "", 0, "-2,8", null);
      builder.doTest("3-9.1=", "", 0, "-6,1", null);
   }

   @Test
   void multiplyOperationTest() throws OverflowException, InvalidInputException, UndefinedResultException {
      builder.doTest("2*2=", "", 0, "4", null);
      builder.doTest("7*3=", "", 0, "21", null);
      builder.doTest("1*8=", "", 0, "8", null);
      builder.doTest("5*6=", "", 0, "30", null);
      builder.doTest("3*9=", "", 0, "27", null);

      builder.doTest("0*0=", "", 0, "0", null);
      builder.doTest("1*0=", "", 0, "0", null);
      builder.doTest("8*0=", "", 0, "0", null);
      builder.doTest("0*5=", "", 0, "0", null);
      builder.doTest("0*3=", "", 0, "0", null);

      builder.doTest("2*5", "2 * 5 ", 2, "2", null);
      builder.doTest("5*3", "5 * 3 ", 2, "5", null);
      builder.doTest("2*3", "2 * 3 ", 2, "2", null);
      builder.doTest("2*8", "2 * 8 ", 2, "2", null);
      builder.doTest("1*2", "1 * 2 ", 2, "1", null);

      builder.doTest("0*0", "0 * 0 ", 2, "0", null);
      builder.doTest("1*0", "1 * 0 ", 2, "1", null);
      builder.doTest("5*0", "5 * 0 ", 2, "5", null);
      builder.doTest("0*2", "0 * 2 ", 2, "0", null);
      builder.doTest("0*6", "0 * 6 ", 2, "0", null);

      builder.doTest("0*", "0 * ", 2, "0", null);
      builder.doTest("2*", "2 * ", 2, "2", null);
      builder.doTest("3*", "3 * ", 2, "3", null);
      builder.doTest("9*", "9 * ", 2, "9", null);
      builder.doTest("8*", "8 * ", 2, "8", null);

      builder.doTest("*1", "0 * 1 ", 2, "0", null);
      builder.doTest("*5", "0 * 5 ", 2, "0", null);
      builder.doTest("*9", "0 * 9 ", 2, "0", null);
      builder.doTest("*7", "0 * 7 ", 2, "0", null);
      builder.doTest("*3", "0 * 3 ", 2, "0", null);

      builder.doTest("*", "0 * ", 2, "0", null);

      builder.doTest("1*=", "", 0, "1", null);
      builder.doTest("5*=", "", 0, "25", null);
      builder.doTest("3*=", "", 0, "9", null);
      builder.doTest("2*=", "", 0, "4", null);
      builder.doTest("7*=", "", 0, "49", null);

      builder.doTest("*7=", "", 0, "0", null);
      builder.doTest("*2=", "", 0, "0", null);
      builder.doTest("*5=", "", 0, "0", null);
      builder.doTest("*9=", "", 0, "0", null);
      builder.doTest("*1=", "", 0, "0", null);

      builder.doTest("1*5*", "1 * 5 * ", 3, "5", null);
      builder.doTest("2*9*", "2 * 9 * ", 3, "18", null);
      builder.doTest("3*8*", "3 * 8 * ", 3, "24", null);
      builder.doTest("4*7*", "4 * 7 * ", 3, "28", null);
      builder.doTest("5*6*", "5 * 6 * ", 3, "30", null);

      builder.doTest("2*3*=", "", 0, "36", null);
      builder.doTest("9*2*=", "", 0, "324", null);
      builder.doTest("5*7*=", "", 0, "1 225", null);
      builder.doTest("6*4*=", "", 0, "576", null);
      builder.doTest("7*6*=", "", 0, "1 764", null);

      builder.doTest("1*1*2=", "", 0, "2", null);
      builder.doTest("6*2*2=", "", 0, "24", null);
      builder.doTest("1*7*3=", "", 0, "21", null);
      builder.doTest("0*2*8=", "", 0, "0", null);
      builder.doTest("5*9*3=", "", 0, "135", null);

      builder.doTest("2*4=*5", "8 * 5 ", 2, "8", null);
      builder.doTest("3*3=*7", "9 * 7 ", 2, "9", null);
      builder.doTest("5*9=*6", "45 * 6 ", 2, "45", null);
      builder.doTest("8*5=*4", "40 * 4 ", 2, "40", null);
      builder.doTest("7*1=*3", "7 * 3 ", 2, "7", null);

      builder.doTest("7*5=*", "35 * ", 2, "35", null);
      builder.doTest("4*3=*", "12 * ", 2, "12", null);
      builder.doTest("2*2=*", "4 * ", 2, "4", null);
      builder.doTest("8*8=*", "64 * ", 2, "64", null);
      builder.doTest("6*1=*", "6 * ", 2, "6", null);

      builder.doTest("3*1=*=", "", 0, "9", null);
      builder.doTest("5*5=*=", "", 0, "625", null);
      builder.doTest("8*3=*=", "", 0, "576", null);
      builder.doTest("4*8=*=", "", 0, "1 024", null);
      builder.doTest("1*4=*=", "", 0, "16", null);

      builder.doTest("5n*3=", "", 0, "-15", null);
      builder.doTest("1n*4=", "", 0, "-4", null);
      builder.doTest("6n*2=", "", 0, "-12", null);
      builder.doTest("8n*7=", "", 0, "-56", null);
      builder.doTest("7n*6=", "", 0, "-42", null);

      builder.doTest("4*4n=", "", 0, "-16", null);
      builder.doTest("2*7n=", "", 0, "-14", null);
      builder.doTest("8*3n=", "", 0, "-24", null);
      builder.doTest("2*6n=", "", 0, "-12", null);
      builder.doTest("4*7n=", "", 0, "-28", null);

      builder.doTest("0.1*1=", "", 0, "0,1", null);
      builder.doTest("2.5*5=", "", 0, "12,5", null);
      builder.doTest("8.2*9=", "", 0, "73,8", null);
      builder.doTest("4.3*4=", "", 0, "17,2", null);
      builder.doTest("7.5*6=", "", 0, "45", null);

      builder.doTest("7*1.2=", "", 0, "8,4", null);
      builder.doTest("2*3.5=", "", 0, "7", null);
      builder.doTest("4*5.6=", "", 0, "22,4", null);
      builder.doTest("5*7.8=", "", 0, "39", null);
      builder.doTest("3*9.1=", "", 0, "27,3", null);
   }

   @Test
   void divideOperationTest() throws OverflowException, InvalidInputException, UndefinedResultException {
      builder.doTest("2/2=", "", 0, "1", null);
      builder.doTest("7/3=", "", 0, "2,333333333333333", null);
      builder.doTest("1/8=", "", 0, "0,125", null);
      builder.doTest("5/6=", "", 0, "0,8333333333333333", null);
      builder.doTest("3/9=", "", 0, "0,3333333333333333", null);

      builder.doTest("0/5=", "", 0, "0", null);
      builder.doTest("0/3=", "", 0, "0", null);

      builder.doTest("2/5", "2 / 5 ", 2, "2", null);
      builder.doTest("5/3", "5 / 3 ", 2, "5", null);
      builder.doTest("2/3", "2 / 3 ", 2, "2", null);
      builder.doTest("2/8", "2 / 8 ", 2, "2", null);
      builder.doTest("1/2", "1 / 2 ", 2, "1", null);

      builder.doTest("0/0", "0 / 0 ", 2, "0", null);
      builder.doTest("1/0", "1 / 0 ", 2, "1", null);
      builder.doTest("5/0", "5 / 0 ", 2, "5", null);
      builder.doTest("0/2", "0 / 2 ", 2, "0", null);
      builder.doTest("0/6", "0 / 6 ", 2, "0", null);

      builder.doTest("0/", "0 / ", 2, "0", null);
      builder.doTest("2/", "2 / ", 2, "2", null);
      builder.doTest("3/", "3 / ", 2, "3", null);
      builder.doTest("9/", "9 / ", 2, "9", null);
      builder.doTest("8/", "8 / ", 2, "8", null);

      builder.doTest("/1", "0 / 1 ", 2, "0", null);
      builder.doTest("/5", "0 / 5 ", 2, "0", null);
      builder.doTest("/9", "0 / 9 ", 2, "0", null);
      builder.doTest("/7", "0 / 7 ", 2, "0", null);
      builder.doTest("/3", "0 / 3 ", 2, "0", null);

      builder.doTest("/", "0 / ", 2, "0", null);

      builder.doTest("1/=", "", 0, "1", null);
      builder.doTest("5/=", "", 0, "1", null);
      builder.doTest("3/=", "", 0, "1", null);
      builder.doTest("2/=", "", 0, "1", null);
      builder.doTest("7/=", "", 0, "1", null);

      builder.doTest("/7=", "", 0, "0", null);
      builder.doTest("/2=", "", 0, "0", null);
      builder.doTest("/5=", "", 0, "0", null);
      builder.doTest("/9=", "", 0, "0", null);
      builder.doTest("/1=", "", 0, "0", null);

      builder.doTest("1/5/", "1 / 5 / ", 3, "0,2", null);
      builder.doTest("2/9/", "2 / 9 / ", 3, "0,2222222222222222", null);
      builder.doTest("3/8/", "3 / 8 / ", 3, "0,375", null);
      builder.doTest("4/7/", "4 / 7 / ", 3, "0,5714285714285714", null);
      builder.doTest("5/6/", "5 / 6 / ", 3, "0,8333333333333333", null);

      builder.doTest("2/3/=", "", 0, "1", null);
      builder.doTest("9/2/=", "", 0, "1", null);
      builder.doTest("5/7/=", "", 0, "1", null);
      builder.doTest("6/4/=", "", 0, "1", null);
      builder.doTest("7/6/=", "", 0, "1", null);

      builder.doTest("1/1/2=", "", 0, "0,5", null);
      builder.doTest("6/2/2=", "", 0, "1,5", null);
      builder.doTest("1/7/3=", "", 0, "0,0476190476190476", null);
      builder.doTest("0/2/8=", "", 0, "0", null);
      builder.doTest("5/9/3=", "", 0, "0,1851851851851852", null);

      builder.doTest("2/4=/5", "0,5 / 5 ", 2, "0,5", null);
      builder.doTest("3/3=/7", "1 / 7 ", 2, "1", null);
      builder.doTest("5/9=/6", "0,5555555555555556 / 6 ", 2, "0,5555555555555556", null);
      builder.doTest("8/5=/4", "1,6 / 4 ", 2, "1,6", null);
      builder.doTest("7/1=/3", "7 / 3 ", 2, "7", null);

      builder.doTest("7/5=/", "1,4 / ", 2, "1,4", null);
      builder.doTest("4/3=/", "1,333333333333333 / ", 2, "1,333333333333333", null);
      builder.doTest("2/2=/", "1 / ", 2, "1", null);
      builder.doTest("8/8=/", "1 / ", 2, "1", null);
      builder.doTest("6/1=/", "6 / ", 2, "6", null);

      builder.doTest("3/1=/=", "", 0, "1", null);
      builder.doTest("5/5=/=", "", 0, "1", null);
      builder.doTest("8/3=/=", "", 0, "1", null);
      builder.doTest("4/8=/=", "", 0, "1", null);
      builder.doTest("1/4=/=", "", 0, "1", null);

      builder.doTest("5n/3=", "", 0, "-1,666666666666667", null);
      builder.doTest("1n/4=", "", 0, "-0,25", null);
      builder.doTest("6n/2=", "", 0, "-3", null);
      builder.doTest("8n/7=", "", 0, "-1,142857142857143", null);
      builder.doTest("7n/6=", "", 0, "-1,166666666666667", null);

      builder.doTest("4/4n=", "", 0, "-1", null);
      builder.doTest("2/7n=", "", 0, "-0,2857142857142857", null);
      builder.doTest("8/3n=", "", 0, "-2,666666666666667", null);
      builder.doTest("2/6n=", "", 0, "-0,3333333333333333", null);
      builder.doTest("4/7n=", "", 0, "-0,5714285714285714", null);

      builder.doTest("0.1/1=", "", 0, "0,1", null);
      builder.doTest("2.5/5=", "", 0, "0,5", null);
      builder.doTest("8.2/9=", "", 0, "0,9111111111111111", null);
      builder.doTest("4.3/4=", "", 0, "1,075", null);
      builder.doTest("7.5/6=", "", 0, "1,25", null);

      builder.doTest("7/1.2=", "", 0, "5,833333333333333", null);
      builder.doTest("2/3.5=", "", 0, "0,5714285714285714", null);
      builder.doTest("4/5.6=", "", 0, "0,7142857142857143", null);
      builder.doTest("5/7.8=", "", 0, "0,641025641025641", null);
      builder.doTest("3/9.1=", "", 0, "0,3296703296703297", null);
   }

   @Test
   void equalsTests() throws OverflowException, UndefinedResultException, InvalidInputException {
      builder.doTest("=", "", 0, "0", null);
      builder.doTest("+=", "", 0, "0", null);
      builder.doTest("3=", "", 0, "3", null);
      builder.doTest("√=", "", 0, "0", null);
      builder.doTest("%=", "", 0, "0", null);
      builder.doTest("n=", "", 0, "0", null);
      builder.doTest("2+3=+++", "5 + ", 2, "5", null);
      builder.doTest("239*4=+-*/", "956 / ", 2, "956", null);
      builder.doTest("10+=", "", 0, "20", null);
      builder.doTest("10+=+=", "", 0, "40", null);
      builder.doTest("10+=+=+=", "", 0, "80", null);
      builder.doTest("7+3=1+", "1 + ", 2, "1", null);
      builder.doTest("2+3=4===", "", 0, "13", null);
      builder.doTest("1+2=4=", "", 0, "6", null);
      builder.doTest("1+3===", "", 0, "10", null);
      builder.doTest("289-102===", "", 0, "-17", null);
      builder.doTest("2*3===", "", 0, "54", null);
      builder.doTest("188/2===", "", 0, "23,5", null);
      builder.doTest("5+3=", "", 0, "8", "3");

      builder.doTest("4√=", "", 0, "2", "2");
      builder.doTest("8q=", "", 0, "64", "64");
      builder.doTest("2r=", "", 0, "0,5", null);
      builder.doTest("5+3=r", "1/(8) ", 1, "8", "0,125");
      builder.doTest("5+3=r=", "", 0, "3,125", "0,125");
      builder.doTest("5+3=r==", "", 0, "6,125", null);
      builder.doTest("5+3=r===", "", 0, "9,125", null);
      builder.doTest("5+3=r===5=", "", 0, "9,125", null);
      builder.doTest("3=6+", "6 + ", 2, "6", null);
      builder.doTest("2=+", "2 + ", 2, "2", null);
   }

   @Test
   void specialOperationsTests() throws OverflowException, UndefinedResultException, InvalidInputException {
      builder.doTest("4√", "√(4) ", 1, null, "2");
      builder.doTest("4+5=√", "√(9) ", 1, "9", null);
      builder.doTest("4+5=√=", "", 0, "8", "3");
      builder.doTest("8q", "sqr(8) ", 1, "0", "64");
      builder.doTest("2r", "1/(2) ", 1, "0", "0,5");
      builder.doTest("200+4%rrq√", "200 + √(sqr(1/(1/(8)))) ", 2, "200", "8");
      builder.doTest("5+qq", "5 + sqr(sqr(5)) ", 2, "5", "625");
      builder.doTest("5+qq=", "", 0, "630", "625");
      builder.doTest("2+3=q", "sqr(5) ", 1, "5", "25");

      builder.doTest("2√q√q√q", null, 0, null, "2");
      builder.doTest("3rrrrrr", null, 0, null, "3");
      builder.doTest("1/3=r", null, 0, null, "3");
      builder.doTest("1000000000000000-r", "1000000000000000 - 1/(1000000000000000) ", 2, null, "0,000000000000001");
      builder.doTest("1000000000000000*=-r", "1,e+30 - 1/(1,e+30) ", 2, null, "1,e-30");
      builder.doTest("1000000000000000*=====-r", "1,e+90 - 1/(1,e+90) ", 2, null, "1,e-90");
      builder.doTest("5+2√1", "5 + 1 ", 2, "5", "1");
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

      builder.checkBuildOperand("<", "0");
      builder.checkBuildOperand(".", "0,");
      builder.checkBuildOperand("...", "0,");
      builder.checkBuildOperand(".<", "0");
      builder.checkBuildOperand("...<<<", "0");
      builder.checkBuildOperand("...<", "0");
      builder.checkBuildOperand("<.", "0,");
      builder.checkBuildOperand("<...", "0,");

      builder.checkBuildOperand("<0", "0");
      builder.checkBuildOperand("0<", "0");
      builder.checkBuildOperand("000000<<<<<", "0");
      builder.checkBuildOperand("0.0001<", "0,000");
      builder.checkBuildOperand("0.0<", "0,");
      builder.checkBuildOperand("1.0<", "1,");
      builder.checkBuildOperand("1.01<", "1,0");
      builder.checkBuildOperand("12.0<", "12,");
      builder.checkBuildOperand("12.0<<", "12");
      builder.checkBuildOperand("12.0<<<", "1");
      builder.checkBuildOperand("12.0<<<<", "0");
      builder.checkBuildOperand("<<<1234.12345", "1 234,12345");
      builder.checkBuildOperand("1234.12345<<<", "1 234,12");
      builder.checkBuildOperand("1234.12345<<<<", "1 234,1");
      builder.checkBuildOperand("1234.12345<<<<<", "1 234,");
      builder.checkBuildOperand("1234.12345<<<<<<", "1 234");
      builder.checkBuildOperand("1234.12345<<<<<<<", "123");

      builder.checkBuildOperand("1000000000000000.", "1 000 000 000 000 000,");
      builder.checkBuildOperand("1000000000000000.<", "1 000 000 000 000 000");
      builder.checkBuildOperand("1000000000000000.<.00", "1 000 000 000 000 000,");
      builder.checkBuildOperand("1000000000000000.<.10", "1 000 000 000 000 000,");
      builder.checkBuildOperand("1000000000000000.<0", "1 000 000 000 000 000");

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
   void hiddenOperand() throws OverflowException, UndefinedResultException, InvalidInputException {
      builder.doTest("4+n", "4 + negate(4) ", 2, "4", "-4");
      builder.doTest("4+n=", "", 0, "0", null);
      builder.doTest("/*-+", "0 + ", 2, "0", null);
      builder.doTest("-9√=", "", 0, "-3", null);
      builder.doTest("8+5-√=", "", 0, "9,394448724536011", null);
   }

   @Test
   void testRoundingMode() throws OverflowException, UndefinedResultException, InvalidInputException {
      builder.doTest("0.0000000000000001+1=", "", 0, "1", null);
      builder.doTest("1/3*3=", "", 0, "1", null);
      builder.doTest("1/3*3-1", "1 / 3 * 3 - 1 ", 4, "1", null);
      builder.doTest("0.0111111111111111*0.1=", "", 0, "0,0011111111111111", null);
      builder.doTest("0.0111111111111111*0.1==", "", 0, "1,11111111111111e-4", null);
      builder.doTest("0.0111111111111111*0.1===", "", 0, "1,11111111111111e-5", null);
      builder.doTest("1/3*3-1=", "", 0, "0", null);
      builder.doTest("2.0000000000000001+1========", "", 0, "10", null);
      builder.doTest("0.1*================", "", 0, "1,e-17", null);
      builder.doTest("0.9*=================================================================", "", 0, "9,550049507968252e-4", null);
      builder.doTest("9999999999999999+2=", "", 0, "1,e+16", null);
      builder.doTest("9999999999999999*2=", "", 0, "2,e+16", null);
      builder.doTest("9999999999999999*8=", "", 0, "7,999999999999999e+16", null);
      builder.doTest("9999999999999999*8==", "", 0, "6,399999999999999e+17", null);
      builder.doTest("9999999999999999*8===", "", 0, "5,119999999999999e+18", null);
      builder.doTest("9999999999999999*8====", "", 0, "4,096e+19", null);
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
      builder.doTest("1/7*1000000000000000*7-1000000000000000=", "", 0, "0", null);
      builder.doTest("1/3/7/11/13/17*1000000000000000*3*7*11*13*17-1000000000000000=", "", 0, "0", null);
      builder.doTest("5√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√", null, 0, "0", "1,000000000000002");
      builder.doTest("1/3*0.0000000000000001*0.00000000001*1000000000000000*10000000000000*3=", "", 0, "10", null);
      builder.doTest("0.9999999999999999*999999999999999.1=", "", 0, "999 999 999 999 999", null);
      builder.doTest("0.9999999999999999*999999999999999.3=", "", 0, "999 999 999 999 999,2", null);
      builder.doTest("0.9999999999999999*999999999999999.5=", "", 0, "999 999 999 999 999,4", null);
      builder.doTest("0.9999999999999999*999999999999999.7=", "", 0, "999 999 999 999 999,6", null);
      builder.doTest("0.9999999999999999*99999999999999.11=", "", 0, "99 999 999 999 999,1", null);

      builder.doTest("10/3==*1000000000000000========", "", 0, "1,111111111111111e+120", null);
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