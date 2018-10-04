package com.implemica.controller;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

import com.implemica.model.calculator.Calculator;
import com.implemica.model.dto.ResponseDto;
import com.implemica.model.interfaces.SpecialOperation;
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

      percentOperation.setOnAction(event -> actionForSpecialOperations(new Percent()));
      sqrtOperation.setOnAction(event -> actionForSpecialOperations(new SquareRoot()));
      square.setOnAction(event -> actionForSpecialOperations(new Square()));
      divideByX.setOnAction(event -> actionForSpecialOperations(new DivideBy()));
      negate.setOnAction(event -> actionForSpecialOperations(new Negate()));

      equalsOperation.setOnAction(event -> {
         ResponseDto response = calculator.equalsOperation();
         if(!disassembleDto(response)) {
            showResult(response.getResult());
            updateHistory(response.getHistory());
         }
      });
   }

   private void actionForOperations(SimpleOperation operation) {
      ResponseDto response = calculator.executeSimpleOperation(operation);
      if(!disassembleDto(response)) {
         showResult(response.getResult());
         updateHistory(response.getHistory());
      }
   }

   private void actionForSpecialOperations(SpecialOperation operation) {
      ResponseDto response = calculator.executeSpecialOperation(operation);

      if(!disassembleDto(response)){
         showResult(response.getOperand());
         updateHistory(response.getHistory());
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

      separateBtn.setOnAction(event -> {
         ResponseDto response = calculator.separateOperand();
         showResult(response.getOperand());
      });
   }

   private void actionForBuildOperand(Number number) {
      ResponseDto response = calculator.buildOperand(number);
      showResult(response.getBuildOperand());
   }

   private void actionsForCleanOperations() {
      backSpace.setOnAction(event -> {
         ResponseDto response = calculator.backspace();
         showResult(response.getBuildOperand());
      });
      c.setOnAction(event -> {
         ResponseDto response = calculator.clear();
         showResult(response.getOperand());
         updateHistory(response.getHistory());
      });
      ce.setOnAction(event -> {
         ResponseDto response = calculator.clearEntry();
         showResult(response.getOperand());
         updateHistory(response.getHistory());
      });
   }

   private void showResult(String result) {
      resultLabel.setText(result);
   }

   private void updateHistory(String history) {
      historyLabel.setText(history);
   }

   private void blockButtons(boolean isThrownException) {
      this.isThrownException = isThrownException;

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

   private boolean disassembleDto(ResponseDto response) {
      boolean result = false;
      switch (response.getExceptionType()) {
         case OVERFLOW:
            showResult(texts.getProperty("overflow"));
            blockButtons(true);
            result = true;
            break;
         case UNDEFINED_RESULT:
            showResult(texts.getProperty("undefinedResult"));
            blockButtons(true);
            result = true;
            break;
      }
      return result;
   }
}