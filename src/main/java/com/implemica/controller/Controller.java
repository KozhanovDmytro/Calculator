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
   private Button clear, clearEntry, backSpace;

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

   private Properties textsForLabel = new Properties();

   private Calculator calculator = new Calculator(new Arabic());

   private boolean isBlocked;

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

      textsForLabel.load(stream);
      stream.close();
   }

   private void setTexts() {
      title.setText(textsForLabel.getProperty("title"));
      mode.setText(textsForLabel.getProperty("mode"));

      standard.setText(textsForLabel.getProperty("standard"));
      scientific.setText(textsForLabel.getProperty("scientific"));
      programmer.setText(textsForLabel.getProperty("programmer"));
      dateCalculation.setText(textsForLabel.getProperty("dateCalculation"));
      currency.setText(textsForLabel.getProperty("currency"));
      volume.setText(textsForLabel.getProperty("volume"));
      length.setText(textsForLabel.getProperty("length"));
      weight.setText(textsForLabel.getProperty("weight"));
      temperature.setText(textsForLabel.getProperty("temperature"));
      energy.setText(textsForLabel.getProperty("energy"));
      area.setText(textsForLabel.getProperty("area"));
      speed.setText(textsForLabel.getProperty("speed"));
      time.setText(textsForLabel.getProperty("time"));
      power.setText(textsForLabel.getProperty("power"));
      data.setText(textsForLabel.getProperty("data"));
      pressure.setText(textsForLabel.getProperty("pressure"));
      angle.setText(textsForLabel.getProperty("angle"));

      about.setText(textsForLabel.getProperty("about"));

      menuConverter.setText(textsForLabel.getProperty("menuConverter"));
      menuCalculator.setText(textsForLabel.getProperty("menuCalculator"));

      extraMemoryLabel.setText(textsForLabel.getProperty("noMemory"));
      extraLogLabel.setText(textsForLabel.getProperty("noLog"));
      memoryBtn.setText(textsForLabel.getProperty("memory"));
      logBtn.setText(textsForLabel.getProperty("history"));
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
         unlock();
         ResponseDto response = calculator.equalsOperation();
         if(disassembleDto(response)) {
            showResult(response.getResult());
         }
         showHistory(response.getHistory());
      });
   }

   private void actionForOperations(SimpleOperation operation) {
      ResponseDto response = calculator.executeSimpleOperation(operation);

      if(disassembleDto(response)) {
         showResult(response.getResult());
      }

      showHistory(response.getHistory());
   }

   private void actionForSpecialOperations(SpecialOperation operation) {
      ResponseDto response = calculator.executeSpecialOperation(operation);

      if(disassembleDto(response)){
         showResult(response.getOperand());
      }

      showHistory(response.getHistory());
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
         showResult(response.getBuildOperand());
      });
   }

   private void actionForBuildOperand(Number number) {
      unlock();
      ResponseDto response = calculator.buildOperand(number);
      showResult(response.getBuildOperand());
      if(response.getHistory() != null) {
         showHistory(response.getHistory());
      }
   }

   private void actionsForCleanOperations() {
      backSpace.setOnAction(event -> {
         unlock();
         ResponseDto response = calculator.backspace();
         showResult(response.getBuildOperand());
      });
      clear.setOnAction(event -> {
         unlock();
         ResponseDto response = calculator.clear();
         showResult(response.getOperand());
         showHistory(response.getHistory());
      });
      clearEntry.setOnAction(event -> {
         unlock();
         ResponseDto response = calculator.clearEntry();
         showResult(response.getOperand());
         showHistory(response.getHistory());
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
         extraMemoryLabel.setText(textsForLabel.getProperty("noMemory"));
         clearMemory.setDisable(true);
         recallMemory.setDisable(true);
         showMemory.setDisable(true);
      }));
   }

   private void showResult(String result) {
      resultLabel.setText(result);
   }

   private void showHistory(String history) {
      historyLabel.setText(history);
   }

   private void blockButtons(boolean isThrownException) {
      isBlocked = isThrownException;
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
            showResult(textsForLabel.getProperty("overflow"));
            blockButtons(true);
            return false;
         case UNDEFINED_RESULT:
            showResult(textsForLabel.getProperty("undefinedResult"));
            blockButtons(true);
            return false;
         case DIVIDE_BY_ZERO:
            showResult(textsForLabel.getProperty("divideByZero"));
            blockButtons(true);
            return false;
         case INVALID_INPUT:
            showResult(textsForLabel.getProperty("invalidInput"));
            blockButtons(true);
            return false;
      }
      return true;
   }

   private void unlock(){
      if(isBlocked) {
         blockButtons(false);
         ResponseDto response = calculator.getCurrentState();
         showResult(response.getResult());
         showHistory(response.getHistory());
      }
   }
}