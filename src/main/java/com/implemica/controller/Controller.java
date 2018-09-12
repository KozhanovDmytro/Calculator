package com.implemica.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

import com.implemica.model.Container;
import com.implemica.model.interfaces.Numeral;
import com.implemica.model.numerals.Arabic;
import com.implemica.model.numerals.numbers.Number;
import com.implemica.model.operations.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {

   @FXML
   private ResourceBundle resources;

   @FXML
   private URL location;

   @FXML
   private Button c, ce, backSpace;

   @FXML
   private Button equalsOperation;

   @FXML
   private Button plusOperation, minusOperation, multiplyOperation, divideOperation;

   @FXML
   private Button percentOperation, sqrtOperation, square, divideByX;

   @FXML
   private Button negate, separateBtn, btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;

   @FXML
   private Label resultLabel;

   @FXML
   private Label historyLabel;

   private Container container = new Container();

   private Numeral numeral = new Arabic();

   @FXML
   void initialize() {
      // TODO this must be at another method.
      container.setMadeOperand(true);

      actionsForBuildOperand();
      actionsForOperationButtons();
      actionsForCleanOperations();
   }

   private void actionsForOperationButtons() {

      plusOperation.setOnAction(event -> actionForOperations(new Plus()));
      minusOperation.setOnAction(event -> actionForOperations(new Minus()));
      multiplyOperation.setOnAction(event -> actionForOperations(new Multiply()));
      divideOperation.setOnAction(event -> actionForOperations(new Divide()));

      equalsOperation.setOnAction(event -> {
         if (container.isMadeOperand()) {
            container.getOperation().setOperand(new BigDecimal(container.showNumberForSystem(resultLabel.getText())));
         } else {
            if (container.getOperation() instanceof Equals) {
               Equals eq = (Equals) container.getOperation();
               container.setResult(new BigDecimal(container.showNumberForSystem(resultLabel.getText())));
               eq.getLastOperation().setOperand(eq.getLastOperation().getOperand());
            }
         }
         container.calculate();
         showResult();
         Operation equals = new Equals(container.getOperation());
         container.getHistory().clear();
         updateHistory();
         container.setOperation(equals);
         container.getHistory().add(equals);

         container.setMadeOperand(false);
      });
   }

   private void actionForOperations(Operation operation) {
      if (!(container.getOperation() instanceof Equals))
         container.calculate();
      showResult();
      updateHistory();
      container.setOperation(operation);
      container.setMadeOperand(true);
   }

   private void actionsForBuildOperand() {
      btn0.setOnAction(event -> actionForBuildOperand(Number.ZERO));
      btn1.setOnAction(event -> actionForBuildOperand(Number.ONE));
      btn2.setOnAction(event -> actionForBuildOperand(Number.TWO));
      btn3.setOnAction(event -> actionForBuildOperand(Number.THREE));
      btn4.setOnAction(event -> actionForBuildOperand(Number.FOUR));
      btn5.setOnAction(event -> actionForBuildOperand(Number.FIVE));
      btn6.setOnAction(event -> actionForBuildOperand(Number.SIX));
      btn7.setOnAction(event -> actionForBuildOperand(Number.SEVEN));
      btn8.setOnAction(event -> actionForBuildOperand(Number.EIGHT));
      btn9.setOnAction(event -> actionForBuildOperand(Number.NINE));

      negate.setOnAction(event -> {
         if (container.isMadeOperand()) {
            container.getOperation().negateOperand();
            showOperand();
         } else {
            container.negateResult();
            showResult();
            updateHistory();
         }
      });

      separateBtn.setOnAction(event -> {
         container.getOperation().setSeparated(true);

         showOperand();
      });

   }

   private void actionForBuildOperand(Number number) {
      if (container.isMadeOperand() || container.getOperation() instanceof Equals) {
         container.getOperation().buildOperand(numeral.translate(number));
         showOperand();
      }
   }

   private void actionsForCleanOperations() {
      backSpace.setOnAction(event -> {
         if (container.isMadeOperand()) {
            container.getOperation().removeLast();
            showOperand();
         }
      });

      c.setOnAction(event -> {
         container.getHistory().clear();
         container.setResult(BigDecimal.ZERO);
         container.setOperation(new Default());
         showOperand();
      });

      ce.setOnAction(event -> {
         container.getOperation().setOperand(BigDecimal.ZERO);
         showOperand();
      });
   }

   private void showResult() {
      resultLabel.setText(container.showComfortableNumber(container.getResult()));
   }

   private void showOperand() {
      resultLabel.setText(container.showComfortableNumber(container.getOperation().getOperand()));
   }

   private void updateHistory() {
      historyLabel.setText(container.getHistory().toString());
   }


}

