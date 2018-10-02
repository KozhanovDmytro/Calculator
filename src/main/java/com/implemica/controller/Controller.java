package com.implemica.controller;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

import com.implemica.model.calculator.Calculator;
import com.implemica.model.numerals.Arabic;
import com.implemica.model.numerals.numbers.Number;
import com.implemica.model.operations.*;
import com.implemica.model.operations.simple.Divide;
import com.implemica.model.operations.simple.Minus;
import com.implemica.model.operations.simple.Multiply;
import com.implemica.model.operations.simple.Plus;
import com.implemica.model.operations.special.*;
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

   private Properties texts = new Properties();

   private Calculator calculator = new Calculator(new Arabic());

   @FXML
   void initialize() {
      actionsForBuildOperand();
      actionsForOperationButtons();
      actionsForCleanOperations();

      executeProperties();
   }

   private void executeProperties(){
      File f = new File("/properties/text_En.properties");
      System.out.println(f.getAbsolutePath());
      try {
         File file = new File(getClass().getResource("/properties/text_En.properties").toURI());

         InputStream stream = new FileInputStream(file);

         texts.load(stream);
         // TODO load into labels
      } catch (IOException e) {
         e.printStackTrace();
      } catch (URISyntaxException e) {
         e.printStackTrace();
      }
   }

   private void actionsForOperationButtons() {
      plusOperation.setOnAction(event -> actionForOperations(new Plus()));
      minusOperation.setOnAction(event -> actionForOperations(new Minus()));
      multiplyOperation.setOnAction(event -> actionForOperations(new Multiply()));
      divideOperation.setOnAction(event -> actionForOperations(new Divide()));


      percentOperation.setOnAction(event -> {
         calculator.executeSpecialOperation(new Percent());

         showOperand();
         updateHistory();
      });

      sqrtOperation.setOnAction(event -> {
         calculator.executeSpecialOperation(new SquareRoot());

         showOperand();
         updateHistory();
      });

      square.setOnAction(event -> {
         calculator.executeSpecialOperation(new Square());

         showOperand();
         updateHistory();
      });

      divideByX.setOnAction(event -> {
         calculator.executeSpecialOperation(new DivideBy());

         showOperand();
         updateHistory();
      });

      equalsOperation.setOnAction(event -> {
         calculator.equalsOperation();

         showResult();
         updateHistory();
      });
   }

   private void actionForOperations(SimpleOperation operation) {
      calculator.executeSimpleOperation(operation);

      showResult();
      updateHistory();
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
         calculator.executeSpecialOperation(new Negate());

         showOperand();
         updateHistory();
      });

      separateBtn.setOnAction(event -> {
         calculator.separateOperand();
         showOperand();
      });
   }

   private void actionForBuildOperand(Number number) {
      calculator.buildOperand(number);
      showBuiltOperand();
   }

   private void actionsForCleanOperations() {
      backSpace.setOnAction(event -> {
         if(calculator.backspace()){
            showOperand();
         }
      });
      c.setOnAction(event -> {
         calculator.clear();

         showOperand();
         updateHistory();
      });
      ce.setOnAction(event -> {
         calculator.clearEntry();
         showOperand();
      });
   }

   private void showResult() {
      resultLabel.setText(calculator.showResult());
   }

   private void showOperand() {
      resultLabel.setText(calculator.showOperand());
   }

   private void showBuiltOperand() {
      resultLabel.setText(calculator.showBuiltOperand());
   }

   private void updateHistory() {
      historyLabel.setText(calculator.showHistory());
   }
}