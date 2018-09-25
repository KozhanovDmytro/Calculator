package com.implemica.model.calculator;

import com.implemica.model.interfaces.History;
import com.implemica.model.numerals.Arabic;
import com.implemica.model.numerals.numbers.Number;
import com.implemica.model.operations.Default;
import com.implemica.model.operations.SimpleOperation;
import com.implemica.model.operations.simple.Divide;
import com.implemica.model.operations.simple.Minus;
import com.implemica.model.operations.simple.Multiply;
import com.implemica.model.operations.simple.Plus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

   private Calculator calculator;

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

      executeSimpleOperation(new Plus(), "3");
      executeSimpleOperation(new Plus(), "3");
      executeSimpleOperation(new Plus(), "3");
      executeSimpleOperation(new Plus(), "3");
      executeSimpleOperation(new Plus(), "3");

      checkHistory("3 + ", 2);
      checkResult("3");
   }

   @Test
   public void manyMinusOperations() {
      buildOperand(Number.FIVE);

      executeSimpleOperation(new Minus(), "5");
      executeSimpleOperation(new Minus(), "5");
      executeSimpleOperation(new Minus(), "5");
      executeSimpleOperation(new Minus(), "5");
      executeSimpleOperation(new Minus(), "5");

      checkHistory("5 - ", 2);
      checkResult("5");
   }

   @Test
   public void manyMultiplyOperations() {
      buildOperand(Number.EIGHT);

      executeSimpleOperation(new Multiply(), "8");
      executeSimpleOperation(new Multiply(), "8");
      executeSimpleOperation(new Multiply(), "8");
      executeSimpleOperation(new Multiply(), "8");
      executeSimpleOperation(new Multiply(), "8");

      checkHistory("8 * ", 2);
      checkResult("8");
   }

   @Test
   public void manyDivideOperations() {
      buildOperand(Number.ONE);
      buildOperand(Number.TWO);

      executeSimpleOperation(new Divide(), "12");
      executeSimpleOperation(new Divide(), "12");
      executeSimpleOperation(new Divide(), "12");
      executeSimpleOperation(new Divide(), "12");

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

      executeSimpleOperation(new Plus(), "2");
      executeSimpleOperation(new Plus(), "2");
      executeSimpleOperation(new Plus(), "2");
      executeSimpleOperation(new Plus(), "2");
      executeSimpleOperation(new Plus(), "2");

      equals();

      checkHistory("", 0);
      checkResult("4");
   }

   @Test
   public void minusOperationsEquals() {
      buildOperand(Number.FOUR);

      executeSimpleOperation(new Minus(), "4");
      executeSimpleOperation(new Minus(), "4");
      executeSimpleOperation(new Minus(), "4");
      executeSimpleOperation(new Minus(), "4");
      executeSimpleOperation(new Minus(), "4");

      equals();

      checkHistory("", 0);
      checkResult("0");
   }

   @Test
   public void multiplyOperationsEquals() {
      buildOperand(Number.FOUR);
      buildOperand(Number.FIVE);

      executeSimpleOperation(new Multiply(), "45");
      executeSimpleOperation(new Multiply(), "45");
      executeSimpleOperation(new Multiply(), "45");
      executeSimpleOperation(new Multiply(), "45");

      equals();

      checkHistory("", 0);
      checkResult("2025");
   }

   @Test
   public void divideOperationsEquals() {
      buildOperand(Number.ONE);
      buildOperand(Number.TWO);

      executeSimpleOperation(new Divide(), "12");
      executeSimpleOperation(new Divide(), "12");
      executeSimpleOperation(new Divide(), "12");
      executeSimpleOperation(new Divide(), "12");
      executeSimpleOperation(new Divide(), "12");

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

      executeSimpleOperation(new Plus(), "1");

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

      executeSimpleOperation(new Minus(), "289");

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

      executeSimpleOperation(new Multiply(), "2");

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

      executeSimpleOperation(new Divide(), "188");

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

      executeSimpleOperation(new Plus(), "2");

      buildOperand(Number.THREE);

      equals();

      executeSimpleOperation(new Plus(), "5");

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

      executeSimpleOperation(new Multiply(), "239");

      buildOperand(Number.FOUR);

      equals();

      executeSimpleOperation(new Plus(), "956");
      executeSimpleOperation(new Minus(), "956");
      executeSimpleOperation(new Multiply(), "956");
      executeSimpleOperation(new Divide(), "956");

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

      executeSimpleOperation(new Plus(), "10");
      equals();

      executeSimpleOperation(new Plus(), "20");
      equals();

      executeSimpleOperation(new Plus(), "40");
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

      executeSimpleOperation(new Divide(), "70");

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

      executeSimpleOperation(new Plus(), "7");

      buildOperand(Number.THREE);

      equals();

      buildOperand(Number.ONE);

      executeSimpleOperation(new Plus(), "10");

      checkResult("10");
   }

   private void executeSimpleOperation(SimpleOperation operation, String comfortableResult) {
      calculator.executeSimpleOperation(operation);
      assertEquals(comfortableResult, calculator.showResult());
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