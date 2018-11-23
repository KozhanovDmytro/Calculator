package com.implemica.controller;

import com.implemica.controller.util.Field;
import com.implemica.model.calculator.Calculator;
import com.implemica.model.dto.ResponseDto;
import com.implemica.model.operations.operation.SpecialOperation;
import com.implemica.model.operations.operation.Number;
import com.implemica.model.operations.operation.SimpleOperation;
import com.implemica.model.operations.simple.Divide;
import com.implemica.model.operations.simple.Minus;
import com.implemica.model.operations.simple.Multiply;
import com.implemica.model.operations.simple.Plus;
import com.implemica.model.operations.special.*;
import com.implemica.model.validation.Validator;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;

import java.io.*;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.Properties;

public class Controller {

   private static final String PATH_TO_ENGLISH_TEXTS = "/com/implemica/view/resources/properties/text_En.properties";

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

   private Calculator calculator = new Calculator();

   private Validator validator = new Validator();

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
      File file = new File(getClass().getResource(PATH_TO_ENGLISH_TEXTS).toURI());

      InputStream stream = new FileInputStream(file);

      textsForLabel.load(stream);
      stream.close();
   }

   private void setTexts() {
      // labels
      setText(title, Field.TITLE);
      setText(mode, Field.MODE);

      // buttons in menu
      setText(standard, Field.STANDARD);
      setText(scientific, Field.SCIENTIFIC);
      setText(programmer, Field.PROGRAMMER);
      setText(dateCalculation, Field.DATE_CALCULATION);
      setText(currency, Field.CURRENCY);
      setText(volume, Field.VOLUME);
      setText(length, Field.LENGTH);
      setText(weight, Field.WEIGHT);
      setText(temperature, Field.TEMPERATURE);
      setText(energy, Field.ENERGY);
      setText(area, Field.AREA);
      setText(speed, Field.SPEED);
      setText(time, Field.TIME);
      setText(power, Field.POWER);
      setText(data, Field.DATA);
      setText(pressure, Field.PRESSURE);
      setText(angle, Field.ANGLE);
      setText(about, Field.ABOUT);

      // labels in menu
      setText(menuConverter, Field.MENU_CONVERTER);
      setText(menuCalculator, Field.MENU_CALCULATOR);

      // extra field
      setText(extraMemoryLabel, Field.NO_MEMORY);
      setText(extraLogLabel, Field.NO_LOG);
      setText(memoryBtn, Field.MEMORY);
      setText(logBtn, Field.HISTORY);
   }
   
   private void setText(Labeled node, Field field) {
      node.setText(getText(field));
   }
   

   private void actionsForOperationButtons() {
      plusOperation     .setOnAction(event -> actionForOperations(new Plus()));
      minusOperation    .setOnAction(event -> actionForOperations(new Minus()));
      multiplyOperation .setOnAction(event -> actionForOperations(new Multiply()));
      divideOperation   .setOnAction(event -> actionForOperations(new Divide()));

      percentOperation  .setOnAction(event -> actionForSpecialOperations(new Percent()));
      sqrtOperation     .setOnAction(event -> actionForSpecialOperations(new SquareRoot()));
      square            .setOnAction(event -> actionForSpecialOperations(new Square()));
      divideByX         .setOnAction(event -> actionForSpecialOperations(new DivideBy()));
      negate            .setOnAction(event -> actionForSpecialOperations(new Negate()));

      equalsOperation.setOnAction(event -> {
         parseDto(calculator.equalsOperation());
         unlock();
      });
   }

   private void actionForOperations(SimpleOperation operation) {
      parseDto(calculator.executeSimpleOperation(operation));
   }

   private void actionForSpecialOperations(SpecialOperation operation) {
      parseDto(calculator.executeSpecialOperation(operation));
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
         parseBuiltOperand(response);
      });
   }

   private void actionForBuildOperand(Number number) {
      parseBuiltOperand(calculator.buildOperand(number));
      unlock();
   }

   private void actionsForCleanOperations() {
      backSpace.setOnAction(event -> {
         parseBuiltOperand(calculator.backspace());
         unlock();
      });
      clear.setOnAction(event -> {
         parseDto(calculator.clear());
         unlock();
      });
      clearEntry.setOnAction(event -> {
         parseDto(calculator.clearEntry());
         unlock();
      });
   }

   private void parseBuiltOperand(ResponseDto response) {
      if (response.getOperand() != null) {
         showResult(validator.builtOperand(response.getOperand(), response.isSeparated()));
      }
   }

   private void actionsForMemory() {
      addMemory.setOnAction(event -> disableMemory(false, validator.showNumber(calculator.addMemory())));

      subtractMemory.setOnAction(event -> disableMemory(false, validator.showNumber(calculator.subtractMemory())));

      recallMemory.setOnAction((event -> parseDto(calculator.getMemory())));

      clearMemory.setOnAction(event -> disableMemory(true, "0"));
   }

   private void disableMemory(boolean disable, String memory) {
      if (disable) {
         BigDecimal memoryValue = calculator.clearMemory();
         memoryLabel.setText(validator.showNumber(memoryValue));
         extraMemoryLabel.setText(getText(Field.NO_MEMORY));
         clearMemory.setDisable(true);
         recallMemory.setDisable(true);
         showMemory.setDisable(true);
      } else {
         memoryLabel.setText(memory);
         extraMemoryLabel.setText(memory);
         clearMemory.setDisable(false);
         recallMemory.setDisable(false);
         showMemory.setDisable(false);
      }
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

   private void parseDto(ResponseDto response) {
      boolean isThrownException = false;
      switch (response.getExceptionType()) {
         case OVERFLOW:
            showResult(getText(Field.OVERFLOW));
            blockButtons(true);
            isThrownException = true;
            break;
         case UNDEFINED_RESULT:
            showResult(getText(Field.UNDEFINED_RESULT));
            blockButtons(true);
            isThrownException = true;
            break;
         case DIVIDE_BY_ZERO:
            showResult(getText(Field.DIVIDE_BY_ZERO));
            blockButtons(true);
            isThrownException = true;
            break;
         case INVALID_INPUT:
            showResult(getText(Field.INVALID_INPUT));
            blockButtons(true);
            isThrownException = true;
            break;
      }

      if (response.getHistory() != null) {
         showHistory(response.getHistory().buildHistory());
      }

      if (!isThrownException) {
         updateData(response);
      }
   }

   private void updateData(ResponseDto response) {
      if (response.getResult() != null) {
         showResult(validator.showNumber(response.getResult()));
      }

      if (response.getOperand() != null) {
         showResult(validator.showNumber(response.getOperand().stripTrailingZeros()));
      }

      if (response.isSeparated()) {
         resultLabel.setText(resultLabel.getText() + validator.SEPARATOR);
      }
   }

   private void unlock() {
      if (isBlocked) {
         blockButtons(false);
         parseDto(calculator.getCurrentState());
      }
   }

   private String getText(Field field) {
      return textsForLabel.getProperty(field.getName());
   }
}