package com.implemica.model.calculator;

import com.implemica.model.interfaces.History;
import com.implemica.model.interfaces.SpecialOperation;
import com.implemica.model.numerals.Arabic;
import com.implemica.model.numerals.numbers.Number;
import com.implemica.model.operations.SimpleOperation;
import com.implemica.model.operations.simple.Divide;
import com.implemica.model.operations.simple.Minus;
import com.implemica.model.operations.simple.Multiply;
import com.implemica.model.operations.simple.Plus;
import com.implemica.model.operations.special.*;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

   private Calculator calculator;

   private final String SQRT = "√";

   @BeforeEach
   public void init() {
      calculator = new Calculator(new Arabic());
   }

   /**
    * Group of tests which test this pattern:
    *    <code>N + + + +</code>
    * As a result must be number N. In a history must be
    * only two {@link SimpleOperation} - Default and something
    * else of simple operation.
    */
   @Test
   public void manyPlusOperations(){
      buildOperand(Number.THREE);

      executeSimpleOperation(new Plus());
      executeSimpleOperation(new Plus());
      executeSimpleOperation(new Plus());
      executeSimpleOperation(new Plus());
      executeSimpleOperation(new Plus());

      checkHistory("3 + ", 2);
      checkResult("3");
   }

   @Test
   public void manyMinusOperations() {
      buildOperand(Number.FIVE);

      executeSimpleOperation(new Minus());
      executeSimpleOperation(new Minus());
      executeSimpleOperation(new Minus());
      executeSimpleOperation(new Minus());
      executeSimpleOperation(new Minus());

      checkHistory("5 - ", 2);
      checkResult("5");
   }

   @Test
   public void manyMultiplyOperations() {
      buildOperand(Number.EIGHT);

      executeSimpleOperation(new Multiply());
      executeSimpleOperation(new Multiply());
      executeSimpleOperation(new Multiply());
      executeSimpleOperation(new Multiply());
      executeSimpleOperation(new Multiply());

      checkHistory("8 * ", 2);
      checkResult("8");
   }

   @Test
   public void manyDivideOperations() {
      buildOperand(Number.ONE);
      buildOperand(Number.TWO);

      executeSimpleOperation(new Divide());
      executeSimpleOperation(new Divide());
      executeSimpleOperation(new Divide());
      executeSimpleOperation(new Divide());

      checkHistory("12 / ", 2);
      checkResult("12");
   }

   /**
    * Pattern:
    *    <code>N + eq</code>
    * result must be:
    *    <code>N + N</code>
    */
   @Test
   public void plusOperationsEquals(){
      buildOperand(Number.TWO);

      executeSimpleOperation(new Plus());
      executeSimpleOperation(new Plus());
      executeSimpleOperation(new Plus());
      executeSimpleOperation(new Plus());
      executeSimpleOperation(new Plus());

      equals();

      checkHistory("", 0);
      checkResult("4");
   }

   @Test
   public void minusOperationsEquals() {
      buildOperand(Number.FOUR);

      executeSimpleOperation(new Minus());
      executeSimpleOperation(new Minus());
      executeSimpleOperation(new Minus());
      executeSimpleOperation(new Minus());
      executeSimpleOperation(new Minus());

      equals();

      checkHistory("", 0);
      checkResult("0");
   }

   @Test
   public void multiplyOperationsEquals() {
      buildOperand(Number.FOUR);
      buildOperand(Number.FIVE);

      executeSimpleOperation(new Multiply());
      executeSimpleOperation(new Multiply());
      executeSimpleOperation(new Multiply());
      executeSimpleOperation(new Multiply());

      equals();

      checkHistory("", 0);
      checkResult("2025");
   }

   @Test
   public void divideOperationsEquals() {
      buildOperand(Number.ONE);
      buildOperand(Number.TWO);

      executeSimpleOperation(new Divide());
      executeSimpleOperation(new Divide());
      executeSimpleOperation(new Divide());
      executeSimpleOperation(new Divide());
      executeSimpleOperation(new Divide());

      equals();

      checkHistory("", 0);
      checkResult("1");
   }

   /**
    * Pattern:
    *    <code>N + N(eq*n)</code>
    * Must be:
    *    <code>(N + N)n</code>
    */
   @Test
   public void plusOperationMultiEquals(){
      buildOperand(Number.ONE);

      executeSimpleOperation(new Plus());

      buildOperand(Number.THREE);

      equals();
      equals();
      equals();

      checkHistory("", 0);
      checkResult("10");
   }

   @Test
   public void minusOperationMultiEquals() {
      buildOperand(Number.TWO);
      buildOperand(Number.EIGHT);
      buildOperand(Number.NINE);

      executeSimpleOperation(new Minus());

      buildOperand(Number.ONE);
      buildOperand(Number.ZERO);
      buildOperand(Number.TWO);

      equals();
      equals();
      equals();

      checkHistory("", 0);
      checkResult("-17");
   }

   @Test
   public void multiplyOperationMultiEquals() {
      buildOperand(Number.TWO);

      executeSimpleOperation(new Multiply());

      buildOperand(Number.THREE);

      equals();
      equals();
      equals();

      checkHistory("", 0);
      checkResult("54");
   }

   @Test
   public void divideOperationMultiEquals() {
      buildOperand(Number.ONE);
      buildOperand(Number.EIGHT);
      buildOperand(Number.EIGHT);

      executeSimpleOperation(new Divide());

      buildOperand(Number.TWO);

      equals();
      equals();
      equals();

      checkHistory("", 0);
      checkResult("23.5");
   }

   /**
    * Pattern:
    *    <code>N + N eq +</code>
    * Must be:
    *    <code>N + N eq</code>
    */
   @Test
   public void operationIsTheLast(){
      buildOperand(Number.TWO);

      executeSimpleOperation(new Plus());

      buildOperand(Number.THREE);

      equals();

      checkResult("5");

      executeSimpleOperation(new Plus());

      checkHistory("5 + ", 2);
      checkResult("5");
   }

   /**
    * Pattern:
    *    <code>N + N eq +-*\/</code>
    * Must be:
    *    <code>N + N eq</code>
    * but in a history: <code>RESULT / </code>
    */
   @Test
   public void manyOperationIsTheLast(){
      buildOperand(Number.TWO);
      buildOperand(Number.THREE);
      buildOperand(Number.NINE);

      executeSimpleOperation(new Multiply());

      buildOperand(Number.FOUR);

      equals();

      checkResult("956");

      executeSimpleOperation(new Plus());
      executeSimpleOperation(new Minus());
      executeSimpleOperation(new Multiply());
      executeSimpleOperation(new Divide());

      checkHistory("956 / ", 2);
      checkResult("956");
   }

   /**
    * Pattern:
    *    <code>N + eq + eq + eq + eq</code>
    */
   @Test
   public void multiOperationAndEquals() {
      buildOperand(Number.ONE);
      buildOperand(Number.ZERO);

      executeSimpleOperation(new Plus());
      equals();

      checkResult("20");

      executeSimpleOperation(new Plus());
      equals();

      checkResult("40");

      executeSimpleOperation(new Plus());
      equals();

      checkHistory("", 0);
      checkResult("80");
   }

   /**
    * Pattern:
    *    <code>N + N eq (backspace)</code>
    * Backspace mustn't work after equals.
    */
   @Test
   public void backspaceAfterEquals(){
      buildOperand(Number.SEVEN);
      buildOperand(Number.ZERO);

      executeSimpleOperation(new Divide());

      buildOperand(Number.SEVEN);

      equals();
      executeBackSpace();
      executeBackSpace();
      executeBackSpace();

      checkHistory("", 0);
      checkResult("10");
   }

   /**
    * Pattern:
    *    <code>N + N eq N +</code>
    */
   @Test
   public void someNumberAfterEquals() {
      buildOperand(Number.SEVEN);

      executeSimpleOperation(new Plus());

      buildOperand(Number.THREE);

      equals();

      checkResult("10");

      buildOperand(Number.ONE);

      executeSimpleOperation(new Plus());

      checkResult("1");
   }

   /**
    * Pattern:
    *    <code>N + N eq N (eq*n)</code>
    */
   @Test
   public void someNumberAndOperationAndEqualsAfterEquals() {
      buildOperand(Number.TWO);

      executeSimpleOperation(new Plus());

      buildOperand(Number.THREE);

      equals();

      buildOperand(Number.FOUR);

      equals();
      equals();
      equals();

      checkHistory("", 0);
      checkResult("13");
   }

   /**
    * Pattern:
    *    <code>N + N% eq %</code>
    */
   @Test
   public void percentTest() {
      buildOperand(Number.TWO);
      buildOperand(Number.ZERO);
      buildOperand(Number.ZERO);

      executeSimpleOperation(new Plus());

      checkHistory("200 + ", 2);

      buildOperand(Number.TWO);

      executeSpecialOperation(new Percent());

      checkHistory("200 + 4 ", 2);

      equals();

      checkResult("204");

      executeSpecialOperation(new Percent());

      checkResult("416.16");
   }

   /**
    * Pattern:
    *    <code>N + N eq %%%</code>
    */
   @Test
   public void manyPercentAfterEquals() {
      buildOperand(Number.ONE);
      buildOperand(Number.NINE);
      buildOperand(Number.NINE);

      executeSimpleOperation(new Plus());

      buildOperand(Number.ONE);

      equals();

      checkResult("200");

      executeSpecialOperation(new Percent());

      checkResult("400");

      executeSpecialOperation(new Percent());

      checkResult("800");

      executeSpecialOperation(new Percent());

      checkResult("1600");
   }

   /**
    * Pattern:
    *    <code>N + N eq N +</code>
    */
   @Test
   public void afterEqualsNumberAndOperation() {
      buildOperand(Number.ONE);

      executeSimpleOperation(new Plus());

      buildOperand(Number.TWO);

      equals();

      checkResult("3");

      buildOperand(Number.FOUR);

      executeSimpleOperation(new Plus());

      checkHistory("4 + ", 2);
      checkResult("4");

      equals();
      equals();
      equals();

      checkResult("16");
   }

   /**
    * Pattern
    *    <code>N%</code>
    */
   @Test
   public void percentWithoutResult() {
      buildOperand(Number.TWO);

      executeSpecialOperation(new Percent());

      checkHistory("0 ", 1);
      checkResult("0");
   }

   /**
    * Pattern
    *    <code>sqrt(4)</code>
    */
   @Test
   public void sqrtWithNumber() {
      buildOperand(Number.FOUR);

      executeSpecialOperation(new SquareRoot());

      checkHistory(SQRT + "(4) ", 1);

      equals();

      checkResult("2");
   }

   @Test
   public void sqrWithNumber() {
      buildOperand(Number.EIGHT);

      executeSpecialOperation(new Square());

      checkHistory("sqr(8) ", 1);

      equals();

      checkHistory("", 0);
      checkResult("64");
   }

   @Test
   public void divideByWithNumber() {
      buildOperand(Number.TWO);

      executeSpecialOperation(new DivideBy());

      checkHistory("1/(2) ", 1);

      equals();

      checkResult("0.5");
   }

   /**
    * Group tests of that pattern:
    *    <code> + N</code>
    */
   @Test
   public void plusAndNumber() {
      executeSimpleOperation(new Plus());

      buildOperand(Number.SEVEN);

      checkHistory("0 + 7 ", 2);

      equals();

      checkResult("7");
   }

   @Test
   public void minusAndNumber() {
      executeSimpleOperation(new Minus());

      buildOperand(Number.SEVEN);

      checkHistory("0 - 7 ", 2);

      equals();

      checkResult("-7");
   }

   @Test
   public void multiplyAndNumber() {
      executeSimpleOperation(new Multiply());

      buildOperand(Number.FOUR);

      checkHistory("0 * 4 ", 2);

      equals();

      checkResult("0");
   }

   @Test
   public void divideAndNumber() {
      executeSimpleOperation(new Divide());

      buildOperand(Number.EIGHT);

      checkHistory("0 / 8 ", 2);

      equals();

      checkResult("0");
   }

   @Test
   public void manySpecialOperations() {
      buildOperand(Number.TWO);
      buildOperand(Number.ZERO);
      buildOperand(Number.ZERO);

      executeSimpleOperation(new Plus());

      buildOperand(Number.FOUR);

      executeSpecialOperation(new Percent());

      checkResult("200");

      executeSpecialOperation(new DivideBy());
      executeSpecialOperation(new DivideBy());
      executeSpecialOperation(new Square());
      executeSpecialOperation(new SquareRoot());

      checkHistory("200 + " + SQRT + "(sqr(1/(1/(8)))) ", 2);
   }

   /**
    * Group of tests where second operand is hidden.
    * Pattern
    *    <code>N + eq = N + N</code>
    */
   @Test
   public void hiddenSecondOperand() {
      buildOperand(Number.TWO);

      executeSimpleOperation(new Plus());

      equals();

      checkHistory("", 0);
      checkResult("4");
   }

   @Test
   public void hiddenSecondOperandWithNegate() {
      buildOperand(Number.FOUR);

      executeSimpleOperation(new Plus());
      executeSpecialOperation(new Negate());

      checkHistory("4 + negate(4) ", 2);

      equals();

      checkHistory("", 0);
      checkResult("0");
   }

   // TODO N + (negate) = N + -N
   // TODO clear
   // TODO do test for build operand

   private void executeSimpleOperation(SimpleOperation operation) {
      calculator.executeSimpleOperation(operation);
      calculator.showResult();
   }

   private void executeSpecialOperation(SpecialOperation operation){
      calculator.executeSpecialOperation(operation);
      calculator.showOperand();

   }

   private void executeBackSpace(){
      calculator.backspace();
   }

   private void equals() {
      calculator.equalsOperation();
      calculator.showResult();
   }

   private void checkResult(String expected){
      assertEquals(expected, calculator.getContainer().getResult().toString());
   }

   private void buildOperand(Number number){
      calculator.buildOperand(number);
      calculator.showOperand();
   }

   private void checkHistory(String expectedHistory, int size){
      History history = calculator.getContainer().getHistory();

      assertEquals(size, history.size());
      assertEquals(expectedHistory, history.buildHistory());
   }

}