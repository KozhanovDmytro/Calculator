package com.implemica.controller.util;

import com.implemica.view.util.NodesFinder;
import com.implemica.view.util.TestFxBase;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFxBaseBuilder extends TestFxBase {

   public void doTest(String pattern, String history, String result) {
      String[] actions = pattern.split(" ");
      for (String action : actions) {
         switch (action) {
            case "C":
               clickOnButton(NodesFinder.C);
               break;
            case "CE":
               clickOnButton(NodesFinder.CE);
               break;
            case "MC":
               clickOnButton(NodesFinder.MEMORY_CLEAR);
               break;
            case "MR":
               clickOnButton(NodesFinder.MEMORY_RECALL);
               break;
            case "M-":
               clickOnButton(NodesFinder.MEMORY_SUBTRACT);
               break;
            case "M+":
               clickOnButton(NodesFinder.MEMORY_ADD);
               break;
            default:
               checkBySymbols(action);
         }
      }

      if (history != null) {
         checkHistory(history);
      }

      if (result != null) {
         checkResult(result);
      }

      clickOnButton(NodesFinder.C);
      clickOnButton(NodesFinder.MEMORY_CLEAR);
   }
   
   private void checkBySymbols(String pattern) {
      for (char action : pattern.toCharArray()) {
         switch (action) {
            case '0':
               clickOnButton(NodesFinder.BTN0);
               break;
            case '1':
               clickOnButton(NodesFinder.BTN1);
               break;
            case '2':
               clickOnButton(NodesFinder.BTN2);
               break;
            case '3':
               clickOnButton(NodesFinder.BTN3);
               break;
            case '4':
               clickOnButton(NodesFinder.BTN4);
               break;
            case '5':
               clickOnButton(NodesFinder.BTN5);
               break;
            case '6':
               clickOnButton(NodesFinder.BTN6);
               break;
            case '7':
               clickOnButton(NodesFinder.BTN7);
               break;
            case '8':
               clickOnButton(NodesFinder.BTN8);
               break;
            case '9':
               clickOnButton(NodesFinder.BTN9);
               break;
            case '.':
               clickOnButton(NodesFinder.SEPARATE_BTN);
               break;
            case '+':
               clickOnButton(NodesFinder.PLUS_OPERATION);
               break;
            case '-':
               clickOnButton(NodesFinder.MINUS_OPERATION);
               break;
            case '×':
               clickOnButton(NodesFinder.MULTIPLY_OPERATION);
               break;
            case '÷':
               clickOnButton(NodesFinder.DIVIDE_OPERATION);
               break;
            case '=':
               clickOnButton(NodesFinder.EQUALS_OPERATION);
               break;
            case '%':
               clickOnButton(NodesFinder.PERCENT_OPERATION);
               break;
            case '√':
               clickOnButton(NodesFinder.SQRT_OPERATION);
               break;
            case 'q':
               clickOnButton(NodesFinder.SQUARE);
               break;
            case 'r':
               clickOnButton(NodesFinder.DIVIDE_BY_X);
               break;
            case '<':
               clickOnButton(NodesFinder.BACKSPACE);
               break;
            case 'n':
               clickOnButton(NodesFinder.NEGATE);
               break;
         }
      }
   }

   private void clickOnButton(NodesFinder node) {
      Button button = findBy(node.getQuery());
      clickOn(button);
   }

   private void checkHistory(String history) {
      Label historyLabel = findBy(NodesFinder.HISTORY_LABEL.getQuery());
      assertEquals(history, historyLabel.getText());
   }

   private void checkResult(String history) {
      Label resultLabel = findBy(NodesFinder.RESULT_LABEL.getQuery());
      assertEquals(history, resultLabel.getText());
   }
}
