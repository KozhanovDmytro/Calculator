package com.implemica.controller;

import com.implemica.controller.util.Field;
import com.implemica.model.calculator.Calculator;
import com.implemica.model.dto.ResponseDto;
import com.implemica.model.operations.operation.Number;
import com.implemica.model.operations.operation.SimpleOperation;
import com.implemica.model.operations.operation.SpecialOperation;
import com.implemica.model.operations.simple.Divide;
import com.implemica.model.operations.simple.Minus;
import com.implemica.model.operations.simple.Multiply;
import com.implemica.model.operations.simple.Plus;
import com.implemica.model.operations.special.*;
import com.implemica.model.validation.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * Controller.
 *
 * The class that has permission to process the view side.
 *
 * @see Button
 * @see Label
 * @see Calculator
 * @see Validator
 *
 * @author Dmytro Kozhanov
 */
public class Controller {

   /** The path where a file with texts in English is. */
   private static final String PATH_TO_ENGLISH_TEXTS = "/com/implemica/view/resources/properties/text_En.properties";

   /** Group of {@link Button} which have permission to change operand and result. */
   @FXML private Button clear, clearEntry, backSpace;

   /** The {@link Button} for equals. */
   @FXML private Button equalsOperation;

   /** Group of {@link Button} which operate with {@link SimpleOperation}. */
   @FXML private Button plusOperation, minusOperation, multiplyOperation, divideOperation;

   /** Group of {@link Button} which operate with {@link SpecialOperation}. */
   @FXML private Button percentOperation, sqrtOperation, square, divideByX;

   /** Group of {@link Button} which have permission to build operand. */
   @FXML private Button negate, separateBtn, btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;

   /** Group of {@link Button} which operate with memory. */
   @FXML private Button clearMemory, recallMemory, addMemory, subtractMemory, showMemory, memoryBtn, logBtn;

   /** Group of {@link Button} where are on the menu. */
   @FXML private Button currency, volume, length, weight, temperature, energy,
           area, speed, time, power, data, pressure, angle, standard,
           scientific, programmer, dateCalculation, about;

   /** Group of {@link Label} which are on the menu.  */
   @FXML private Label menuConverter, menuCalculator;

   /** Group of {@link Label} where is displayed current state model. */
   @FXML private Label resultLabel, memoryLabel, historyLabel;

   /** Group of {@link Label} where is displayed current state model on the extra field. */
   @FXML private Label extraLogLabel, extraMemoryLabel;

   /** The title of application */
   @FXML private Label title;

   /** Displaying current mode. */
   @FXML private Label mode;

   /** Texts container. */
   private Properties textsForLabel = new Properties();

   /** The instance of model. */
   private Calculator calculator = new Calculator();

   /** The instance which can display comfortable to user number. */
   private Validator validator = new Validator();

   /** The flag which note current state of some buttons. */
   private boolean isBlocked;

   /**
    * The first point to enter to application
    */
   @FXML void initialize() throws IOException, URISyntaxException {
      actionsForBuildOperand();
      actionsForOperationButtons();
      actionsForCleanOperations();
      actionsForMemory();

      executeProperties();
      setTexts();
   }


   /**
    * Load texts from file of property.
    */
   private void executeProperties() throws IOException, URISyntaxException {
      File file = new File(getClass().getResource(PATH_TO_ENGLISH_TEXTS).toURI());

      InputStream stream = new FileInputStream(file);

      textsForLabel.load(stream);
      stream.close();
   }

   /**
    * Give each node on the view his text.
    */
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

   /**
    * Give to current node his text.
    *
    * @param node  current node.
    * @param field name of text.
    */
   private void setText(Labeled node, Field field) {
      node.setText(getText(field));
   }

   /**
    * Give permission to operation button.
    */
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

   /**
    * Execute simple operation and update current state of model.
    *
    * @param operation simple operation
    */
   private void actionForOperations(SimpleOperation operation) {
      parseDto(calculator.executeSimpleOperation(operation));
   }

   /**
    * Execute special operation and update current state of model.
    *
    * @param operation special operation
    */
   private void actionForSpecialOperations(SpecialOperation operation) {
      parseDto(calculator.executeSpecialOperation(operation));
   }

   /**
    * Give to buttons permissions for building operand.
    */
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

   /**
    * Set permission to build a special {@link Number} to operation.
    *
    * @param number number
    */
   private void actionForBuildOperand(Number number) {
      parseBuiltOperand(calculator.buildOperand(number));
      unlock();
   }

   /**
    * Set actions for clean operations.
    */
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

   /**
    * Gets current state from model and represent into a view side.
    *
    * @param response current state
    */
   private void parseBuiltOperand(ResponseDto response) {
      if (response.getOperand() != null) {
         showResult(validator.builtOperand(response.getOperand(), response.isSeparated()));
      }
   }

   /**
    * Set actions to memory buttons.
    */
   private void actionsForMemory() {
      addMemory.setOnAction(event -> disableMemory(false, validator.showNumber(calculator.addMemory())));

      subtractMemory.setOnAction(event -> disableMemory(false, validator.showNumber(calculator.subtractMemory())));

      recallMemory.setOnAction((event -> parseDto(calculator.getMemory())));

      clearMemory.setOnAction(event -> disableMemory(true, BigDecimal.ZERO.toString()));
   }

   /**
    * This function disable memory button if memory is empty.
    *
    * @param disable boolean variable - disable or not.
    * @param memory text for displaying on view side.
    */
   private void disableMemory(boolean disable, String memory) {
      if (disable) {
         BigDecimal memoryValue = calculator.clearMemory();
         memoryLabel.setText(validator.showNumber(memoryValue));
         extraMemoryLabel.setText(getText(Field.NO_MEMORY));
      } else {
         memoryLabel.setText(memory);
         extraMemoryLabel.setText(memory);
      }
      clearMemory.setDisable(disable);
      recallMemory.setDisable(disable);
      showMemory.setDisable(disable);
   }

   /**
    * Update result into {@link this#resultLabel}
    *
    * @param result text which must be displayed.
    */
   private void showResult(String result) {
      resultLabel.setText(result);
   }

   /**
    * Update history into {@link this#historyLabel}
    *
    * @param history text which must be displayed.
    */
   private void showHistory(String history) {
      historyLabel.setText(history);
   }

   /**
    * This function provides to block buttons if an exception was appear.
    *
    * @param isThrownException boolean variable - the exception was throws or not.
    */
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

   /**
    * Function parse DTO and check if exception was thrown.
    *
    * @param response - response from model.
    */
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

   /**
    * Parse the instance of {@link ResponseDto} and displaying into view side.
    *
    * @param response - response from model.
    */
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

   /**
    * Unlock buttons.
    */
   private void unlock() {
      if (isBlocked) {
         blockButtons(false);
         parseDto(calculator.getCurrentState());
      }
   }

   /**
    * Return the text for special field.
    *
    * @param field special field.
    */
   private String getText(Field field) {
      return textsForLabel.getProperty(field.getName());
   }
}