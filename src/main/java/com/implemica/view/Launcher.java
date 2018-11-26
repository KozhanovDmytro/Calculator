package com.implemica.view;

import com.implemica.view.util.Coordinates;
import com.implemica.view.util.NodesFinder;
import com.implemica.view.util.Side;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class represents view side.
 *
 * @see Stream
 * @see Collectors
 *
 * @see Stage
 * @see Parent
 * @see Node
 * @see Button
 * @see Label
 * @see Pane
 *
 * @see Coordinates
 * @see NodesFinder
 * @see Side
 *
 * @author Dmytro Kozhanov
 */
public class Launcher extends Application {

   /** Current stage. */
   private Stage primaryStage;

   /** The point where drag was started. */
   private Point2D startDrag;

   /** Current position of stage. */
   private Point2D stagePosition;

   /** The flag indicates whether the window has full screen or not. */
   private boolean isFullScreen;

   /** The flag indicates whether the window resizing or not. */
   private boolean isDragging;

   /** The flag indicates whether menu shown or not. */
   private boolean isMenuShown;

   /** The flag indicates whether memory shown or not. */
   private boolean isMemoryShown;

   /** The container of nodes. */
   private Parent root;

   /*constants*/
   /** The way to root fxml file. */
   private final String WAY_TO_ROOT_FXML_FILE = "/com/implemica/view/resources/fxml/root.fxml";
   
   /** The way to stylesheet file. */
   private final String WAY_TO_STYLESHEET_FILE = "/com/implemica/view/resources/css/style.css";

   /** The way to icon. */
   private final String WAY_TO_ICON = "/com/implemica/view/resources/icons/icon.png";

   /** Min width of stage. */
   private final int MIN_WIDTH = 322;

   /** Min height of stage. */
   private final int MIN_HEIGHT = 500;

   /** Offset for resize font size when stage is changing. */
   private final int OFFSET_RESIZE_FOR_RESULT = 15;

   /** The max length where result label can do resize. */
   private final int MAX_LENGTH_FOR_RESULT = 13;

   /** Max font size for result label. */
   private final double MAX_FONT_SIZE_FOR_RESULT = 48.0d;

   /** Just zero. */
   private final double ZERO = 0.0d;

   /** Min size stage when extra info field can be appear. */
   private final int MIN_SIZE_STAGE_FOR_EXTRA_INFO = 560;

   /** Min size for extra info field. */
   private final int MIN_SIZE_FOR_EXTRA_INFO = 240;

   /** Max size for extra info field. */
   private final int MAX_SIZE_FOR_EXTRA_INFO = 325;

   /** Full screen icon. */
   private final String FULL_SCREEN_ICON = "\uE922";

   /** Small screen icon. */
   private final String SMALL_SCREEN_ICON = "\uE923";

   /** The name of stylesheet for selection button which is in extraInfo field. */
   private final String STYLE_FOR_BUTTON_IN_EXTRA_FIELD = "ExtraInfoBtnSelected";

   /** Width for menu. */
   private final double MENU_SIZE = 260.0d;

   /** Height for memory field.  */
   private final double MEMORY_FIELD_SIZE = 300.0d;

   /** Animation time for memory and menu field.  */
   private final int ANIMATION_TIME = 50;

   /** Min font size for number buttons. */
   private final int MIN_FONT_SIZE_FOR_NUMBER_BUTTONS = 24;

   /** Max font size for number buttons. */
   private final int MAX_FONT_SIZE_FOR_NUMBER_BUTTONS = 28;

   /** Min font size for other buttons. */
   private final int MIN_FONT_SIZE_FOR_OTHER_BUTTONS = 15;

   /** Max font size for other buttons. */
   private final int MAX_FONT_SIZE_FOR_OTHER_BUTTONS = 21;
   
   /** Start slow menu animation. */
   private final double START_SLOW_MENU_ANIMATION = 40;

   /** End slow menu animation. */
   private final double END_SLOW_MENU_ANIMATION = 300;

   /**
    * Launch application.
    *
    * @param stage current stage.
    * @throws Exception if fxml not found.
    */
   @Override public void start(Stage stage) throws Exception {
      setSettingsForStage(stage);

      setMoveActionForWindow();
      setActionsForMainPainAndButtons();
      setResizeActionForStage();
      setActionsForInfoButtons();

      setActionForResultLabel();
      setActionsForKeyboard();

      setActionForDropDownMenu();

      primaryStage.show();
   }

   /**
    * Sets settings for current stage.
    * @param stage current stage.
    * @throws Exception if fxml not found.
    */
   private void setSettingsForStage(Stage stage) throws Exception {
      primaryStage = stage;

      FXMLLoader loader = new FXMLLoader(getClass().getResource(WAY_TO_ROOT_FXML_FILE));

      root = loader.load();
      root.getStylesheets().add(WAY_TO_STYLESHEET_FILE);
      Scene scene = new Scene(root, MIN_WIDTH, MIN_HEIGHT);

      primaryStage.initStyle(StageStyle.UNDECORATED);
      primaryStage.setScene(scene);

      primaryStage.setMinHeight(MIN_HEIGHT);
      primaryStage.setMinWidth(MIN_WIDTH);

      primaryStage.setMaxWidth(Screen.getPrimary().getBounds().getMaxX());
      primaryStage.setMaxHeight(Screen.getPrimary().getBounds().getMaxY());

      primaryStage.getIcons().add(new Image(WAY_TO_ICON));

      setUserAgentStylesheet(STYLESHEET_CASPIAN);
   }

   /**
    * Sets action for resize result label.
    */
   private void setActionForResultLabel() {
      Label resultLabel = findBy(NodesFinder.RESULT_LABEL);
      HBox resultLabelBox = findBy(NodesFinder.RESULT_LABEL_BOX);

      resultLabelBox.widthProperty()
              .addListener((observable, oldValue, newValue) -> calculateSizeForResultLabel());
      resultLabel.textProperty()
              .addListener((observable, oldValue, newValue) -> calculateSizeForResultLabel());
   }

   /**
    * Action for resize label.
    */
   private void calculateSizeForResultLabel() {
      Label resultLabel = findBy(NodesFinder.RESULT_LABEL);
      HBox resultLabelBox = findBy(NodesFinder.RESULT_LABEL_BOX);

      double fontSize = resultLabel.getFont().getSize();

      Text text = new Text(resultLabel.getText());
      text.setFont(resultLabel.getFont());

      double width = text.getBoundsInLocal().getWidth() + OFFSET_RESIZE_FOR_RESULT;

      if (width > resultLabelBox.getWidth() || resultLabel.getText().length() >= MAX_LENGTH_FOR_RESULT) {
         fontSize = fontSize * resultLabelBox.getWidth() / (width);
      } else {
         fontSize = MAX_FONT_SIZE_FOR_RESULT;
      }

      if (fontSize <= MAX_FONT_SIZE_FOR_RESULT) {
         resultLabel.setFont(new Font(resultLabel.getFont().getName(), fontSize));
      }
   }

   /**
    * Sets action for moving current window.
    */
   private void setMoveActionForWindow() {
      AnchorPane mainPane = findBy(NodesFinder.MAIN_PANE);
      mainPane.setOnMouseDragged(event -> {
         if (!isDragging && !isFullScreen) {
            primaryStage.setX(primaryStage.getX() + (event.getScreenX() - startDrag.getX()));
            primaryStage.setY(primaryStage.getY() + (event.getScreenY() - startDrag.getY()));

            startDrag = new Point2D(event.getScreenX(), event.getScreenY());
         }
      });

      mainPane.setOnMousePressed(event -> {
         startDrag = new Point2D(event.getScreenX(), event.getScreenY());
         if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
            setFullScreen();
         }
      });
   }

   /**
    * set action for system buttons.
    */
   private void setActionsForMainPainAndButtons() {
      Button close = findBy(NodesFinder.CLOSE);
      Button full  = findBy(NodesFinder.FULL);
      Button hide  = findBy(NodesFinder.HIDE);

      close.setOnMouseClicked(event -> {
         menuSize(false);
         memorySize(false);
         primaryStage.close();
      });
      hide.setOnMouseClicked(event -> {
         menuSize(false);
         memorySize(false);
         primaryStage.setIconified(!primaryStage.isIconified());
      });
      full.setOnMouseClicked(event -> {
         menuSize(false);
         memorySize(false);
         setFullScreen();
      });
   }

   /**
    * Sets actions for resize window for resize panes.
    */
   private void setResizeActionForStage() {
      AnchorPane extraInfoFull = findBy(NodesFinder.EXTRA_INFO_FULL);

      Pane left         = findBy(NodesFinder.LEFT_RESIZE);
      Pane extraLeft    = findBy(NodesFinder.EXTRA_LEFT_RESIZE);
      Pane right        = findBy(NodesFinder.RIGHT_RESIZE);
      Pane extraRight   = findBy(NodesFinder.EXTRA_RIGHT_RESIZE);
      Pane top          = findBy(NodesFinder.TOP_RESIZE);
      Pane bottom       = findBy(NodesFinder.BOTTOM_RESIZE);

      Pane leftBottom   = findBy(NodesFinder.LEFT_BOTTOM_RESIZE);
      Pane rightBottom  = findBy(NodesFinder.RIGHT_BOTTOM_RESIZE);
      Pane leftTop      = findBy(NodesFinder.LEFT_TOP_RESIZE);
      Pane rightTop     = findBy(NodesFinder.RIGHT_TOP_RESIZE);

      Stream.of(left, extraLeft, right, extraRight, top, bottom, leftBottom,
              rightBottom, leftTop, rightTop)
              .collect(Collectors.toList())
              .forEach(pane -> {
                 pane.setOnMousePressed(event -> {
                    calculateSizeForResultLabel();
                    isDragging = true;
                    startDrag = new Point2D(event.getScreenX(), event.getScreenY());
                 });

                 pane.setOnMouseDragged(event -> doResize(pane, event));
                 pane.setOnMouseReleased(event -> {
                    calculateSizeForResultLabel();
                    isDragging = false;
                 });
              });

      primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
         if (newValue.intValue() >= MIN_SIZE_STAGE_FOR_EXTRA_INFO && !isFullScreen) {
            double plusPx = newValue.intValue() - MIN_SIZE_STAGE_FOR_EXTRA_INFO;

            double calculatedSize = MIN_SIZE_FOR_EXTRA_INFO + plusPx * 0.5;
            if (calculatedSize <= MAX_SIZE_FOR_EXTRA_INFO) {
               extraInfoFull.setPrefWidth(calculatedSize);
            }
         } else if (extraInfoFull.getWidth() != ZERO) {
            extraInfoFull.setPrefWidth(ZERO);
         }
      });

      extraInfoFull.widthProperty().addListener((observable, oldValue, newValue) -> {
         Button showMemory = findBy(NodesFinder.SHOW_MEMORY);
         Button logButton = findBy(NodesFinder.LOG_BUTTON);

         if (extraInfoFull.getPrefWidth() == ZERO) {
            showMemory.setVisible(true);
            logButton.setVisible(true);
         } else {
            showMemory.setVisible(false);
            logButton.setVisible(false);
         }
      });
   }

   /**
    * function calculate size for stage.
    * @param pane the pane which used for make resize.
    * @param event mouse event
    */
   private void doResize(Pane pane, MouseEvent event) {

      Side side = NodesFinder.findByQuery(pane.getId()).getSide();

      double width = primaryStage.getWidth() + (event.getScreenX() - startDrag.getX()) * side.coefficient(Coordinates.X);
      double height = primaryStage.getHeight() + (event.getScreenY() - startDrag.getY()) * side.coefficient(Coordinates.Y);

      if (primaryStage.getMinWidth() <= width && width <= primaryStage.getMaxWidth()) {
         if (side == Side.LEFT || side == Side.LEFT_TOP || side == Side.LEFT_BOTTOM) {
            primaryStage.setX(primaryStage.getX() + (event.getScreenX() - startDrag.getX()));
         }
         primaryStage.setWidth(width);
         startDrag = new Point2D(event.getScreenX(), startDrag.getY());
      }

      if (primaryStage.getMinHeight() <= height && height <= primaryStage.getMaxHeight()) {
         if (side == Side.TOP || side == Side.LEFT_TOP || side == Side.RIGHT_TOP) {
            primaryStage.setY(primaryStage.getY() + (event.getScreenY() - startDrag.getY()));
         }
         primaryStage.setHeight(height);
         startDrag = new Point2D(startDrag.getX(), event.getScreenY());
      }
   }

   /**
    * Sets full screen for window.
    */
   private void setFullScreen() {
      Button full = findBy(NodesFinder.FULL);

      Pane left         = findBy(NodesFinder.LEFT_RESIZE);
      Pane extraLeft    = findBy(NodesFinder.EXTRA_LEFT_RESIZE);
      Pane right        = findBy(NodesFinder.RIGHT_RESIZE);
      Pane extraRight   = findBy(NodesFinder.EXTRA_RIGHT_RESIZE);
      Pane top          = findBy(NodesFinder.TOP_RESIZE);
      Pane bottom       = findBy(NodesFinder.BOTTOM_RESIZE);

      Pane leftBottom         = findBy(NodesFinder.LEFT_BOTTOM_RESIZE);
      Pane rightBottom        = findBy(NodesFinder.RIGHT_BOTTOM_RESIZE);
      Pane leftTop            = findBy(NodesFinder.LEFT_TOP_RESIZE);
      Pane rightTop           = findBy(NodesFinder.RIGHT_TOP_RESIZE);
      Pane bottomResizeFull   = findBy(NodesFinder.BOTTOM_RESIZE);

      Button btn0 = findBy(NodesFinder.BTN0);
      Button btn1 = findBy(NodesFinder.BTN1);
      Button btn2 = findBy(NodesFinder.BTN2);
      Button btn3 = findBy(NodesFinder.BTN3);
      Button btn4 = findBy(NodesFinder.BTN4);
      Button btn5 = findBy(NodesFinder.BTN5);
      Button btn6 = findBy(NodesFinder.BTN6);
      Button btn7 = findBy(NodesFinder.BTN7);
      Button btn8 = findBy(NodesFinder.BTN8);
      Button btn9 = findBy(NodesFinder.BTN9);

      Button negate     = findBy(NodesFinder.NEGATE);
      Button separate   = findBy(NodesFinder.SEPARATE_BTN);
      Button backspace  = findBy(NodesFinder.BACKSPACE);
      Button clear      = findBy(NodesFinder.C);
      Button clearEntry = findBy(NodesFinder.CE);

      Button equals     = findBy(NodesFinder.EQUALS_OPERATION);
      Button plus       = findBy(NodesFinder.PLUS_OPERATION);
      Button minus      = findBy(NodesFinder.MINUS_OPERATION);
      Button multiply   = findBy(NodesFinder.MULTIPLY_OPERATION);
      Button divide     = findBy(NodesFinder.DIVIDE_OPERATION);


      Button divideBy   = findBy(NodesFinder.DIVIDE_BY_X);
      Button square     = findBy(NodesFinder.SQUARE);
      Button sqrt       = findBy(NodesFinder.SQRT_OPERATION);
      Button percent    = findBy(NodesFinder.PERCENT_OPERATION);

      int fontSizeForNumberBtn;
      int fontSizeForOtherBtn;

      if (isFullScreen) {
         primaryStage.setX(stagePosition.getX());
         primaryStage.setY(stagePosition.getY());
         primaryStage.setWidth(primaryStage.getMinWidth());
         primaryStage.setHeight(primaryStage.getMinHeight());

         Stream.of(leftTop, extraLeft, left, leftBottom, bottom, rightBottom,
                 right, extraRight, rightTop, top, bottomResizeFull)
                 .collect(Collectors.toList())
                 .forEach(pane -> pane.setDisable(false));

         full.setText(FULL_SCREEN_ICON);

         fontSizeForNumberBtn = MIN_FONT_SIZE_FOR_NUMBER_BUTTONS;
         fontSizeForOtherBtn = MIN_FONT_SIZE_FOR_OTHER_BUTTONS;

         isFullScreen = false;
      } else {
         Screen screen = Screen.getPrimary();
         Rectangle2D bounds = screen.getVisualBounds();

         stagePosition = new Point2D(primaryStage.getX(), primaryStage.getY());

         ((AnchorPane) findBy(NodesFinder.EXTRA_INFO_FULL)).setPrefWidth(MAX_SIZE_FOR_EXTRA_INFO);
         primaryStage.setX(bounds.getMinX());
         primaryStage.setY(bounds.getMinY());
         primaryStage.setWidth(bounds.getWidth());
         primaryStage.setHeight(bounds.getHeight());

         Stream.of(leftTop, extraLeft, left, leftBottom, bottom, rightBottom,
                 right, extraRight, rightTop, top, bottomResizeFull)
                 .collect(Collectors.toList())
                 .forEach(pane -> pane.setDisable(true));

         fontSizeForNumberBtn = MAX_FONT_SIZE_FOR_NUMBER_BUTTONS;
         fontSizeForOtherBtn = MAX_FONT_SIZE_FOR_OTHER_BUTTONS;

         full.setText(SMALL_SCREEN_ICON);

         isFullScreen = true;
      }

      Stream.of(btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9)
              .collect(Collectors.toList())
              .forEach(button -> button.setStyle(String.format("-fx-font-size: %dpx;", fontSizeForNumberBtn)));

      Stream.of(negate, separate, equals, plus, minus, multiply, divide, backspace,
              clear, clearEntry, divideBy, square, sqrt, percent)
              .collect(Collectors.toList())
              .forEach(button -> button.setStyle(String.format("-fx-font-size: %dpx;", fontSizeForOtherBtn)));
   }

   /**
    * Sets actions for buttons in extra field.
    */
   private void setActionsForInfoButtons() {
      Button logBtn  = findBy(NodesFinder.LOG_BTN);
      Pane logSelect = findBy(NodesFinder.LOG_SELECT);

      Button memoryBtn  = findBy(NodesFinder.MEMORY_BTN);
      Pane memorySelect = findBy(NodesFinder.MEMORY_SELECT);

      Label extraLogLabel     = findBy(NodesFinder.EXTRA_LOG_LABEL);
      Label extraMemoryLabel  = findBy(NodesFinder.EXTRA_MEMORY_LABEL);
      extraMemoryLabel.setVisible(false);
      extraLogLabel.setVisible(true);

      memoryBtn.setOnMouseClicked(event -> {
         logSelect.getStyleClass().clear();
         memorySelect.getStyleClass().add(STYLE_FOR_BUTTON_IN_EXTRA_FIELD);
         extraMemoryLabel.setVisible(true);
         extraLogLabel.setVisible(false);
      });

      logBtn.setOnMouseClicked(event -> {
         memorySelect.getStyleClass().clear();
         logSelect.getStyleClass().add(STYLE_FOR_BUTTON_IN_EXTRA_FIELD);
         extraMemoryLabel.setVisible(false);
         extraLogLabel.setVisible(true);
      });
   }

   /**
    * Sets actions for keyboard.
    */
   private void setActionsForKeyboard() {
      primaryStage.getScene().setOnKeyPressed(key -> {
         if (key.getCode() == KeyCode.DIGIT5 && key.isShiftDown()) {
            ((Button) findBy(NodesFinder.PERCENT_OPERATION)).fire();
            return;
         }
         if (key.getCode() == KeyCode.DIGIT2 && key.isShiftDown()) {
            ((Button) findBy(NodesFinder.SQRT_OPERATION)).fire();
            return;
         }
         if (key.getCode() == KeyCode.Q && !key.isShiftDown()) {
            ((Button) findBy(NodesFinder.SQUARE)).fire();
         }
         if (key.getCode() == KeyCode.R && !key.isShiftDown()) {
            ((Button) findBy(NodesFinder.DIVIDE_BY_X)).fire();
         }
         if (key.getCode() == KeyCode.DIGIT0 && !key.isShiftDown()) {
            ((Button) findBy(NodesFinder.BTN0)).fire();
         }
         if (key.getCode() == KeyCode.DIGIT1 && !key.isShiftDown()) {
            ((Button) findBy(NodesFinder.BTN1)).fire();
         }
         if (key.getCode() == KeyCode.DIGIT2 && !key.isShiftDown()) {
            ((Button) findBy(NodesFinder.BTN2)).fire();
         }
         if (key.getCode() == KeyCode.DIGIT3 && !key.isShiftDown()) {
            ((Button) findBy(NodesFinder.BTN3)).fire();
         }
         if (key.getCode() == KeyCode.DIGIT4 && !key.isShiftDown()) {
            ((Button) findBy(NodesFinder.BTN4)).fire();
         }
         if (key.getCode() == KeyCode.DIGIT5 && !key.isShiftDown()) {
            ((Button) findBy(NodesFinder.BTN5)).fire();
         }
         if (key.getCode() == KeyCode.DIGIT6 && !key.isShiftDown()) {
            ((Button) findBy(NodesFinder.BTN6)).fire();
         }
         if (key.getCode() == KeyCode.DIGIT7 && !key.isShiftDown()) {
            ((Button) findBy(NodesFinder.BTN7)).fire();
         }
         if (key.getCode() == KeyCode.DIGIT8 && !key.isShiftDown()) {
            ((Button) findBy(NodesFinder.BTN8)).fire();
         }
         if (key.getCode() == KeyCode.DIGIT9 && !key.isShiftDown()) {
            ((Button) findBy(NodesFinder.BTN9)).fire();
         }
         if (key.getCode() == KeyCode.BACK_SPACE && !key.isShiftDown()) {
            ((Button) findBy(NodesFinder.BACKSPACE)).fire();
         }
         if (key.getCode() == KeyCode.COMMA) {
            ((Button) findBy(NodesFinder.SEPARATE_BTN)).fire();
         }
         if (key.getCode() == KeyCode.F9 && !key.isShiftDown()) {
            ((Button) findBy(NodesFinder.NEGATE)).fire();
         }
         if (key.getCode() == KeyCode.EQUALS && !key.isShiftDown()) {
            ((Button) findBy(NodesFinder.EQUALS_OPERATION)).fire();
         }
         if (key.getCode() == KeyCode.PLUS || (key.getCode() == KeyCode.EQUALS && key.isShiftDown())) {
            ((Button) findBy(NodesFinder.PLUS_OPERATION)).fire();
         }
         if (key.getCode() == KeyCode.MINUS) {
            ((Button) findBy(NodesFinder.MINUS_OPERATION)).fire();
         }
         if (key.getCode() == KeyCode.DIVIDE || key.getCode() == KeyCode.SLASH) {
            ((Button) findBy(NodesFinder.DIVIDE_OPERATION)).fire();
         }
         if (key.getCode() == KeyCode.MULTIPLY || (key.getCode() == KeyCode.DIGIT8 && key.isShiftDown())) {
            ((Button) findBy(NodesFinder.MULTIPLY_OPERATION)).fire();
         }
         if (key.getCode() == KeyCode.ESCAPE && !key.isShiftDown()) {
            ((Button) findBy(NodesFinder.C)).fire();
         }
         if (key.getCode() == KeyCode.DELETE && !key.isShiftDown()) {
            ((Button) findBy(NodesFinder.CE)).fire();
         }
      });
   }

   /**
    * Sets actions for menu.
    */
   private void setActionForDropDownMenu() {
      Button menuBtn       = findBy(NodesFinder.MENU_BTN);
      AnchorPane mainPane  = findBy(NodesFinder.MAIN_PANE);
      Pane hideMenu        = findBy(NodesFinder.HIDE_MENU);
      Pane hideMemoryField = findBy(NodesFinder.HIDE_MEMORY_FIELD);

      Button showMemory = findBy(NodesFinder.SHOW_MEMORY);

      hideMenu.setVisible(false);
      hideMemoryField.setVisible(false);

      mainPane.setOnMouseClicked(event -> {
         memorySize(false);
         menuSize(false);
      });
      hideMenu.setOnMouseClicked(event -> menuSize(false));
      hideMemoryField.setOnMouseClicked(event -> memorySize(false));

      menuBtn.setOnMouseClicked(event -> menuSize(!isMenuShown));
      showMemory.setOnMouseClicked(event -> memorySize(!isMemoryShown));
   }

   /**
    * Sets animation for menu.
    *
    * @param show flag which indicates show menu or not.
    */
   private void menuSize(boolean show) {
      AnchorPane menu = findBy(NodesFinder.MENU);

      Pane hideMenu = findBy(NodesFinder.HIDE_MENU);

      KeyFrame key1;
      KeyFrame key2;
      KeyFrame key3;

      if (show) {
         key1 = new KeyFrame(Duration.ZERO, new KeyValue(menu.translateXProperty(), -MENU_SIZE));
         key2 = new KeyFrame(new Duration(START_SLOW_MENU_ANIMATION), new KeyValue(menu.translateXProperty(), -ANIMATION_TIME));
         key3 = new KeyFrame(new Duration(END_SLOW_MENU_ANIMATION), new KeyValue(menu.translateXProperty(), ZERO));
         isMenuShown = true;
      } else {
         key1 = new KeyFrame(Duration.ZERO, new KeyValue(menu.translateXProperty(), ZERO));
         key2 = new KeyFrame(new Duration(START_SLOW_MENU_ANIMATION), new KeyValue(menu.translateXProperty(), ZERO));
         key3 = new KeyFrame(new Duration(END_SLOW_MENU_ANIMATION), new KeyValue(menu.translateXProperty(), -MENU_SIZE));
         isMenuShown = false;
      }

      Timeline timeline = new Timeline(key1, key2, key3);
      hideMenu.setVisible(isMenuShown);
      timeline.play();
      menu.setVisible(show);
   }

   /** Sets height for menu. */
   private void memorySize(boolean show) {
      AnchorPane memoryField = findBy(NodesFinder.MEMORY_FIELD);
      TranslateTransition transition = new TranslateTransition(Duration.millis(ANIMATION_TIME), memoryField);
      Pane hideMemoryField = findBy(NodesFinder.HIDE_MEMORY_FIELD);

      if (show) {
         transition.setToY(ZERO);
         isMemoryShown = true;
      } else {
         transition.setToY(MEMORY_FIELD_SIZE);
         isMemoryShown = false;
      }

      hideMemoryField.setVisible(isMemoryShown);
      transition.play();
      memoryField.setVisible(show);
   }

   /**
    * Finder for nodes.
    * @param desiredNode desire node.
    * @param <T> type of Node.
    * @return type of Node.
    */
   private <T extends Node> T findBy(NodesFinder desiredNode) {
      return (T) root.lookup(desiredNode.getName());
   }
}