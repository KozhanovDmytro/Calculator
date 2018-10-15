package com.implemica.controller;

import com.implemica.model.calculator.Calculator;
import com.implemica.model.dto.ResponseDto;
import com.implemica.model.interfaces.SpecialOperation;
import com.implemica.model.numerals.Arabic;
import com.implemica.model.numerals.numbers.Number;
import com.implemica.model.operations.SimpleOperation;
import com.implemica.model.operations.simple.Divide;
import com.implemica.model.operations.simple.Minus;
import com.implemica.model.operations.simple.Multiply;
import com.implemica.model.operations.simple.Plus;
import com.implemica.model.operations.special.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

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
   private Button clearMemory, recallMemory, addMemory, subtractMemory, showMemory, memoryBtn, logBtn;

   @FXML
   private Button currency, volume, length, weight, temperature, energy,
           area, speed, time, power, data, pressure, angle, standard,
            scientific, programmer, dateCalculation, about;

   @FXML
   private Label menuConverter, menuCalculator;

   @FXML
   private Label resultLabel, memoryLabel, historyLabel;

   @FXML
   private Label extraLogLabel, extraMemoryLabel;

   @FXML
   private Label title;

   @FXML
   private Label mode;

   private Properties texts = new Properties();

   private Calculator calculator = new Calculator(new Arabic());

   @FXML
   void initialize() throws IOException, URISyntaxException {
      actionsForBuildOperand();
      actionsForOperationButtons();
      actionsForCleanOperations();
      actionsForMemory();

      executeProperties();
      setTexts();
   }

   private void executeProperties() throws IOException, URISyntaxException {
      File file = new File(getClass().getResource("/properties/text_En.properties").toURI());

      InputStream stream = new FileInputStream(file);

      texts.load(stream);
      stream.close();
   }

   private void setTexts() {
      title.setText(texts.getProperty("title"));
      mode.setText(texts.getProperty("mode"));

      standard.setText(texts.getProperty("standard"));
      scientific.setText(texts.getProperty("scientific"));
      programmer.setText(texts.getProperty("programmer"));
      dateCalculation.setText(texts.getProperty("dateCalculation"));
      currency.setText(texts.getProperty("currency"));
      volume.setText(texts.getProperty("volume"));
      length.setText(texts.getProperty("length"));
      weight.setText(texts.getProperty("weight"));
      temperature.setText(texts.getProperty("temperature"));
      energy.setText(texts.getProperty("energy"));
      area.setText(texts.getProperty("area"));
      speed.setText(texts.getProperty("speed"));
      time.setText(texts.getProperty("time"));
      power.setText(texts.getProperty("power"));
      data.setText(texts.getProperty("data"));
      pressure.setText(texts.getProperty("pressure"));
      angle.setText(texts.getProperty("angle"));

      about.setText(texts.getProperty("about"));

      menuConverter.setText(texts.getProperty("menuConverter"));
      menuCalculator.setText(texts.getProperty("menuCalculator"));

      extraMemoryLabel.setText(texts.getProperty("noMemory"));
      extraLogLabel.setText(texts.getProperty("noLog"));
      memoryBtn.setText(texts.getProperty("memory"));
      logBtn.setText(texts.getProperty("history"));
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
         if(disassembleDto(response)) {
            showResult(response.getResult());
            updateHistory(response.getHistory());
         }
      });
   }

   private void actionForOperations(SimpleOperation operation) {
      ResponseDto response = calculator.executeSimpleOperation(operation);
      if(disassembleDto(response)) {
         showResult(response.getResult());
         updateHistory(response.getHistory());
      }
   }

   private void actionForSpecialOperations(SpecialOperation operation) {
      ResponseDto response = calculator.executeSpecialOperation(operation);

      if(disassembleDto(response)){
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

   private void actionsForMemory() {
      addMemory.setOnAction((event)->{
         String result = calculator.addMemory();
         memoryLabel.setText(result);
         extraMemoryLabel.setText(result);
         clearMemory.setDisable(false);
         recallMemory.setDisable(false);
         showMemory.setDisable(false);
      });

      subtractMemory.setOnAction((event)->{
         String result = calculator.subtractMemory();
         memoryLabel.setText(result);
         extraMemoryLabel.setText(result);
         clearMemory.setDisable(false);
         recallMemory.setDisable(false);
         showMemory.setDisable(false);
      });

      recallMemory.setOnAction((event -> showResult(calculator.getMemory().getOperand())));

      clearMemory.setOnAction((event -> {
         calculator.getContainer().getMemory().clear();
         memoryLabel.setText("0");
         extraMemoryLabel.setText(texts.getProperty("noMemory"));
         clearMemory.setDisable(true);
         recallMemory.setDisable(true);
         showMemory.setDisable(true);
      }));
   }

   private void showResult(String result) {
      resultLabel.setText(result);
   }

   private void updateHistory(String history) {
      historyLabel.setText(history);
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

   private boolean disassembleDto(ResponseDto response) {
      switch (response.getExceptionType()) {
         case OVERFLOW:
            showResult(texts.getProperty("overflow"));
            blockButtons(true);
            return false;
         case UNDEFINED_RESULT:
            showResult(texts.getProperty("undefinedResult"));
            blockButtons(true);
            return false;
         case DIVIDE_BY_ZERO:
            showResult(texts.getProperty("divideByZero"));
            blockButtons(true);
            return false;
         case INVALID_INPUT:
            showResult(texts.getProperty("invalidInput"));
            blockButtons(true);
            return false;
      }
      return true;
   }
}