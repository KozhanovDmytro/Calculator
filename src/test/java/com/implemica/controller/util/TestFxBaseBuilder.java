package com.implemica.controller.util;

import com.implemica.view.util.TestFxBase;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.loadui.testfx.utils.FXTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFxBaseBuilder extends TestFxBase {

   public void doTest(String pattern, String history, String result) {
      String[] actions = pattern.split(" ");
      for (String action : actions) {
         switch (action) {
            case "C":
               clickOnButton(Node.C);
               break;
            case "CE":
               clickOnButton(Node.CE);
               break;
            case "MC":
               clickOnButton(Node.MEMORY_CLEAR);
               break;
            case "MR":
               clickOnButton(Node.MEMORY_RECALL);
               break;
            case "M-":
               clickOnButton(Node.MEMORY_SUBTRACT);
               break;
            case "M+":
               clickOnButton(Node.MEMORY_ADD);
               break;
            case "SQR":
               clickOnButton(Node.SQUARE);
               break;
            case "1/x":
               clickOnButton(Node.DIVIDE_BY_X);
               break;
            default:
               checkBySymbols(action);
         }
      }

      FXTestUtils.awaitEvents();

      if (history != null) {
         checkHistory(history);
      }

      if (result != null) {
         checkResult(result);
      }

      clickOnButton(Node.C);
      clickOnButton(Node.MEMORY_CLEAR);
   }
   
   private void checkBySymbols(String pattern) {
      for (char action : pattern.toCharArray()) {
         switch (action) {
            case '0':
               clickOnButton(Node.BTN0);
               break;
            case '1':
               clickOnButton(Node.BTN1);
               break;
            case '2':
               clickOnButton(Node.BTN2);
               break;
            case '3':
               clickOnButton(Node.BTN3);
               break;
            case '4':
               clickOnButton(Node.BTN4);
               break;
            case '5':
               clickOnButton(Node.BTN5);
               break;
            case '6':
               clickOnButton(Node.BTN6);
               break;
            case '7':
               clickOnButton(Node.BTN7);
               break;
            case '8':
               clickOnButton(Node.BTN8);
               break;
            case '9':
               clickOnButton(Node.BTN9);
               break;
            case '.':
               clickOnButton(Node.SEPARATE_BTN);
               break;
            case '+':
               clickOnButton(Node.PLUS_OPERATION);
               break;
            case '-':
               clickOnButton(Node.MINUS_OPERATION);
               break;
            case '×':
               clickOnButton(Node.MULTIPLY_OPERATION);
               break;
            case '÷':
               clickOnButton(Node.DIVIDE_OPERATION);
               break;
            case '=':
               clickOnButton(Node.EQUALS_OPERATION);
               break;
            case '%':
               clickOnButton(Node.PERCENT_OPERATION);
               break;
            case '√':
               clickOnButton(Node.SQRT_OPERATION);
               break;
            case '<':
               clickOnButton(Node.BACKSPACE);
               break;
            case 'n':
               clickOnButton(Node.NEGATE);
               break;
         }
      }
   }

   private void clickOnButton(Node node) {
      Button button = findBy(node.getName());
      clickOn(button);
   }

   private void checkHistory(String history) {
      Label historyLabel = findBy(Node.HISTORY_LABEL.getName());
      assertEquals(history, historyLabel.getText());
   }

   private void checkResult(String history) {
      Label resultLabel = findBy(Node.RESULT_LABEL.getName());
      assertEquals(history, resultLabel.getText());
   }
}
