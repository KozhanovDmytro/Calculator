package com.implemica.view;

import com.implemica.view.util.Coordinates;
import com.implemica.view.util.NodesFinder;
import com.implemica.view.util.Side;
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

public class Launcher extends Application {

   private Stage primaryStage;

   private Point2D startDrag;

   private Point2D stagePosition;

   private boolean isFullScreen;

   private boolean isDragging;

   private boolean isMenuShown;

   private boolean isMemoryShown;

   private Parent root;

   /*constants*/
   private final String WAY_TO_ROOT_FXML_FILE = "/fxml/root.fxml";
   private final String WAY_TO_STYLESHEET_FILE = "/css/style.css";
   private final String WAY_TO_ICON = "icons/icon.png";

   private final int MIN_WIDTH = 322;
   private final int MIN_HEIGHT = 500;

   private final int OFFSET_RESIZE_FOR_RESULT = 10;
   private final int MAX_LENGTH_FOR_RESULT = 13;
   private final double MAX_FONT_SIZE_FOR_RESULT = 48.0d;
   private final double ZERO = 0.0d;
   private final int MIN_SIZE_STAGE_FOR_EXTRA_INFO = 560;
   private final int MIN_SIZE_FOR_EXTRA_INFO = 240;
   private final int MAX_SIZE_FOR_EXTRA_INFO = 325;
   private final String FULL_SCREEN_ICON = "\uE922";
   private final String SMALL_SCREEN_ICON = "\uE923";
   private final String STYLE_FOR_BUTTON_IN_EXTRA_FIELD = "ExtraInfoBtnSelected";
   private final double MENU_SIZE = 260.0d;
   private final double MEMORY_FIELD_SIZE = 300.0d;
   private final int ANIMATION_TIME = 50;

   @Override
   public void start(Stage stage) throws Exception {
      setSettingsForStage(stage);

      setMoveActionForWindow();
      setActionsForMainPainAndButtons();
      setResizeActionForStage();
      setActionsForInfoBtns();

      setActionForResultLabel();
      setActionsForKeyboard();

      setActionForDropDownMenu();

      primaryStage.show();
   }

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

   private void setActionForResultLabel() {
      Label resultLabel = findBy(NodesFinder.RESULT_LABEL);
      HBox resultLabelBox = findBy(NodesFinder.RESULT_LABEL_BOX);

      resultLabelBox.widthProperty()
              .addListener((observable, oldValue, newValue) -> calculateSizeForResultLabel(resultLabel, resultLabelBox));
      resultLabel.textProperty()
              .addListener((observable, oldValue, newValue) -> calculateSizeForResultLabel(resultLabel, resultLabelBox));
   }

   private void calculateSizeForResultLabel(Label resultLabel, HBox resultLabelBox) {
      double fontSize = resultLabel.getFont().getSize();

      Text text = new Text(resultLabel.getText());
      text.setFont(resultLabel.getFont());

      double width = text.getBoundsInLocal().getWidth() + OFFSET_RESIZE_FOR_RESULT;

      if (width > resultLabelBox.getWidth() || resultLabel.getText().length() >= MAX_LENGTH_FOR_RESULT) {
         fontSize = fontSize * resultLabelBox.getWidth() / (width);
      } else {
         fontSize = MAX_FONT_SIZE_FOR_RESULT;
      }

      if (fontSize <= MAX_FONT_SIZE_FOR_RESULT)
         resultLabel.setFont(new Font(resultLabel.getFont().getName(), fontSize));
   }

   private void setMoveActionForWindow() {
      AnchorPane mainPane = findBy(NodesFinder.MAIN_PANE);
      mainPane.setOnMouseDragged(event -> {
         if (!isDragging && !isFullScreen) {
            primaryStage.setX(primaryStage.getX() + (event.getScreenX() - startDrag.getX()));
            primaryStage.setY(primaryStage.getY() + (event.getScreenY() - startDrag.getY()));

            startDrag = new Point2D(event.getScreenX(), event.getScreenY());
         }
      });

      mainPane.setOnMousePressed(event -> startDrag = new Point2D(event.getScreenX(), event.getScreenY()));
   }

   private void setActionsForMainPainAndButtons() {
      Button close = findBy(NodesFinder.CLOSE);
      Button full = findBy(NodesFinder.FULL);
      Button hide = findBy(NodesFinder.HIDE);

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

   private void setResizeActionForStage() {
      AnchorPane extraInfoFull = findBy(NodesFinder.EXTRA_INFO_FULL);
      HBox extraInfoBtns = findBy(NodesFinder.EXTRA_INFO_BTNS);

      Pane left = findBy(NodesFinder.LEFT_RESIZE);
      Pane extraLeft = findBy(NodesFinder.EXTRA_LEFT_RESIZE);
      Pane right = findBy(NodesFinder.RIGHT_RESIZE);
      Pane extraRight = findBy(NodesFinder.EXTRA_RIGHT_RESIZE);
      Pane top = findBy(NodesFinder.TOP_RESIZE);
      Pane bottom = findBy(NodesFinder.BOTTOM_RESIZE);

      Pane leftBottom = findBy(NodesFinder.LEFT_BOTTOM_RESIZE);
      Pane rightBottom = findBy(NodesFinder.RIGHT_BOTTOM_RESIZE);
      Pane leftTop = findBy(NodesFinder.LEFT_TOP_RESIZE);
      Pane rightTop = findBy(NodesFinder.RIGHT_TOP_RESIZE);

      Pane rightResizeFull = findBy(NodesFinder.RIGHT_RESIZE_FULL);
      Pane rightBottomResizeFull = findBy(NodesFinder.RIGHT_BOTTOM_RESIZE_FULL);
      Pane bottomResizeFull = findBy(NodesFinder.BOTTOM_RESIZE_FULL);

      Stream.of(left, extraLeft, right, extraRight, top, bottom, leftBottom,
              rightBottom, leftTop, rightTop, rightResizeFull, rightBottomResizeFull, bottomResizeFull)
              .collect(Collectors.toList())
              .forEach(pane -> {
                 pane.setOnMousePressed(event -> {
                    isDragging = true;
                    startDrag = new Point2D(event.getScreenX(), event.getScreenY());
                 });

                 pane.setOnMouseDragged(event -> doResize(pane, event));
                 pane.setOnMouseReleased(event -> isDragging = false);
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

         Stream.of(right, rightBottom).collect(Collectors.toList()).forEach(pane -> {
            if (!isFullScreen) {
               pane.setVisible(false);
               pane.setDisable(true);
            }
         });

         Stream.of(rightResizeFull, rightBottomResizeFull, bottomResizeFull).collect(Collectors.toList())
                 .forEach(pane -> {
                    if (!isFullScreen) {
                       pane.setVisible(true);
                       pane.setDisable(false);
                    }
                 });

         if (extraInfoFull.getPrefWidth() == ZERO) {
            showMemory.setVisible(true);
            logButton.setVisible(true);
         } else {
            showMemory.setVisible(false);
            logButton.setVisible(false);
         }

         extraInfoFull.setVisible(true);
         extraInfoFull.setDisable(false);

         extraInfoBtns.setVisible(true);
         extraInfoBtns.setDisable(false);
      });
   }

   private void doResize(Pane pane, MouseEvent event) {
      Side side = NodesFinder.getSide(NodesFinder.findByQuery(pane.getId()));

      double width = primaryStage.getWidth() + (event.getScreenX() - startDrag.getX()) * side.coefficient(Coordinates.X);
      double height = primaryStage.getHeight() + (event.getScreenY() - startDrag.getY()) * side.coefficient(Coordinates.Y);

      if (primaryStage.getMinWidth() <= width && width <= primaryStage.getMaxWidth()) {
         if(side == Side.LEFT || side == Side.LEFT_TOP || side == Side.LEFT_BOTTOM) {
            primaryStage.setX(primaryStage.getX() + (event.getScreenX() - startDrag.getX()));
         }
         primaryStage.setWidth(width);
         startDrag = new Point2D(event.getScreenX(), startDrag.getY());
      }

      if (primaryStage.getMinHeight() <= height && height <= primaryStage.getMaxHeight()) {
         if(side == Side.TOP || side == Side.LEFT_TOP || side == Side.RIGHT_TOP) {
            primaryStage.setY(primaryStage.getY() + (event.getScreenY() - startDrag.getY()));
         }
         primaryStage.setHeight(height);
         startDrag = new Point2D(startDrag.getX(), event.getScreenY());
      }

   }

   private void setFullScreen() {
      Button full = findBy(NodesFinder.FULL);

      Pane left = findBy(NodesFinder.LEFT_RESIZE);
      Pane extraLeft = findBy(NodesFinder.EXTRA_LEFT_RESIZE);
      Pane right = findBy(NodesFinder.RIGHT_RESIZE);
      Pane extraRight = findBy(NodesFinder.EXTRA_RIGHT_RESIZE);
      Pane top = findBy(NodesFinder.TOP_RESIZE);
      Pane bottom = findBy(NodesFinder.BOTTOM_RESIZE);

      Pane leftBottom = findBy(NodesFinder.LEFT_BOTTOM_RESIZE);
      Pane rightBottom = findBy(NodesFinder.RIGHT_BOTTOM_RESIZE);
      Pane leftTop = findBy(NodesFinder.LEFT_TOP_RESIZE);
      Pane rightTop = findBy(NodesFinder.RIGHT_TOP_RESIZE);

      Pane rightResizeFull = findBy(NodesFinder.RIGHT_RESIZE_FULL);
      Pane rightBottomResizeFull = findBy(NodesFinder.RIGHT_BOTTOM_RESIZE_FULL);
      Pane bottomResizeFull = findBy(NodesFinder.BOTTOM_RESIZE_FULL);

      if (isFullScreen) {
         primaryStage.setX(stagePosition.getX());
         primaryStage.setY(stagePosition.getY());
         primaryStage.setWidth(primaryStage.getMinWidth());
         primaryStage.setHeight(primaryStage.getMinHeight());

         Stream.of(leftTop, extraLeft, left, leftBottom, bottom, bottomResizeFull, rightBottom,
                 rightBottomResizeFull, right, extraRight, rightResizeFull, rightTop, top)
                 .collect(Collectors.toList())
                 .forEach(pane -> pane.setDisable(false));

         full.setText(FULL_SCREEN_ICON);

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

         Stream.of(leftTop, extraLeft, left, leftBottom, bottom, bottomResizeFull, rightBottom,
                 rightBottomResizeFull, right, extraRight, rightResizeFull, rightTop, top)
                 .collect(Collectors.toList())
                 .forEach(pane -> pane.setDisable(true));

         full.setText(SMALL_SCREEN_ICON);

         isFullScreen = true;
      }
   }

   private void setActionsForInfoBtns() {
      Button logBtn = findBy(NodesFinder.LOG_BTN);
      Pane logSelect = findBy(NodesFinder.LOG_SELECT);

      Button memoryBtn = findBy(NodesFinder.MEMORY_BTN);
      Pane memorySelect = findBy(NodesFinder.MEMORY_SELECT);

      Label extraLogLabel = findBy(NodesFinder.EXTRA_LOG_LABEL);
      Label extraMemoryLabel = findBy(NodesFinder.EXTRA_MEMORY_LABEL);
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

   private void setActionForDropDownMenu() {
      Button menuBtn = findBy(NodesFinder.MENU_BTN);
      AnchorPane mainPane = findBy(NodesFinder.MAIN_PANE);
      Pane hideMenu = findBy(NodesFinder.HIDE_MENU);
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

   private void menuSize(boolean show) {
      AnchorPane menu = findBy(NodesFinder.MENU);
      TranslateTransition animation = new TranslateTransition(Duration.millis(ANIMATION_TIME), menu);
      Pane hideMenu = findBy(NodesFinder.HIDE_MENU);

      if (show) {
         animation.setToX(ZERO);
         isMenuShown = true;
      } else {
         animation.setToX(-MENU_SIZE);
         isMenuShown = false;
      }

      hideMenu.setVisible(isMenuShown);
      animation.play();
      menu.setVisible(show);
   }

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

   public <T extends Node> T findBy(NodesFinder desiredNode) {
      return (T) root.lookup(desiredNode.getQuery());
   }

}