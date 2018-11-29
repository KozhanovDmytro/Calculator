package com.implemica.controller;

import com.implemica.controller.util.HistoryParser;
import com.implemica.controller.util.Validator;
import com.implemica.model.calculator.Calculator;
import com.implemica.model.dto.ResponseDto;
import com.implemica.model.exceptions.CalculatorException;
import com.implemica.model.history.History;
import com.implemica.model.operations.operation.Number;
import com.implemica.model.operations.operation.SimpleOperation;
import com.implemica.model.operations.operation.SpecialOperation;
import com.implemica.model.operations.simple.Divide;
import com.implemica.model.operations.simple.Minus;
import com.implemica.model.operations.simple.Multiply;
import com.implemica.model.operations.simple.Plus;
import com.implemica.model.operations.special.*;
import com.implemica.controller.util.Node;
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
   @FXML private Button percentOperation, sqrtOperation, squareOperation, divideByX;

   /** Group of {@link Button} which have permission to build operand. */
   @FXML private Button negateOperation, separateBtn, btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;

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

   private HistoryParser historyParser = new HistoryParser();

   /** The flag which note current state of some buttons. */
   private boolean isBlocked;

   /* special */
   /** Percent operation. */
   private static Percent percent = new Percent();

   /** SquareRoot operation. */
   private static SquareRoot squareRoot = new SquareRoot();

   /** Square operation. */
   private static Square square = new Square();

   /** DivideBy operation. */
   private static DivideBy divideBy = new DivideBy();

   /** Negate operation. */
   private static Negate negate = new Negate();

   static {

      percent.setFirstPartHistory("");
      percent.setSecondPartHistory("");

      squareRoot.setFirstPartHistory("√(");
      squareRoot.setSecondPartHistory(")");

      square.setFirstPartHistory("sqr(");
      square.setSecondPartHistory(")");

      divideBy.setFirstPartHistory("1/(");
      divideBy.setSecondPartHistory(")");

      negate.setFirstPartHistory("negate(");
      negate.setSecondPartHistory(")");
   }

   /**
    *  This interface contains a function where an exception expect.
    *  That's needed for catching one exception in different place.
    */
   private interface ExceptionSupplier {
      ResponseDto calculate() throws CalculatorException;
   }

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
      setText(title, Node.TITLE);
      setText(mode, Node.MODE);

      // buttons in menu
      setText(standard, Node.STANDARD);
      setText(scientific, Node.SCIENTIFIC);
      setText(programmer, Node.PROGRAMMER);
      setText(dateCalculation, Node.DATE_CALCULATION);
      setText(currency, Node.CURRENCY);
      setText(volume, Node.VOLUME);
      setText(length, Node.LENGTH);
      setText(weight, Node.WEIGHT);
      setText(temperature, Node.TEMPERATURE);
      setText(energy, Node.ENERGY);
      setText(area, Node.AREA);
      setText(speed, Node.SPEED);
      setText(time, Node.TIME);
      setText(power, Node.POWER);
      setText(data, Node.DATA);
      setText(pressure, Node.PRESSURE);
      setText(angle, Node.ANGLE);
      setText(about, Node.ABOUT);

      // labels in menu
      setText(menuConverter, Node.MENU_CONVERTER);
      setText(menuCalculator, Node.MENU_CALCULATOR);

      // extra field
      setText(extraMemoryLabel, Node.NO_MEMORY);
      setText(extraLogLabel, Node.NO_LOG);
      setText(memoryBtn, Node.MEMORY);
      setText(logBtn, Node.HISTORY);
   }

   /**
    * Give to current node his text.
    *
    * @param labeled  current node.
    * @param nodesFinder enumeration for find current text in property.
    */
   private void setText(Labeled labeled, Node nodesFinder) {
      labeled.setText(getText(nodesFinder));
   }

   /**
    * Give permission to operation button.
    */
   private void actionsForOperationButtons() {
      plusOperation     .setOnAction(event -> actionForOperations(plusFactory()));
      minusOperation    .setOnAction(event -> actionForOperations(minusFactory()));
      multiplyOperation .setOnAction(event -> actionForOperations(multiplyFactory()));
      divideOperation   .setOnAction(event -> actionForOperations(divideFactory()));

      percentOperation  .setOnAction(event -> actionForSpecialOperations(percent));
      sqrtOperation     .setOnAction(event -> actionForSpecialOperations(squareRoot));
      squareOperation   .setOnAction(event -> actionForSpecialOperations(square));
      divideByX         .setOnAction(event -> actionForSpecialOperations(divideBy));
      negateOperation   .setOnAction(event -> actionForSpecialOperations(negate));

      equalsOperation.setOnAction(event -> {
         unlock();
         parseDto(() -> calculator.equalsOperation());
      });
   }

   /**
    * Execute simple operation and update current state of model.
    *
    * @param operation simple operation
    */
   private void actionForOperations(SimpleOperation operation) {
      parseDto(() -> calculator.executeSimpleOperation(operation));
   }

   /**
    * Execute special operation and update current state of model.
    *
    * @param operation special operation
    */
   private void actionForSpecialOperations(SpecialOperation operation) {
      parseDto(() -> calculator.executeSpecialOperation(operation));
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
         parseDto(() -> calculator.clear());
         unlock();
      });
      clearEntry.setOnAction(event -> {
         parseDto(() -> calculator.clearEntry());
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

      recallMemory.setOnAction((event -> parseDto(() -> calculator.getMemory())));

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
         extraMemoryLabel.setText(getText(Node.NO_MEMORY));
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
      squareOperation.setDisable(isThrownException);
      divideByX.setDisable(isThrownException);
      divideOperation.setDisable(isThrownException);
      multiplyOperation.setDisable(isThrownException);
      minusOperation.setDisable(isThrownException);
      plusOperation.setDisable(isThrownException);
      separateBtn.setDisable(isThrownException);
      negateOperation.setDisable(isThrownException);
   }

   /**
    * Function parse DTO and check if exception was thrown.
    *
    * @param supplier for supply an exception.
    */
   private void parseDto(ExceptionSupplier supplier) {
      ResponseDto response = new ResponseDto();
      try{
         response = supplier.calculate();
         updateData(response);
      } catch(CalculatorException e) {
         switch (e.getExceptionType()) {
            case OVERFLOW:
               showResult(getText(Node.OVERFLOW));
               break;
            case UNDEFINED_RESULT:
               showResult(getText(Node.UNDEFINED_RESULT));
               break;
            case DIVIDE_BY_ZERO:
               showResult(getText(Node.DIVIDE_BY_ZERO));
               break;
            case INVALID_INPUT:
               showResult(getText(Node.INVALID_INPUT));
               break;
         }
         blockButtons(true);
         History history = calculator.getCurrentState().getHistory();
         showHistory(historyParser.parse(history));
      }

      if (response.getHistory() != null) {
         showHistory(historyParser.parse(response.getHistory()));
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
         parseDto(() -> calculator.getCurrentState());
      }
   }

   /**
    * Return the text for special field.
    *
    * @param node special field.
    */
   private String getText(Node node) {
      return textsForLabel.getProperty(node.getTextFromProperty());
   }

   /**
    * Factory for plus operation.
    *
    * @see SimpleOperation
    *
    * @return instance of {@link Plus}
    */
   private Plus plusFactory() {
      Plus plus = new Plus();
      plus.setCharacter("+");

      return plus;
   }

   /**
    * Factory for minus operation.
    *
    * @see SimpleOperation
    *
    * @return instance of {@link Minus}
    */
   private Minus minusFactory() {
      Minus minus = new Minus();
      minus.setCharacter("-");

      return minus;
   }

   /**
    * Factory for multiply operation.
    *
    * @see SimpleOperation
    *
    * @return instance of {@link Multiply}
    */
   private Multiply multiplyFactory() {
      Multiply multiply = new Multiply();
      multiply.setCharacter("×");

      return multiply;
   }

   /**
    * Factory for divide operation.
    *
    * @see SimpleOperation
    *
    * @return instance of {@link Divide}
    */
   private Divide divideFactory() {
      Divide divide = new Divide();
      divide.setCharacter("÷");

      return divide;
   }
}