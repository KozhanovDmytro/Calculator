package com.implemica.controller;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

import com.implemica.model.calculator.Calculator;
import com.implemica.model.exceptions.OverflowException;
import com.implemica.model.exceptions.UndefinedResultException;
import com.implemica.model.numerals.Arabic;
import com.implemica.model.numerals.numbers.Number;
import com.implemica.model.operations.*;
import com.implemica.model.operations.simple.Divide;
import com.implemica.model.operations.simple.Minus;
import com.implemica.model.operations.simple.Multiply;
import com.implemica.model.operations.simple.Plus;
import com.implemica.model.operations.special.*;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import lombok.Getter;
import lombok.Setter;

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
   private Label title;

   @FXML
   private Label mode;

   @FXML
   private Label historyLabel;

   @FXML
   @Getter
   @Setter
   private Scene scene;

   private Properties texts = new Properties();

   private Calculator calculator = new Calculator(new Arabic());

   private boolean isThrownException = false;

   @FXML
   void initialize() {
      actionsForBuildOperand();
      actionsForOperationButtons();
      actionsForCleanOperations();

      executeProperties();
   }

   private void executeProperties(){
      try {
         File file = new File(getClass().getResource("/properties/text_En.properties").toURI());

         InputStream stream = new FileInputStream(file);

         texts.load(stream);

         title.setText(texts.getProperty("title"));
         mode.setText(texts.getProperty("mode"));
      } catch (IOException | URISyntaxException e) {
         e.printStackTrace();
      }
   }

   private void actionsForOperationButtons() {
      plusOperation.setOnAction(event -> actionForOperations(new Plus()));
      minusOperation.setOnAction(event -> actionForOperations(new Minus()));
      multiplyOperation.setOnAction(event -> actionForOperations(new Multiply()));
      divideOperation.setOnAction(event -> actionForOperations(new Divide()));


      percentOperation.setOnAction(event -> {
         try {
            calculator.executeSpecialOperation(new Percent());
         } catch (UndefinedResultException e) {
            System.err.println(e);
         }

         showOperand();
         updateHistory();
      });

      sqrtOperation.setOnAction(event -> {
         try {
            calculator.executeSpecialOperation(new SquareRoot());
         } catch (UndefinedResultException e) {
            System.err.println(e);
         }

         showOperand();
         updateHistory();
      });

      square.setOnAction(event -> {
         try {
            calculator.executeSpecialOperation(new Square());
         } catch (UndefinedResultException e) {
            System.err.println(e);
         }

         showOperand();
         updateHistory();
      });

      divideByX.setOnAction(event -> {
         try {
            calculator.executeSpecialOperation(new DivideBy());
         } catch (UndefinedResultException e) {
            System.err.println(e);
         }

         showOperand();
         updateHistory();
      });

      equalsOperation.setOnAction(event -> {
         if(isThrownException) {
            checkBlockedButtons();
         }
         try {
            calculator.equalsOperation();

            showResult();
         } catch (OverflowException e) {
            calculator.clear();
            resultLabel.setText(texts.getProperty("overflow"));
            isThrownException = true;
            blockButtons(true);
         } catch (UndefinedResultException e) {
            calculator.clear();
            isThrownException = true;
            blockButtons(true);
            resultLabel.setText(texts.getProperty("undefinedResult"));
         }
         updateHistory();
      });
   }

   private void checkBlockedButtons() {
      isThrownException = false;
      blockButtons(false);
      showResult();
      updateHistory();
   }
   
   private void blockButtons(boolean isThrownException) {
      percentOperation.setDisable(isThrownException);
      sqrtOperation.setDisable(isThrownException);
      square.setDisable(isThrownException);
      divideByX.setDisable(isThrownException);
      divideOperation.setDisable(isThrownException);
      multiplyOperation.setDisable(isThrownException);
      minusOperation.setDisable(isThrownException);
      plusOperation.setDisable(isThrownException);
      separateBtn.setDisable(isThrownException);
      negate.setDisable(isThrownException);
   }

   private void actionForOperations(SimpleOperation operation) {
      try {
         calculator.executeSimpleOperation(operation);

         showResult();
         updateHistory();
      } catch (OverflowException e) {
         isThrownException = true;
         blockButtons(true);
         resultLabel.setText(texts.getProperty("overflow"));
      } catch (UndefinedResultException e) {
         isThrownException = true;
         blockButtons(true);
         resultLabel.setText(texts.getProperty("undefinedResult"));
      }


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
         try {
            calculator.executeSpecialOperation(new Negate());
         } catch (UndefinedResultException e) {
            System.err.println(e);
         }

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
            showBuiltOperand();
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

   public void actionsForKeyboard() {
      scene.setOnKeyPressed(key -> {
         if (key.getCode() == KeyCode.DIGIT5 && key.isShiftDown()) {
            percentOperation.fire();
            return;
         }
         if (key.getCode() == KeyCode.DIGIT2 && key.isShiftDown()) {
            sqrtOperation.fire();
            return;
         }

         if(key.getCode() == KeyCode.Q && !key.isShiftDown()){
            square.fire();
         }
         if(key.getCode() == KeyCode.R && !key.isShiftDown()){
            divideByX.fire();
         }
         if(key.getCode() == KeyCode.DIGIT0 && !key.isShiftDown()){
            btn0.fire();
         }
         if(key.getCode() == KeyCode.DIGIT1 && !key.isShiftDown()){
            btn1.fire();
         }
         if(key.getCode() == KeyCode.DIGIT2 && !key.isShiftDown()){
            btn2.fire();
         }
         if(key.getCode() == KeyCode.DIGIT3 && !key.isShiftDown()){
            btn3.fire();
         }
         if(key.getCode() == KeyCode.DIGIT4 && !key.isShiftDown()){
            btn4.fire();
         }
         if(key.getCode() == KeyCode.DIGIT5 && !key.isShiftDown()){
            btn5.fire();
         }
         if(key.getCode() == KeyCode.DIGIT6 && !key.isShiftDown()){
            btn6.fire();
         }
         if(key.getCode() == KeyCode.DIGIT7 && !key.isShiftDown()){
            btn7.fire();
         }
         if(key.getCode() == KeyCode.DIGIT8 && !key.isShiftDown()){
            btn8.fire();
         }
         if(key.getCode() == KeyCode.DIGIT9 && !key.isShiftDown()){
            btn9.fire();
         }
         if(key.getCode() == KeyCode.BACK_SPACE && !key.isShiftDown()){
            backSpace.fire();
         }
         if(key.getCode() == KeyCode.COMMA){
            separateBtn.fire();
         }
         if(key.getCode() == KeyCode.F9 && !key.isShiftDown()){
            negate.fire();
         }
         if(key.getCode() == KeyCode.EQUALS && !key.isShiftDown()){
            equalsOperation.fire();
         }
         if(key.getCode() == KeyCode.PLUS || (key.getCode() == KeyCode.EQUALS && key.isShiftDown())){
            plusOperation.fire();
         }
         if(key.getCode() == KeyCode.MINUS){
            minusOperation.fire();
         }
         if(key.getCode() == KeyCode.DIVIDE){
            divideOperation.fire();
         }
         if(key.getCode() == KeyCode.ENTER){
            equalsOperation.fire();
         }
         if(key.getCode() == KeyCode.MULTIPLY || (key.getCode() == KeyCode.DIGIT8 && key.isShiftDown())){
            multiplyOperation.fire();
         }
         if(key.getCode() == KeyCode.ESCAPE && !key.isShiftDown()){
            c.fire();
         }
         if(key.getCode() == KeyCode.DELETE && !key.isShiftDown()) {
            ce.fire();
         }
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