package com.implemica.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

import com.implemica.model.Container;
import com.implemica.model.interfaces.Numeral;
import com.implemica.model.numerals.Arabic;
import com.implemica.model.numerals.numbers.Number;
import com.implemica.model.operations.*;
import com.implemica.model.operations.simple.Divide;
import com.implemica.model.operations.simple.Minus;
import com.implemica.model.operations.simple.Multiply;
import com.implemica.model.operations.simple.Plus;
import com.implemica.model.operations.special.DivideBy;
import com.implemica.model.operations.special.Percent;
import com.implemica.model.operations.special.Square;
import com.implemica.model.operations.special.SquareRoot;
import com.implemica.model.validation.Validator;
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

   private Validator validator = new Validator();

   private Numeral numeral = new Arabic();

   @FXML
   void initialize() {
      actionsForBuildOperand();
      actionsForOperationButtons();
      actionsForCleanOperations();
   }

   private void actionsForOperationButtons() {
      plusOperation.setOnAction(event -> actionForOperations(new Plus()));
      minusOperation.setOnAction(event -> actionForOperations(new Minus()));
      multiplyOperation.setOnAction(event -> actionForOperations(new Multiply()));
      divideOperation.setOnAction(event -> actionForOperations(new Divide()));

      percentOperation.setOnAction(event -> {
         container.getOperation().setOperand(new Percent().calculate(container.getResult(), container.getOperation().getOperand()));

         showOperand();
      });

      sqrtOperation.setOnAction(event -> {
         if(container.isMadeOperand()){
            container.getOperation().setOperand(new SquareRoot().calculate(container.getOperation().getOperand()));
         } else {
            container.setResult(new SquareRoot().calculate(container.getResult()));
         }

         showResult();
      });

      square.setOnAction(event -> {
         if(container.isMadeOperand()){
            container.getOperation().setOperand(new Square().calculate(container.getOperation().getOperand()));
            showOperand();
         } else {
            container.setResult(new Square().calculate(container.getResult()));
            showResult();
         }
      });

      divideByX.setOnAction(event -> {
         if(container.isMadeOperand()){
            container.getOperation().setOperand(new DivideBy().calculate(container.getOperation().getOperand()));
            showOperand();
         } else {
            container.setResult(new DivideBy().calculate(container.getResult()));
            showResult();
         }
      });

      equalsOperation.setOnAction(event -> {
         if (container.isMadeOperand()) {
            container.getOperation().setOperand(new BigDecimal(validator.showNumberForSystem(resultLabel.getText())));
         } else {
            if (container.getOperation() instanceof Equals) {
               Equals eq = (Equals) container.getOperation();
               container.setResult(new BigDecimal(validator.showNumberForSystem(resultLabel.getText())));
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
      container.setMadeOperand(true);

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
         // TODO this.
         /*if (container.isMadeOperand()) {
            container.getOperation().negateOperand();
            showOperand();
         } else {
            container.negateResult();
            showResult();
            updateHistory();
         }*/
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
         container.setMadeOperand(true);

         showOperand();
      });
      ce.setOnAction(event -> {
         container.getOperation().setOperand(BigDecimal.ZERO);
         showOperand();
      });
   }

   private void showResult() {
      resultLabel.setText(validator.showNumber(container.getResult()));
   }

   private void showOperand() {
      resultLabel.setText(validator.showNumber(container.getOperation().getOperand(), container.getOperation().isSeparated()));
   }

   private void updateHistory() {
      historyLabel.setText(container.getHistory().toString());
   }


}

