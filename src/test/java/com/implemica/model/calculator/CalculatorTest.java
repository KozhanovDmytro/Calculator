package com.implemica.model.calculator;

import com.implemica.model.calculator.until.TestBuilder;
import com.implemica.model.exceptions.ExceptionType;
import com.implemica.model.exceptions.InvalidInputException;
import com.implemica.model.exceptions.OverflowException;
import com.implemica.model.exceptions.UndefinedResultException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalculatorTest {

   private TestBuilder builder;

   // this pattern reach to number: 1e-9999 and write it as a result.
   private String smallNumber = "1000000000000000×===================×================================×1000000000000000======" +
           "×10========= 1/x ×0.1= ";

   // this pattern reach to number: 2e-9999 and write it as a result.
   private String doubleSmallNumber = "1000000000000000×===================×================================×1000000000000000======" +
           "×10========= 1/x ×0.2= ";

   // this pattern create number by this short formula (MAX - 1).
   // number must be 99999999...
   // digit 9 must be 10 000 times.
   private String maxMinusOne = "1000000000000000×===================×================================×1000000000000000======" +
           "×10========= -1×10+9=";

   // this pattern provide to storage in memory this number : 0.9999999999999999999..94
   // note! digit 9 must be 19999 times and at the end of this number must be digit 4
   private String oneSubtractTheSmallestNumber = smallNumber + " M+ C " + "1000000000000000×===================×================================×1000000000000000======" +
           "×10========= -1= 1/x - MR ×0.1=-0.9=×0.6=-0.4=n MC M+ C ";

   // this pattern create number by this short formula (-MAX - MIN).
   // number must be -99999999...
   // digit 9 must be 10 000 times.
   private String negateMaxSubtractMin = "1000000000000000×===================×================================×1000000000000000======" +
           "×10========= -1×10n-9= ";

   private String theMaxNumber = oneSubtractTheSmallestNumber + maxMinusOne + " + MR =";

   private String theMinNumber = oneSubtractTheSmallestNumber + negateMaxSubtractMin + " - MR =";

   @BeforeEach
   void init() {
      builder = new TestBuilder();
   }

   @Test
   void simpleOperationTests() throws OverflowException, UndefinedResultException, InvalidInputException {
      builder.doTest("+", "0 + ", 2, "0", null);
      builder.doTest("-", "0 - ", 2, "0", null);
      builder.doTest("×", "0 × ", 2, "0", null);
      builder.doTest("÷", "0 ÷ ", 2, "0", null);

      builder.doTest("÷×-+", "0 + ", 2, "0", null);

      builder.doTest("2+", "2 + ", 2, "2", null);
      builder.doTest("1-", "1 - ", 2, "1", null);
      builder.doTest("3×", "3 × ", 2, "3", null);
      builder.doTest("4÷", "4 ÷ ", 2, "4", null);

      builder.doTest("+0", "0 + 0 ", 2, "0", null);
      builder.doTest("-2", "0 - 2 ", 2, "0", null);
      builder.doTest("×3", "0 × 3 ", 2, "0", null);
      builder.doTest("÷7", "0 ÷ 7 ", 2, "0", null);

      builder.doTest("3+++++", "3 + ", 2, "3", null);
      builder.doTest("5-----", "5 - ", 2, "5", null);
      builder.doTest("8×××××", "8 × ", 2, "8", null);
      builder.doTest("12÷÷÷÷÷", "12 ÷ ", 2, "12", null);

      builder.doTest("9÷×-+-×÷×-++-×+", "9 + ", 2, "9", null);
      builder.doTest("12+-+-××÷÷-+-", "12 - ", 2, "12", null);
      builder.doTest("52--+÷×-+-÷×-+-×", "52 × ", 2, "52", null);
      builder.doTest("12+×÷×-+-×÷÷×÷××÷-+-×÷", "12 ÷ ", 2, "12", null);

      builder.doTest("2++++=", "", 0, "4", null);
      builder.doTest("4----=", "", 0, "0", null);
      builder.doTest("8××××=", "", 0, "64", null);
      builder.doTest("100÷÷÷÷÷=", "", 0, "1", null);
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
      builder.doTest("2×2=", "", 0, "4", null);
      builder.doTest("7×3=", "", 0, "21", null);
      builder.doTest("1×8=", "", 0, "8", null);
      builder.doTest("5×6=", "", 0, "30", null);
      builder.doTest("3×9=", "", 0, "27", null);

      builder.doTest("0×0=", "", 0, "0", null);
      builder.doTest("1×0=", "", 0, "0", null);
      builder.doTest("8×0=", "", 0, "0", null);
      builder.doTest("0×5=", "", 0, "0", null);
      builder.doTest("0×3=", "", 0, "0", null);

      builder.doTest("2×5", "2 × 5 ", 2, "2", null);
      builder.doTest("5×3", "5 × 3 ", 2, "5", null);
      builder.doTest("2×3", "2 × 3 ", 2, "2", null);
      builder.doTest("2×8", "2 × 8 ", 2, "2", null);
      builder.doTest("1×2", "1 × 2 ", 2, "1", null);

      builder.doTest("0×0", "0 × 0 ", 2, "0", null);
      builder.doTest("1×0", "1 × 0 ", 2, "1", null);
      builder.doTest("5×0", "5 × 0 ", 2, "5", null);
      builder.doTest("0×2", "0 × 2 ", 2, "0", null);
      builder.doTest("0×6", "0 × 6 ", 2, "0", null);

      builder.doTest("0×", "0 × ", 2, "0", null);
      builder.doTest("2×", "2 × ", 2, "2", null);
      builder.doTest("3×", "3 × ", 2, "3", null);
      builder.doTest("9×", "9 × ", 2, "9", null);
      builder.doTest("8×", "8 × ", 2, "8", null);

      builder.doTest("×1", "0 × 1 ", 2, "0", null);
      builder.doTest("×5", "0 × 5 ", 2, "0", null);
      builder.doTest("×9", "0 × 9 ", 2, "0", null);
      builder.doTest("×7", "0 × 7 ", 2, "0", null);
      builder.doTest("×3", "0 × 3 ", 2, "0", null);

      builder.doTest("×", "0 × ", 2, "0", null);

      builder.doTest("1×=", "", 0, "1", null);
      builder.doTest("5×=", "", 0, "25", null);
      builder.doTest("3×=", "", 0, "9", null);
      builder.doTest("2×=", "", 0, "4", null);
      builder.doTest("7×=", "", 0, "49", null);

      builder.doTest("×7=", "", 0, "0", null);
      builder.doTest("×2=", "", 0, "0", null);
      builder.doTest("×5=", "", 0, "0", null);
      builder.doTest("×9=", "", 0, "0", null);
      builder.doTest("×1=", "", 0, "0", null);

      builder.doTest("1×5×", "1 × 5 × ", 3, "5", null);
      builder.doTest("2×9×", "2 × 9 × ", 3, "18", null);
      builder.doTest("3×8×", "3 × 8 × ", 3, "24", null);
      builder.doTest("4×7×", "4 × 7 × ", 3, "28", null);
      builder.doTest("5×6×", "5 × 6 × ", 3, "30", null);

      builder.doTest("2×3×=", "", 0, "36", null);
      builder.doTest("9×2×=", "", 0, "324", null);
      builder.doTest("5×7×=", "", 0, "1 225", null);
      builder.doTest("6×4×=", "", 0, "576", null);
      builder.doTest("7×6×=", "", 0, "1 764", null);

      builder.doTest("1×1×2=", "", 0, "2", null);
      builder.doTest("6×2×2=", "", 0, "24", null);
      builder.doTest("1×7×3=", "", 0, "21", null);
      builder.doTest("0×2×8=", "", 0, "0", null);
      builder.doTest("5×9×3=", "", 0, "135", null);

      builder.doTest("2×4=×5", "8 × 5 ", 2, "8", null);
      builder.doTest("3×3=×7", "9 × 7 ", 2, "9", null);
      builder.doTest("5×9=×6", "45 × 6 ", 2, "45", null);
      builder.doTest("8×5=×4", "40 × 4 ", 2, "40", null);
      builder.doTest("7×1=×3", "7 × 3 ", 2, "7", null);

      builder.doTest("7×5=×", "35 × ", 2, "35", null);
      builder.doTest("4×3=×", "12 × ", 2, "12", null);
      builder.doTest("2×2=×", "4 × ", 2, "4", null);
      builder.doTest("8×8=×", "64 × ", 2, "64", null);
      builder.doTest("6×1=×", "6 × ", 2, "6", null);

      builder.doTest("3×1=×=", "", 0, "9", null);
      builder.doTest("5×5=×=", "", 0, "625", null);
      builder.doTest("8×3=×=", "", 0, "576", null);
      builder.doTest("4×8=×=", "", 0, "1 024", null);
      builder.doTest("1×4=×=", "", 0, "16", null);

      builder.doTest("5n×3=", "", 0, "-15", null);
      builder.doTest("1n×4=", "", 0, "-4", null);
      builder.doTest("6n×2=", "", 0, "-12", null);
      builder.doTest("8n×7=", "", 0, "-56", null);
      builder.doTest("7n×6=", "", 0, "-42", null);

      builder.doTest("4×4n=", "", 0, "-16", null);
      builder.doTest("2×7n=", "", 0, "-14", null);
      builder.doTest("8×3n=", "", 0, "-24", null);
      builder.doTest("2×6n=", "", 0, "-12", null);
      builder.doTest("4×7n=", "", 0, "-28", null);

      builder.doTest("0.1×1=", "", 0, "0,1", null);
      builder.doTest("2.5×5=", "", 0, "12,5", null);
      builder.doTest("8.2×9=", "", 0, "73,8", null);
      builder.doTest("4.3×4=", "", 0, "17,2", null);
      builder.doTest("7.5×6=", "", 0, "45", null);

      builder.doTest("7×1.2=", "", 0, "8,4", null);
      builder.doTest("2×3.5=", "", 0, "7", null);
      builder.doTest("4×5.6=", "", 0, "22,4", null);
      builder.doTest("5×7.8=", "", 0, "39", null);
      builder.doTest("3×9.1=", "", 0, "27,3", null);
   }

   @Test
   void divideOperationTest() throws OverflowException, InvalidInputException, UndefinedResultException {
      builder.doTest("2÷2=", "", 0, "1", null);
      builder.doTest("7÷3=", "", 0, "2,333333333333333", null);
      builder.doTest("1÷8=", "", 0, "0,125", null);
      builder.doTest("5÷6=", "", 0, "0,8333333333333333", null);
      builder.doTest("3÷9=", "", 0, "0,3333333333333333", null);

      builder.doTest("0÷5=", "", 0, "0", null);
      builder.doTest("0÷3=", "", 0, "0", null);

      builder.doTest("2÷5", "2 ÷ 5 ", 2, "2", null);
      builder.doTest("5÷3", "5 ÷ 3 ", 2, "5", null);
      builder.doTest("2÷3", "2 ÷ 3 ", 2, "2", null);
      builder.doTest("2÷8", "2 ÷ 8 ", 2, "2", null);
      builder.doTest("1÷2", "1 ÷ 2 ", 2, "1", null);

      builder.doTest("0÷0", "0 ÷ 0 ", 2, "0", null);
      builder.doTest("1÷0", "1 ÷ 0 ", 2, "1", null);
      builder.doTest("5÷0", "5 ÷ 0 ", 2, "5", null);
      builder.doTest("0÷2", "0 ÷ 2 ", 2, "0", null);
      builder.doTest("0÷6", "0 ÷ 6 ", 2, "0", null);

      builder.doTest("0÷", "0 ÷ ", 2, "0", null);
      builder.doTest("2÷", "2 ÷ ", 2, "2", null);
      builder.doTest("3÷", "3 ÷ ", 2, "3", null);
      builder.doTest("9÷", "9 ÷ ", 2, "9", null);
      builder.doTest("8÷", "8 ÷ ", 2, "8", null);

      builder.doTest("÷1", "0 ÷ 1 ", 2, "0", null);
      builder.doTest("÷5", "0 ÷ 5 ", 2, "0", null);
      builder.doTest("÷9", "0 ÷ 9 ", 2, "0", null);
      builder.doTest("÷7", "0 ÷ 7 ", 2, "0", null);
      builder.doTest("÷3", "0 ÷ 3 ", 2, "0", null);

      builder.doTest("÷", "0 ÷ ", 2, "0", null);

      builder.doTest("1÷=", "", 0, "1", null);
      builder.doTest("5÷=", "", 0, "1", null);
      builder.doTest("3÷=", "", 0, "1", null);
      builder.doTest("2÷=", "", 0, "1", null);
      builder.doTest("7÷=", "", 0, "1", null);

      builder.doTest("÷7=", "", 0, "0", null);
      builder.doTest("÷2=", "", 0, "0", null);
      builder.doTest("÷5=", "", 0, "0", null);
      builder.doTest("÷9=", "", 0, "0", null);
      builder.doTest("÷1=", "", 0, "0", null);

      builder.doTest("1÷5÷", "1 ÷ 5 ÷ ", 3, "0,2", null);
      builder.doTest("2÷9÷", "2 ÷ 9 ÷ ", 3, "0,2222222222222222", null);
      builder.doTest("3÷8÷", "3 ÷ 8 ÷ ", 3, "0,375", null);
      builder.doTest("4÷7÷", "4 ÷ 7 ÷ ", 3, "0,5714285714285714", null);
      builder.doTest("5÷6÷", "5 ÷ 6 ÷ ", 3, "0,8333333333333333", null);

      builder.doTest("2÷3÷=", "", 0, "1", null);
      builder.doTest("9÷2÷=", "", 0, "1", null);
      builder.doTest("5÷7÷=", "", 0, "1", null);
      builder.doTest("6÷4÷=", "", 0, "1", null);
      builder.doTest("7÷6÷=", "", 0, "1", null);

      builder.doTest("1÷1÷2=", "", 0, "0,5", null);
      builder.doTest("6÷2÷2=", "", 0, "1,5", null);
      builder.doTest("1÷7÷3=", "", 0, "0,0476190476190476", null);
      builder.doTest("0÷2÷8=", "", 0, "0", null);
      builder.doTest("5÷9÷3=", "", 0, "0,1851851851851852", null);

      builder.doTest("2÷4=÷5", "0,5 ÷ 5 ", 2, "0,5", null);
      builder.doTest("3÷3=÷7", "1 ÷ 7 ", 2, "1", null);
      builder.doTest("5÷9=÷6", "0,5555555555555556 ÷ 6 ", 2, "0,5555555555555556", null);
      builder.doTest("8÷5=÷4", "1,6 ÷ 4 ", 2, "1,6", null);
      builder.doTest("7÷1=÷3", "7 ÷ 3 ", 2, "7", null);

      builder.doTest("7÷5=÷", "1,4 ÷ ", 2, "1,4", null);
      builder.doTest("4÷3=÷", "1,333333333333333 ÷ ", 2, "1,333333333333333", null);
      builder.doTest("2÷2=÷", "1 ÷ ", 2, "1", null);
      builder.doTest("8÷8=÷", "1 ÷ ", 2, "1", null);
      builder.doTest("6÷1=÷", "6 ÷ ", 2, "6", null);

      builder.doTest("3÷1=÷=", "", 0, "1", null);
      builder.doTest("5÷5=÷=", "", 0, "1", null);
      builder.doTest("8÷3=÷=", "", 0, "1", null);
      builder.doTest("4÷8=÷=", "", 0, "1", null);
      builder.doTest("1÷4=÷=", "", 0, "1", null);

      builder.doTest("5n÷3=", "", 0, "-1,666666666666667", null);
      builder.doTest("1n÷4=", "", 0, "-0,25", null);
      builder.doTest("6n÷2=", "", 0, "-3", null);
      builder.doTest("8n÷7=", "", 0, "-1,142857142857143", null);
      builder.doTest("7n÷6=", "", 0, "-1,166666666666667", null);

      builder.doTest("4÷4n=", "", 0, "-1", null);
      builder.doTest("2÷7n=", "", 0, "-0,2857142857142857", null);
      builder.doTest("8÷3n=", "", 0, "-2,666666666666667", null);
      builder.doTest("2÷6n=", "", 0, "-0,3333333333333333", null);
      builder.doTest("4÷7n=", "", 0, "-0,5714285714285714", null);

      builder.doTest("0.1÷1=", "", 0, "0,1", null);
      builder.doTest("2.5÷5=", "", 0, "0,5", null);
      builder.doTest("8.2÷9=", "", 0, "0,9111111111111111", null);
      builder.doTest("4.3÷4=", "", 0, "1,075", null);
      builder.doTest("7.5÷6=", "", 0, "1,25", null);

      builder.doTest("7÷1.2=", "", 0, "5,833333333333333", null);
      builder.doTest("2÷3.5=", "", 0, "0,5714285714285714", null);
      builder.doTest("4÷5.6=", "", 0, "0,7142857142857143", null);
      builder.doTest("5÷7.8=", "", 0, "0,641025641025641", null);
      builder.doTest("3÷9.1=", "", 0, "0,3296703296703297", null);
   }

   @Test
   void equalsTests() throws OverflowException, UndefinedResultException, InvalidInputException {
      builder.doTest("=", "", 0, "0", null);

      builder.doTest("+=", "", 0, "0", null);
      builder.doTest("-=", "", 0, "0", null);
      builder.doTest("×=", "", 0, "0", null);
      builder.doTest("÷=", "", 0, "0", null);

      builder.doTest("3=", "", 0, "3", null);
      builder.doTest("2=", "", 0, "2", null);
      builder.doTest("4=", "", 0, "4", null);
      builder.doTest("5=", "", 0, "5", null);

      builder.doTest("2n=", "", 0, "-2", null);
      builder.doTest("3n=", "", 0, "-3", null);
      builder.doTest("8n=", "", 0, "-8", null);
      builder.doTest("1n=", "", 0, "-1", null);

      builder.doTest("0n=", "", 0, "0", null);

      builder.doTest("√=", "", 0, "0", null);
      builder.doTest("%=", "", 0, "0", null);
      builder.doTest("SQR =", "", 0, "0", null);
      builder.doTest("n=", "", 0, "0", null);

      builder.doTest("5+3=", "", 0, "8", "3");
      builder.doTest("2+2=", "", 0, "4", "2");
      builder.doTest("6+3=", "", 0, "9", "3");
      builder.doTest("5+1=", "", 0, "6", "1");

      builder.doTest("5+4=√", "√(9) ", 1, "9", "3");
      builder.doTest("5+4=%", "0.81 ", 1, "9", "0,81");
      builder.doTest("5+4= SQR ", "sqr(9) ", 1, "9", "81");
      builder.doTest("5+4=n", "negate(9) ", 1, "9", "-9");

      builder.doTest("2+3=+++", "5 + ", 2, "5", null);
      builder.doTest("2+3=---", "5 - ", 2, "5", null);
      builder.doTest("2+3=×××", "5 × ", 2, "5", null);
      builder.doTest("2+3=÷÷÷", "5 ÷ ", 2, "5", null);

      builder.doTest("239×4=+-×+", "956 + ", 2, "956", null);
      builder.doTest("239-4=+-×-", "235 - ", 2, "235", null);
      builder.doTest("239+4=+-÷×", "243 × ", 2, "243", null);
      builder.doTest("239÷4=+-×÷", "59,75 ÷ ", 2, "59,75", null);

      builder.doTest("10+=", "", 0, "20", null);
      builder.doTest("10+=+=", "", 0, "40", null);
      builder.doTest("10+=+=+=", "", 0, "80", null);

      builder.doTest("7÷3=1+", "1 + ", 2, "1", null);
      builder.doTest("2×4=4-", "4 - ", 2, "4", null);
      builder.doTest("5-8=5×", "5 × ", 2, "5", null);
      builder.doTest("1+2=6÷", "6 ÷ ", 2, "6", null);

      builder.doTest("1+3===", "", 0, "10", null);
      builder.doTest("289-102===", "", 0, "-17", null);
      builder.doTest("2×3===", "", 0, "54", null);
      builder.doTest("188÷2===", "", 0, "23,5", null);

      builder.doTest("2+3=1===", "", 0, "10", null);
      builder.doTest("3-1=2===", "", 0, "-1", null);
      builder.doTest("4×2=3===", "", 0, "24", null);
      builder.doTest("5÷7=4===", "", 0, "0,0116618075801749", null);

      builder.doTest("2=+", "2 + ", 2, "2", null);
      builder.doTest("4=-", "4 - ", 2, "4", null);
      builder.doTest("5=×", "5 × ", 2, "5", null);
      builder.doTest("8=÷", "8 ÷ ", 2, "8", null);

      builder.doTest("3=6+", "6 + ", 2, "6", null);
      builder.doTest("4=2-", "2 - ", 2, "2", null);
      builder.doTest("5=3×", "3 × ", 2, "3", null);
      builder.doTest("8=7÷", "7 ÷ ", 2, "7", null);

      builder.doTest("4√=", "", 0, "2", "2");
      builder.doTest("8 SQR =", "", 0, "64", "64");
      builder.doTest("2 1/x =", "", 0, "0,5", null);
      builder.doTest("5 % =", "", 0, "0", null);

      builder.doTest("5+3= 1/x", "1/(8) ", 1, "8", "0,125");
      builder.doTest("5+3= 1/x =", "", 0, "3,125", "0,125");
      builder.doTest("5+3= 1/x ==", "", 0, "6,125", null);
      builder.doTest("5+3= 1/x ===", "", 0, "9,125", null);
      builder.doTest("5+3= 1/x ===5=", "", 0, "9,125", null);
   }

   @Test
   void mixOfOperations() throws OverflowException, UndefinedResultException, InvalidInputException {
      builder.doTest("300+5=%√-1000000000000000= SQR + 9999999999999999 1/x × 1000000000000000 ÷ 50 ", "sqr(-999999999999964,5) + 1/(9999999999999999) × 1000000000000000 ÷ 50 ", 4, "9,99999999999928e+44", "50");
      builder.doTest("12345 1/x n 500 = 400 + % × 0.0001 SQR 123.3 √ 1/x ÷ 55 × 12253 + 124n - 73 CE 81", "400 + 1600 × 1/(√(123,3)) ÷ 55 × 12253 + negate(124) - 81 ", 7, "40 002,2155146669", "81");
      builder.doTest("0.01 × 123 + % = √ SQR + 20 1/x ÷ 7.1 - % + 876 CE 765n ", "sqr(√(1,245129)) + 1/(20) ÷ 7,1 - 0.0003405625920579250 + negate(765) ", 5, "0,1842028176896322", "-765");
      builder.doTest("82 + 1 - 23 C 210 + % √ n × 67.0987 SQR 1/x ÷ 8", "210 + negate(√(441)) × 1/(sqr(67,0987)) ÷ 8 ", 4, "0,0419791453045691", "8");
      builder.doTest("1 + 34 = C 123 n + 3 % n √ × 123.4 + 6 SQR - 90 1/x", "negate(123) + √(negate(-3.69)) × 123,4 + sqr(6) - 1/(90) ", 5, "-14 905,15634073024", "0,0111111111111111");

      builder.doTest("12 - 52 C 123 <<< + 125.4 1/x + 23 SQR - 89 √ × 71 - 1n %", "0 + 1/(125,4) + sqr(23) - √(89) × 71 - -368.8975352782175 ", 6, "36 889,75352782175", "-368,8975352782175");
      builder.doTest("25 × 12 C 234 + 34 < 5 1/x + 32 SQR × 250 √ + ÷ % n %", "234 + 1/(35) + sqr(32) × √(250) ÷ -787012312.5580016 ", 5, "19 891,17823641056", "-787 012 312,5580016");
      builder.doTest("234 ÷ 2 C 8765 - << 76 = SQR SQR + 1/x - 234 √ + × 123", "sqr(sqr(8689)) + 1/(5700056872635765) - √(234) × 123 ", 4, "5 700 056 872 635 750", "123");
      builder.doTest("12 + C 234 CE 324 - 1/x + 876 SQR × 637 √ %", "324 - 1/(324) + sqr(876) × 193758.7192131822 ", 4, "767 699,9969135802", "193 758,7192131822");
      builder.doTest("83 + 2 = C 21 - √ × 90 SQR + 8 1/x - % n", "21 - √(21) × sqr(90) + 1/(8) - negate(176840160.0876563) ", 5, "132 981,2618708577", "-176 840 160,0876563");

      builder.doTest("200+4% 1/x 1/x SQR √", "200 + √(sqr(1/(1/(8)))) ", 2, "200", "8");
      builder.doTest("1÷3= 1/x ", null, 0, null, "3");
      builder.doTest("5+2√1", "5 + 1 ", 2, "5", "1");
   }

   @Test
   void negateTest() throws OverflowException, InvalidInputException, UndefinedResultException {
      builder.doTest("1n", "negate(1) ", 1, "0", "-1");
      builder.doTest("2n", "negate(2) ", 1, "0", "-2");
      builder.doTest("7n", "negate(7) ", 1, "0", "-7");
      builder.doTest("132n", "negate(132) ", 1, "0", "-132");
      builder.doTest("165n", "negate(165) ", 1, "0", "-165");

      builder.doTest("1.04n", "negate(1,04) ", 1, "0", "-1,04");
      builder.doTest("100.0n", "negate(100) ", 1, "0", "-100");
      builder.doTest("13.2n", "negate(13,2) ", 1, "0", "-13,2");
      builder.doTest("14.4n", "negate(14,4) ", 1, "0", "-14,4");
      builder.doTest("31.2n", "negate(31,2) ", 1, "0", "-31,2");

      builder.doTest("5nn", "negate(negate(5)) ", 1, "0", "5");
      builder.doTest("3nn", "negate(negate(3)) ", 1, "0", "3");
      builder.doTest("4nn", "negate(negate(4)) ", 1, "0", "4");
      builder.doTest("5nn", "negate(negate(5)) ", 1, "0", "5");
      builder.doTest("123nn", "negate(negate(123)) ", 1, "0", "123");

      builder.doTest("5+n", "5 + negate(5) ", 2, "5", "-5");
      builder.doTest("21+n", "21 + negate(21) ", 2, "21", "-21");
      builder.doTest("14+n", "14 + negate(14) ", 2, "14", "-14");
      builder.doTest("3+n", "3 + negate(3) ", 2, "3", "-3");
      builder.doTest("7+n", "7 + negate(7) ", 2, "7", "-7");

      builder.doTest("43+n=", "", 0, "0", "-43");
      builder.doTest("0.4+n=", "", 0, "0", "-0,4");
      builder.doTest("3+n=", "", 0, "0", "-3");
      builder.doTest("8+n=", "", 0, "0", "-8");
      builder.doTest("7+n=", "", 0, "0", "-7");
   }

   @Test
   void squareTest() throws OverflowException, InvalidInputException, UndefinedResultException {
      builder.doTest("SQR", "sqr(0) ", 1, null, "0");

      builder.doTest("0 SQR", "sqr(0) ", 1, null, "0");
      builder.doTest("1 SQR", "sqr(1) ", 1, null, "1");
      builder.doTest("2 SQR", "sqr(2) ", 1, null, "4");
      builder.doTest("5 SQR", "sqr(5) ", 1, null, "25");

      builder.doTest("5n SQR", "sqr(negate(5)) ", 1,  null, "25");
      builder.doTest("2n SQR", "sqr(negate(2)) ", 1,  null, "4");
      builder.doTest("6n SQR", "sqr(negate(6)) ", 1,  null, "36");
      builder.doTest("4n SQR", "sqr(negate(4)) ", 1,  null,"16");

      builder.doTest("5 SQR +", "sqr(5) + ", 2, "25", null);
      builder.doTest("2 SQR -", "sqr(2) - ", 2, "4", null);
      builder.doTest("6 SQR ×", "sqr(6) × ", 2, "36", null);
      builder.doTest("8 SQR ÷", "sqr(8) ÷ ", 2, "64", null);

      builder.doTest("5n SQR +", "sqr(negate(5)) + ", 2, "25", null);
      builder.doTest("1n SQR -", "sqr(negate(1)) - ", 2, "1", null);
      builder.doTest("7n SQR ×", "sqr(negate(7)) × ", 2, "49", null);
      builder.doTest("9n SQR ÷", "sqr(negate(9)) ÷ ", 2, "81", null);

      builder.doTest("3 SQR +2", "sqr(3) + 2 ", 2, "9", "2");
      builder.doTest("4 SQR -5", "sqr(4) - 5 ", 2, "16", "5");
      builder.doTest("6 SQR ×6", "sqr(6) × 6 ", 2, "36", "6");
      builder.doTest("8 SQR ÷7", "sqr(8) ÷ 7 ", 2, "64", "7");

      builder.doTest("2 SQR SQR", "sqr(sqr(2)) ", 1, null, "16");
      builder.doTest("2 SQR SQR SQR", "sqr(sqr(sqr(2))) ", 1, null, "256");
      builder.doTest("2√ SQR √ SQR √ SQR", "sqr(√(sqr(√(sqr(√(2)))))) ", 1, null, "2");
      builder.doTest("3 SQR +2 SQR ", "sqr(3) + sqr(2) ", 2, "9", "4");
      builder.doTest("3 SQR +2 SQR =", "", 0, "13", null);
      builder.doTest("3 SQR +7=", "", 0, "16", null);
      builder.doTest("5+ SQR SQR", "5 + sqr(sqr(5)) ", 2, "5", "625");
      builder.doTest("5+ SQR SQR =", "", 0, "630", "625");
      builder.doTest("2+3= SQR", "sqr(5) ", 1, "5", "25");

      builder.doTest("0.0000000000000001 SQR", "sqr(0,0000000000000001) ", 1, "0", "1,e-32");
      builder.doTest("0.0000000000000001 SQR SQR", "sqr(sqr(0,0000000000000001)) ", 1, "0", "1,e-64");
      builder.doTest("0.0000000000000001n= SQR", "sqr(-0,0000000000000001) ", 1, null, "1,e-32");
      builder.doTest("0-0.0000000000000001= SQR", "sqr(-0,0000000000000001) ", 1, null, "1,e-32");
      builder.doTest("9999999999999999 SQR", "sqr(9999999999999999) ", 1, "0", "9,999999999999998e+31");
      builder.doTest("9999999999999999 SQR SQR", "sqr(sqr(9999999999999999)) ", 1, "0", "9,999999999999996e+63");
   }

   @Test
   void squareRootTest() throws OverflowException, InvalidInputException, UndefinedResultException {
      builder.doTest("√", "√(0) ", 1, null, "0");

      builder.doTest("0√", "√(0) ", 1, null, "0");
      builder.doTest("4√", "√(4) ", 1, null, "2");
      builder.doTest("4√√", "√(√(4)) ", 1, null, "1,414213562373095");
      builder.doTest("4√√√", "√(√(√(4))) ", 1, null, "1,189207115002721");

      builder.doTest("4√√√=", "", 0, "1,189207115002721", null);
      builder.doTest("3+4√=", "", 0, "5", null);
      builder.doTest("4+5=√", "√(9) ", 1, "9", null);
      builder.doTest("4+5=√=", "", 0, "8", "3");

      builder.doTest("4+√", "4 + √(4) ", 2, "4", "2");
      builder.doTest("4-1+√", "4 - 1 + √(3) ", 3, "3", "1,732050807568877");
      builder.doTest("4+1+1+1+√", "4 + 1 + 1 + 1 + √(7) ", 5, "7", "2,645751311064591");
      builder.doTest("4+29-8×2÷5+√", "4 + 29 - 8 × 2 ÷ 5 + √(10) ", 6, "10", "3,162277660168379");

      builder.doTest("121√", "√(121) ", 1, "0", "11");
      builder.doTest("456√", "√(456) ", 1, "0", "21,35415650406262");
      builder.doTest("2875√", "√(2875) ", 1, "0", "53,61902647381804");
      builder.doTest("1785√", "√(1785) ", 1, "0", "42,24926034855522");
      builder.doTest("2134√", "√(2134) ", 1, "0", "46,19523784980439");

      builder.doTest("0.0000000000000001√", "√(0,0000000000000001) ", 1, "0", "0,00000001");
      builder.doTest("0.0000000000000001√√", "√(√(0,0000000000000001)) ", 1, "0", "0,0001");

      builder.doTest("9999999999999999√", "√(9999999999999999) ", 1, "0", "100 000 000");
      builder.doTest("9999999999999999√√", "√(√(9999999999999999)) ", 1, "0", "10 000");
   }

   @Test
   void divideByTest() throws OverflowException, InvalidInputException, UndefinedResultException {
      builder.doExceptionsTest(" 1/x ", ExceptionType.DIVIDE_BY_ZERO);

      builder.doTest("1 1/x", "1/(1) ", 1, "0", "1");
      builder.doTest("2 1/x", "1/(2) ", 1, "0", "0,5");
      builder.doTest("10 1/x", "1/(10) ", 1, "0", "0,1");
      builder.doTest("1.1 1/x", "1/(1,1) ", 1, "0", "0,9090909090909091");

      builder.doTest("5 1/x +", "1/(5) + ", 2, "0,2", null);
      builder.doTest("2 1/x -", "1/(2) - ", 2, "0,5", null);
      builder.doTest("6 1/x ×", "1/(6) × ", 2, "0,1666666666666667", null);
      builder.doTest("9 1/x ÷", "1/(9) ÷ ", 2, "0,1111111111111111", null);

      builder.doTest("10 + 1/x ", "10 + 1/(10) ", 2, "10", "0,1");
      builder.doTest("21 - 1/x ", "21 - 1/(21) ", 2, "21", "0,0476190476190476");
      builder.doTest("32 × 1/x ", "32 × 1/(32) ", 2, "32", "0,03125");
      builder.doTest("42 ÷ 1/x ", "42 ÷ 1/(42) ", 2, "42", "0,0238095238095238");

      builder.doTest("10+12= 1/x ", "1/(22) ", 1, "22", "0,0454545454545454");
      builder.doTest("12+43= 1/x ", "1/(55) ", 1, "55", "0,0181818181818182");
      builder.doTest("21+36= 1/x ", "1/(57) ", 1, "57", "0,0175438596491228");
      builder.doTest("83+37= 1/x ", "1/(120) ", 1, "120", "0,0083333333333333");

      builder.doTest("3 1/x 1/x 1/x 1/x 1/x 1/x", null, 0, null, "3");
      builder.doTest("7 1/x 1/x 1/x 1/x 1/x 1/x", null, 0, null, "7");
      builder.doTest("11 1/x 1/x 1/x 1/x 1/x 1/x", null, 0, null, "11");
      builder.doTest("5 1/x 1/x 1/x 1/x 1/x 1/x", null, 0, null, "5");

      builder.doTest("1÷9= 1/x", null, 0, null, "9");
      builder.doTest("1÷3= 1/x", null, 0, null, "3");
      builder.doTest("1÷5= 1/x", null, 0, null, "5");
      builder.doTest("1÷7= 1/x", null, 0, null, "7");

      builder.doTest("1000000000000000- 1/x", "1000000000000000 - 1/(1000000000000000) ", 2, null, "0,000000000000001");
      builder.doTest("1000000000000000×=- 1/x", "1,e+30 - 1/(1,e+30) ", 2, null, "1,e-30");
      builder.doTest("1000000000000000×=====- 1/x", "1,e+90 - 1/(1,e+90) ", 2, null, "1,e-90");

      builder.doTest("0.0000000000000001 1/x ", "1/(0,0000000000000001) ", 1, null, "1,e+16");
      builder.doTest("0.0000000000000001 1/x 1/x ", "1/(1/(0,0000000000000001)) ", 1, null, "0,0000000000000001");

      builder.doTest("9999999999999999 1/x ", "1/(9999999999999999) ", 1, null, "1,e-16");
      builder.doTest("9999999999999999 1/x 1/x ", "1/(1/(9999999999999999)) ", 1, null, "9 999 999 999 999 999");
   }

   @Test
   void percentTest() throws OverflowException, UndefinedResultException, InvalidInputException {
      builder.doTest("%", "0 ", 1, "0", "0");

      builder.doTest("2%", "0 ", 1, "0", "0");
      builder.doTest("168%", "0 ", 1, "0", "0");
      builder.doTest("25%", "0 ", 1, "0", "0");
      builder.doTest("7%%", "0 ", 1, "0", "0");

      builder.doTest("6n%", "0 ", 1, "0", "0");
      builder.doTest("2n%", "0 ", 1, "0", "0");
      builder.doTest("4n%", "0 ", 1, "0", "0");
      builder.doTest("5n%", "0 ", 1, "0", "0");

      builder.doTest("10%%%=", "", 0, "0", "0");
      builder.doTest("32%%%=", "", 0, "0", "0");
      builder.doTest("41%%%=", "", 0, "0", "0");
      builder.doTest("12%%%=", "", 0, "0", "0");

      builder.doTest("200+2%", "200 + 4 ", 2, "200", "4");
      builder.doTest("152+3%", "152 + 4.56 ", 2, "152", "4,56");
      builder.doTest("234+4%", "234 + 9.36 ", 2, "234", "9,36");
      builder.doTest("543+5%", "543 + 27.15 ", 2, "543", "27,15");

      builder.doTest("5+%", "5 + 0.25 ", 2, "5", "0,25");
      builder.doTest("1+%", "1 + 0.01 ", 2, "1", "0,01");
      builder.doTest("3+%", "3 + 0.09 ", 2, "3", "0,09");
      builder.doTest("4+%", "4 + 0.16 ", 2, "4", "0,16");

      builder.doTest("200+2%=", "", 0, "204", null);
      builder.doTest("152+3%=", "", 0, "156,56", null);
      builder.doTest("234+4%=", "", 0, "243,36", null);
      builder.doTest("543+5%=", "", 0, "570,15", null);

      builder.doTest("200+2%=%", "416.16 ", 1, null, "416,16");
      builder.doTest("152+3%=%", "245.110336 ", 1, null, "245,110336");
      builder.doTest("234+4%=%", "592.240896 ", 1, null, "592,240896");
      builder.doTest("543+5%=%", "3250.710225 ", 1, null, "3 250,710225");

      builder.doTest("199+1=%", "400 ", 1, "200", "400");
      builder.doTest("199+1=%%", "800 ", 1, "200", "800");
      builder.doTest("199+1=%%%", "1600 ", 1, "200", "1 600");
      builder.doTest("199+1=%%%%", "3200 ", 1, "200", "3 200");

      builder.doTest("200+25%%%%%%%%%", "200 + 12800 ", 2, "200", null);
      builder.doTest("200+25%%%%%%%%%=", "", 0, "13 000", null);
      builder.doTest("300n+15%=", "", 0, "-345", null);
      builder.doTest("245+63n%=", "", 0, "90,65", null);
      builder.doTest("199+1=", "", 0, "200", "1");
   }

   @Test
   void backspaceTest() throws OverflowException, UndefinedResultException, InvalidInputException {
      builder.doTest("2 SQR SQR SQR <<<<<<<<=", "", 0, "256", null);
      builder.doTest("70÷7=<<<", "", 0, "10", null);
      builder.doTest("1234567890<<<<<<<<<<<<<<<<<<<<<=", "", 0, "0", null);
      builder.doTest("<<<<2√ SQR =", "", 0, "2", null);
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
      builder.doTest("C", "", 0, "0", "0");

      builder.doTest("4 C", "", 0, "0", "4");
      builder.doTest("1 C", "", 0, "0", "1");
      builder.doTest("3 C", "", 0, "0", "3");
      builder.doTest("4 C", "", 0, "0", "4");

      builder.doTest("42 C 12", "", 0, "0", "12");
      builder.doTest("85 C 47", "", 0, "0", "47");
      builder.doTest("78 C 65", "", 0, "0", "65");
      builder.doTest("96 C 32", "", 0, "0", "32");

      builder.doTest("96 C 32 C", "", 0, "0", "32");
      builder.doTest("75 C 76 C", "", 0, "0", "76");
      builder.doTest("95 C 43 C", "", 0, "0", "43");
      builder.doTest("32 C 16 C", "", 0, "0", "16");

      builder.doTest("32 + C 16 - ", "16 - ", 2, "16", "16");
      builder.doTest("75 - C 21 × ", "21 × ", 2, "21", "21");
      builder.doTest("65 × C 82 ÷ ", "82 ÷ ", 2, "82", "82");
      builder.doTest("32 ÷ C 10 + ", "10 + ", 2, "10", "10");

      builder.doTest("+ C ÷", "0 ÷ ", 2, "0", "0");
      builder.doTest("- C ×", "0 × ", 2, "0", "0");
      builder.doTest("× C -", "0 - ", 2, "0", "0");
      builder.doTest("÷ C +", "0 + ", 2, "0", "0");

      builder.doTest("2 + 7 C 8 ÷ 2 C", "", 0, "0", "2");
      builder.doTest("2 - 7 C 8 × 2 C", "", 0, "0", "2");
      builder.doTest("2 × 7 C 8 - 2 C", "", 0, "0", "2");
      builder.doTest("2 ÷ 7 C 8 + 2 C", "", 0, "0", "2");

      builder.doTest("2+2×2-6×3-10 C", "", 0, "0", null);
      builder.doTest("2+5÷2 C C C C C C C C C", "", 0, "0", null);
      builder.doTest("5 SQR SQR SQR SQR SQR SQR √√ SQR SQR SQR SQR SQR√ SQR SQR SQR C C", "", 0, "0", null);
   }

   @Test
   void testClearEntry() throws OverflowException, UndefinedResultException, InvalidInputException {
      builder.doTest(" CE ","", 0, "0", "0");

      builder.doTest("5 CE ","", 0, "0", "0");
      builder.doTest("85 CE", "", 0, "0", "0");
      builder.doTest("894 CE", "", 0, "0", "0");
      builder.doTest("1245 CE", "", 0, "0", "0");

      builder.doTest("1245 CE 45", "", 0, "0", "45");
      builder.doTest("9845 CE 23", "", 0, "0", "23");
      builder.doTest("1889 CE 87", "", 0, "0", "87");
      builder.doTest("4651 CE 62", "", 0, "0", "62");

      builder.doTest("4651 CE 21 CE ", "", 0, "0", "0");
      builder.doTest("7621 CE 45 CE ", "", 0, "0", "0");
      builder.doTest("9276 CE 32 CE ", "", 0, "0", "0");
      builder.doTest("4628 CE 70 CE ", "", 0, "0", "0");

      builder.doTest("+ CE ÷", "0 + 0 ÷ ", 3, "0", "0");
      builder.doTest("- CE ×", "0 - 0 × ", 3, "0", "0");
      builder.doTest("× CE -", "0 × 0 - ", 3, "0", "0");
      builder.doTest("+ CE +", "0 + 0 + ", 3, "0", "0");

      builder.doTest("1+2÷3 CE 9", "1 + 2 ÷ 9 ", 3, "3", "9");
      builder.doTest("1-2×3 CE 9", "1 - 2 × 9 ", 3, "-1", "9");
      builder.doTest("1×2-3 CE 9", "1 × 2 - 9 ", 3, "2", "9");
      builder.doTest("1÷2+3 CE 9", "1 ÷ 2 + 9 ", 3, "0,5", "9");

      builder.doTest("1 CE 5 CE 8 + 2 CE 6 ÷ 1 CE 5", "8 + 6 ÷ 5 ", 3, null, "5");
      builder.doTest("2 CE 6 CE 9 - 3 CE 7 × 2 CE 6", "9 - 7 × 6 ", 3, null, "6");
      builder.doTest("3 CE 7 CE 0 × 4 CE 8 - 3 CE 7", "0 × 8 - 7 ", 3, null, "7");
      builder.doTest("4 CE 8 CE 1 ÷ 5 CE 9 + 4 CE 8", "1 ÷ 9 + 8 ", 3, null, "8");
   }

   @Test
   void testRoundingMode() throws OverflowException, UndefinedResultException, InvalidInputException {
      builder.doTest("0.0000000000000001+1=", "", 0, "1", null);
      builder.doTest("1÷3×3=", "", 0, "1", null);
      builder.doTest("1÷3×3-1", "1 ÷ 3 × 3 - 1 ", 4, "1", null);
      builder.doTest("0.0111111111111111×0.1=", "", 0, "0,0011111111111111", null);
      builder.doTest("0.0111111111111111×0.1==", "", 0, "1,11111111111111e-4", null);
      builder.doTest("0.0111111111111111×0.1===", "", 0, "1,11111111111111e-5", null);
      builder.doTest("1÷3×3-1=", "", 0, "0", null);
      builder.doTest("2.0000000000000001+1========", "", 0, "10", null);
      builder.doTest("0.1×================", "", 0, "1,e-17", null);
      builder.doTest("0.9×=================================================================", "", 0, "9,550049507968252e-4", null);
      builder.doTest("9999999999999999+2=", "", 0, "1,e+16", null);
      builder.doTest("9999999999999999×2=", "", 0, "2,e+16", null);
      builder.doTest("9999999999999999×8=", "", 0, "7,999999999999999e+16", null);
      builder.doTest("9999999999999999×8==", "", 0, "6,399999999999999e+17", null);
      builder.doTest("9999999999999999×8===", "", 0, "5,119999999999999e+18", null);
      builder.doTest("9999999999999999×8====", "", 0, "4,096e+19", null);
      builder.doTest("9999999999999999×6=", "", 0, "5,999999999999999e+16", null);
      builder.doTest("9999999999999999×6==", "", 0, "3,6e+17", null);
      builder.doTest("9999999999999999×6===", "", 0, "2,16e+18", null);
      builder.doTest("9999999999999999×6====", "", 0, "1,296e+19", null);
      builder.doTest("9999999999999999×6=====", "", 0, "7,775999999999999e+19", null);
      builder.doTest("9999999999999999+6=", "", 0, "1,e+16", null);
      builder.doTest("9999999999999999+7=", "", 0, "1,000000000000001e+16", null);
      builder.doTest("9999999999999999 1/x +1=", "", 0, "1", null);
      builder.doTest("9999999999999999 1/x =", "", 0, "1,e-16", null);
      builder.doTest("9999999999999999 1/x +=", "", 0, "2,e-16", null);
      builder.doTest("9999999999999999 1/x +==", "", 0, "3,e-16", null);
      builder.doTest("9999999999999999 1/x +===", "", 0, "4,e-16", null);
      builder.doTest("9999999999999999 1/x +====", "", 0, "5,000000000000001e-16", null);
      builder.doTest("2.000000000000001+1=", "", 0, "3,000000000000001", null);
      builder.doTest("2.000000000000001+2=", "", 0, "4,000000000000001", null);
      builder.doTest("2.000000000000001+3=", "", 0, "5,000000000000001", null);
      builder.doTest("2.000000000000001+4=", "", 0, "6,000000000000001", null);
      builder.doTest("2.000000000000001+8=", "", 0, "10", null);

      builder.doTest("0.0000000000000001+=", "", 0, "0,0000000000000002", null);
      builder.doTest("0.0000000000000001-=", "", 0, "0", null);
      builder.doTest("1÷7×7-1=", "", 0, "0", null);
      builder.doTest("1÷7×1000000000000000×7-1000000000000000=", "", 0, "0", null);
      builder.doTest("1÷3÷7÷11÷13÷17×1000000000000000×3×7×11×13×17-1000000000000000=", "", 0, "0", null);
      builder.doTest("5√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√", null, 0, "0", "1,000000000000002");
      builder.doTest("1÷3×0.0000000000000001×0.00000000001×1000000000000000×10000000000000×3=", "", 0, "10", null);
      builder.doTest("0.9999999999999999×999999999999999.1=", "", 0, "999 999 999 999 999", null);
      builder.doTest("0.9999999999999999×999999999999999.3=", "", 0, "999 999 999 999 999,2", null);
      builder.doTest("0.9999999999999999×999999999999999.5=", "", 0, "999 999 999 999 999,4", null);
      builder.doTest("0.9999999999999999×999999999999999.7=", "", 0, "999 999 999 999 999,6", null);
      builder.doTest("0.9999999999999999×99999999999999.11=", "", 0, "99 999 999 999 999,1", null);

      builder.doTest("10÷3==×1000000000000000========", "", 0, "1,111111111111111e+120", null);
   }

   @Test
   void buildOperandTest() throws OverflowException, InvalidInputException, UndefinedResultException {
      builder.doTest("1", "", 0, null, "1");
      builder.doTest("2", "", 0, null, "2");
      builder.doTest("3", "", 0, null, "3");
      builder.doTest("4", "", 0, null, "4");
      builder.doTest("5", "", 0, null, "5");
      builder.doTest("6", "", 0, null, "6");
      builder.doTest("7", "", 0, null, "7");
      builder.doTest("8", "", 0, null, "8");
      builder.doTest("9", "", 0, null, "9");

      builder.doTest("0000000000000000000000000000000000000001", "", 0, null, "1");
      builder.doTest("01010111101010101000011", "", 0, null, "1 010 111 101 010 101");
      builder.doTest("123456789", "", 0, null, "123 456 789");
      builder.doTest("1000000000000000000", "", 0, null, "1 000 000 000 000 000");
      builder.doTest("1000000000000000000.", "", 0, null, "1 000 000 000 000 000,");
      builder.doTest("9999999999999999999.", "", 0, null, "9 999 999 999 999 999,");
      builder.doTest("1234567890123456789", "", 0, null, "1 234 567 890 123 456");

      builder.doTest("3.0", "", 0, null, "3,0");
      builder.doTest("3.00", "", 0, null, "3,00");
      builder.doTest("0.00000000000000000", "", 0, null, "0,0000000000000000");
      builder.doTest("3.00000000000000000", "", 0, null, "3,000000000000000");
      builder.doTest("3.000000000000000001", "", 0, null, "3,000000000000000");
      builder.doTest("10.000000000000000001", "", 0, null, "10,00000000000000");
      builder.doTest("100.000000000000000001", "", 0, null, "100,0000000000000");
      builder.doTest("1000.000000000000000001", "", 0, null, "1 000,000000000000");
      builder.doTest("100000000000000.000000000000000001", "", 0, null, "100 000 000 000 000,0");
      builder.doTest("1000000000000000.000000000000000001", "", 0, null, "1 000 000 000 000 000,");
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

      builder.doTest("1.00001√√√", "√(√(√(1,00001))) ", 1, null, null);
   }

   @Test
   void memoryTest() throws OverflowException, UndefinedResultException, InvalidInputException {
      builder.doTest("MR", "", 0, "0", "0");
      builder.doTest("M+ MR", "", 0, "0", "0");
      builder.doTest("M- MR", "", 0, "0", "0");

      // takes from operand
      builder.doTest("4 M+ MR ", null, 0, null, "4");
      builder.doTest("4 M+ M+ MR ", null, 0, null, "8");
      builder.doTest("4n M+ MR ", null, 0, null, "-4");
      builder.doTest("4n M+ M+ MR ", null, 0, null, "-8");

      builder.doTest("4 M- MR ", null, 0, null, "-4");
      builder.doTest("4 M- M- MR ", null, 0, null, "-8");
      builder.doTest("4n M- MR ", null, 0, null, "4");
      builder.doTest("4n M- M- MR ", null, 0, null, "8");
      
      builder.doTest("0 M- MR ", null, 0, "0", "0");
      builder.doTest("0 M- M- MR ", null, 0, "0", "0");
      builder.doTest("0n M-  MR ", null, 0, "0", "0");
      builder.doTest("0n M- M- MR ", null, 0, "0", "0");

      builder.doTest("0 M+ MR ", null, 0, "0", "0");
      builder.doTest("0 M+ M+ MR ", null, 0, "0", "0");
      builder.doTest("0n M+  MR ", null, 0, "0", "0");
      builder.doTest("0n M+ M+ MR ", null, 0, "0", "0");

      builder.doTest(". M+ MR", null, 0, "0", "0");
      builder.doTest("0. M+ MR", null, 0, "0", "0");
      builder.doTest(".n M+ MR", null, 0, "0", "0");
      builder.doTest("0.n M+ MR", null, 0, "0", "0");

      builder.doTest(". M- MR", null, 0, "0", "0");
      builder.doTest("0. M- MR", null, 0, "0", "0");
      builder.doTest(".n M- MR", null, 0, "0", "0");
      builder.doTest("0.n M- MR", null, 0, "0", "0");

      builder.doTest("1 M+ M+ ÷ MR =", "", 0, "0,5", null);
      builder.doTest("2 M+ M+ × MR =", "", 0, "8", null);
      builder.doTest("3 M+ M+ - MR =", "", 0, "-3", null);
      builder.doTest("4 M+ M+ + MR =", "", 0, "12", null);

      builder.doTest("1 M- M- ÷ MR =", "", 0, "-0,5", null);
      builder.doTest("2 M- M- × MR =", "", 0, "-8", null);
      builder.doTest("3 M- M- - MR =", "", 0, "9", null);
      builder.doTest("4 M- M- + MR =", "", 0, "-4", null);

      builder.doTest("1 M+ M+ M+ M+ MR =", "", 0, "4", null);
      builder.doTest("2 M+ M+ M+ M- MR =", "", 0, "4", null);
      builder.doTest("3 M+ M+ M- M- MR =", "", 0, "0", null);
      builder.doTest("4 M+ M- M- M- MR =", "", 0, "-8", null);

      // takes from result
      builder.doTest("1+2-3×4÷5+ M+ MR =", "", 0, "0", null);
      builder.doTest("1-2×3÷4+5+ M+ MR =", "", 0, "8,5", null);
      builder.doTest("1×2÷3+4+5- M+ MR =", "", 0, "0", null);
      builder.doTest("1÷2+3+4-5× M+ MR =", "", 0, "6,25", null);

      builder.doTest("2+2= M+ M+ M+ M+ MR + 0=", "", 0, "16", null);
      builder.doTest("1+5= M+ M+ M+ M+ MR + 0=", "", 0, "24", null);
      builder.doTest("3+6= M+ M+ M+ M+ MR + 0=", "", 0, "36", null);
      builder.doTest("4+7= M+ M+ M+ M+ MR + 0=", "", 0, "44", null);

      builder.doTest("0 + 20 M- M- M- M- M- =- MR =", "", 0, "120", null);
      builder.doTest("1 + 21 M- M- M- M- M- =+ MR =", "", 0, "-83", null);
      builder.doTest("2 + 22 M- M- M- M- M- =× MR =", "", 0, "-2 640", null);
      builder.doTest("3 + 23 M- M- M- M- M- =÷ MR =", "", 0, "-0,2260869565217391", null);

      builder.doTest("0,0000000000000001 SQR SQR SQR SQR SQR SQR M+ √√√√ SQR SQR SQR SQR M- MR ", null, 0, "0", "0");
      builder.doTest("0,0000000000000001 SQR SQR SQR SQR SQR SQR M+ √√√√ SQR SQR SQR SQR M- MR =", null, 0, "0", "0");

      builder.doTest("1000000000000000×1000000000000000×1000000000000000= M- C 1÷7×1000000000000000×1000000000000000×1000000000000000×7= M+ C MR=", null, 0, "0", null);
      builder.doTest("1000000000000000×1000000000000000×1000000000000000= M- C 1÷3×1000000000000000×1000000000000000×1000000000000000×3= M+ C MR=", null, 0, "0", null);
      builder.doTest("1000000000000000×1000000000000000×1000000000000000= M- C 1÷5×1000000000000000×1000000000000000×1000000000000000×5= M+ C MR=", null, 0, "0", null);
      builder.doTest("1000000000000000×1000000000000000×1000000000000000= M- C 1÷1×1000000000000000×1000000000000000×1000000000000000×11= M+ C MR=", null, 0, "0", null);
   }

   @Test
   void checkExceptions() {
      // overflow
      builder.doExceptionsTest("1000000000000000 SQR SQR SQR SQR SQR SQR SQR SQR SQR SQR", ExceptionType.OVERFLOW);
      builder.doExceptionsTest("1000000000000000×=×=×=×=×=×=×=×=×=×=", ExceptionType.OVERFLOW);
      builder.doExceptionsTest("-1000000000000000×=×=×=×=×=×=×=×=×=×=", ExceptionType.OVERFLOW);
      builder.doExceptionsTest("1000000000000000×=×=×=×=×=×=×=×=×= M+ × MR n =", ExceptionType.OVERFLOW);

      builder.doExceptionsTest("0.0000000000000001 SQR SQR SQR SQR SQR SQR SQR SQR SQR SQR", ExceptionType.OVERFLOW);
      builder.doExceptionsTest("0.0000000000000001 ×=×=×=×=×=×=×=×=×=×=", ExceptionType.OVERFLOW);
      builder.doExceptionsTest("-0.0000000000000001 ×=×=×=×=×=×=×=×=×=×=", ExceptionType.OVERFLOW);
      builder.doExceptionsTest("0.0000000000000001 ×=×=×=×=×=×=×=×=×= M+ × MR n =", ExceptionType.OVERFLOW);

      // undefined
      builder.doExceptionsTest("0÷0=", ExceptionType.UNDEFINED_RESULT);
      builder.doExceptionsTest("0.0000000000000000÷0=", ExceptionType.UNDEFINED_RESULT);
      builder.doExceptionsTest("1-1 M+ MR ÷0=", ExceptionType.UNDEFINED_RESULT);
      builder.doExceptionsTest("0÷0+", ExceptionType.UNDEFINED_RESULT);

      builder.doExceptionsTest("2+2×2-8÷0=", ExceptionType.UNDEFINED_RESULT);
      builder.doExceptionsTest("2+4×6-36÷0=", ExceptionType.UNDEFINED_RESULT);
      builder.doExceptionsTest("5+1×8-48÷0=", ExceptionType.UNDEFINED_RESULT);
      builder.doExceptionsTest("9+4×3-39÷0=", ExceptionType.UNDEFINED_RESULT);

      // divide by zero
      builder.doExceptionsTest("1÷0=", ExceptionType.DIVIDE_BY_ZERO);
      builder.doExceptionsTest("2n÷0=", ExceptionType.DIVIDE_BY_ZERO);
      builder.doExceptionsTest("3÷0=", ExceptionType.DIVIDE_BY_ZERO);
      builder.doExceptionsTest("0.0000000000000001÷0=", ExceptionType.DIVIDE_BY_ZERO);

      builder.doExceptionsTest("2+1-10*2÷0=", ExceptionType.DIVIDE_BY_ZERO);
      builder.doExceptionsTest("3+6-32*3÷0=", ExceptionType.DIVIDE_BY_ZERO);
      builder.doExceptionsTest("4+7-34*6÷0=", ExceptionType.DIVIDE_BY_ZERO);
      builder.doExceptionsTest("5+8-84*9÷0=", ExceptionType.DIVIDE_BY_ZERO);

      builder.doExceptionsTest("0 1/x ", ExceptionType.DIVIDE_BY_ZERO);
      builder.doExceptionsTest("8÷0+", ExceptionType.DIVIDE_BY_ZERO);
      builder.doExceptionsTest("8÷0=", ExceptionType.DIVIDE_BY_ZERO);

      // invalid input
      builder.doExceptionsTest("5n√", ExceptionType.INVALID_INPUT);
      builder.doExceptionsTest("50-45-500√=√", ExceptionType.INVALID_INPUT);
      builder.doExceptionsTest("0.4565468n√", ExceptionType.INVALID_INPUT);
      builder.doExceptionsTest("-0.57894=√", ExceptionType.INVALID_INPUT);
   }

   @Test
   void boundaryTest() throws OverflowException, UndefinedResultException, InvalidInputException {

      // right side
      // expected number : 9999999..9.999..94
      // where the integer part have digit 9 10000 times and imaginary part of the number have digit 9 20000 times.
      builder.doBoundaryTest(theMaxNumber, "9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999.99999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999994");

      // 1e10000
      builder.doExceptionsTest(theMaxNumber + " MC M+ C " + smallNumber + " + MR = ", ExceptionType.OVERFLOW);

      // 1e10000 + smallNumber
      builder.doExceptionsTest(theMaxNumber + " MC M+ C " + doubleSmallNumber + " + MR = ", ExceptionType.OVERFLOW);

      // gets 1.000000000000001e-10000 number
      builder.doBoundaryTest(smallNumber + "×0.1000000000000001=", "1.000000000000001e-10000");

      // gets 1e-10000
      builder.doExceptionsTest(smallNumber + "×0.1=", ExceptionType.OVERFLOW);

      // gets 9.999999999999999e-10001 number
      builder.doExceptionsTest(smallNumber + "×0.0999999999999999=", ExceptionType.OVERFLOW);

      // left side
      // -999999..9.999999999...94
      // where the integer part have digit 9 10000 times and imaginary part of the number have digit 9 20000 times.
      builder.doBoundaryTest(theMinNumber, "-9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999.99999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999994");

      // -1e10000
      builder.doExceptionsTest(theMinNumber + " MC M+ C " + smallNumber + " ×1n + MR = ", ExceptionType.OVERFLOW);

      // -1e10000 + smallNumber
      builder.doExceptionsTest(theMinNumber + " MC M+ C " + doubleSmallNumber + " ×1n + MR = ", ExceptionType.OVERFLOW);

      // gets -1.000000000000001e-10000 number
      builder.doBoundaryTest(smallNumber + " ×0.1000000000000001n= ", "-1.000000000000001e-10000");

      // gets -1e-10000
      builder.doExceptionsTest(smallNumber + "M+ C 0 - MR =" + "×0.1=", ExceptionType.OVERFLOW);

      // gets -9.999999999999999e-10001 number
      builder.doExceptionsTest(smallNumber + "M+ C 0 - MR =" + "×0.0999999999999999=", ExceptionType.OVERFLOW);
   }

   @Test
   void testWithSpecialOperationsUsingBounds() throws OverflowException, InvalidInputException, UndefinedResultException {
      builder.doExceptionsTest(theMaxNumber + " SQR ", ExceptionType.OVERFLOW);
      builder.doTest(theMaxNumber + " √√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√ ", null, 0, null, "1");
      builder.doExceptionsTest(theMaxNumber + " + % ", ExceptionType.OVERFLOW);
      builder.doExceptionsTest(theMaxNumber + " + 1/x ", ExceptionType.OVERFLOW);

      builder.doExceptionsTest(theMinNumber + " SQR ", ExceptionType.OVERFLOW);
      builder.doExceptionsTest(theMinNumber + " √√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√√ ", ExceptionType.INVALID_INPUT);
      builder.doExceptionsTest(theMinNumber + " + % ", ExceptionType.OVERFLOW);
      builder.doExceptionsTest(theMinNumber + " + 1/x ", ExceptionType.OVERFLOW);
   }

   @Test
   void specialCases() throws OverflowException, InvalidInputException, UndefinedResultException {
      builder.doTest("÷×-+", "0 + ", 2, "0", null);
      builder.doTest("5+=", "", 0, "10", null);
      builder.doTest("1+3===", "", 0, "10", null);
      builder.doTest("3n3333.3333", null, 0, "0", "-33 333,3333");
      builder.doTest("4-1+√", "4 - 1 + √(3) ", 3, "3", "1,732050807568877");
      builder.doTest("1000000000000000×=====- 1/x", "1,e+90 - 1/(1,e+90) ", 2, null, "1,e-90");
      builder.doTest("5+%", "5 + 0.25 ", 2, "5", "0,25");
      builder.doTest("2 SQR SQR SQR <<<<<<<<=", "", 0, "256", null);
      builder.doTest("2+3=<<<<", "", 0, "5", null);
      builder.checkBuildOperand("1000000000000000.", "1 000 000 000 000 000,");
      builder.doTest("+ CE ÷", "0 + 0 ÷ ", 3, "0", "0");
      builder.doExceptionsTest("0÷0=", ExceptionType.UNDEFINED_RESULT);
   }
}