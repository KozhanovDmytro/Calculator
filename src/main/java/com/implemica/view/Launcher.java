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
import javafx.scene.layout.GridPane;
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
            // TODO Rename it.
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
      Button full = (Button) root.lookup("#full");

      Pane left = (Pane) root.lookup("#leftResize");
      Pane extraLeft = (Pane) root.lookup("#extraLeftResize");
      Pane right = (Pane) root.lookup("#rightResize");
      Pane extraRight = (Pane) root.lookup("#extraRightResize");
      Pane top = (Pane) root.lookup("#topResize");
      Pane bottom = (Pane) root.lookup("#bottomResize");

      Pane leftBottom = (Pane) root.lookup("#leftBottomResize");
      Pane rightBottom = (Pane) root.lookup("#rightBottomResize");
      Pane leftTop = (Pane) root.lookup("#leftTopResize");
      Pane rightTop = (Pane) root.lookup("#rightTopResize");

      Pane rightResizeFull = (Pane) root.lookup("#rightResizeFull");
      Pane rightBottomResizeFull = (Pane) root.lookup("#rightBottomResizeFull");
      Pane bottomResizeFull = (Pane) root.lookup("#bottomResizeFull");

      if (isFullScreen) {
         primaryStage.setX(stagePosition.getX());
         primaryStage.setY(stagePosition.getY());
         primaryStage.setWidth(primaryStage.getMinWidth());
         primaryStage.setHeight(primaryStage.getMinHeight());

         Stream.of(leftTop, extraLeft, left, leftBottom, bottom, bottomResizeFull, rightBottom,
                 rightBottomResizeFull, right, extraRight, rightResizeFull, rightTop, top)
                 .collect(Collectors.toList())
                 .forEach(pane -> pane.setDisable(false));

         full.setText("\uE922");

         isFullScreen = false;
      } else {
         Screen screen = Screen.getPrimary();
         Rectangle2D bounds = screen.getVisualBounds();

         stagePosition = new Point2D(primaryStage.getX(), primaryStage.getY());

         ((AnchorPane) root.lookup("#extraInfoFull")).setPrefWidth(325);
         primaryStage.setX(bounds.getMinX());
         primaryStage.setY(bounds.getMinY());
         primaryStage.setWidth(bounds.getWidth());
         primaryStage.setHeight(bounds.getHeight());

         Stream.of(leftTop, extraLeft, left, leftBottom, bottom, bottomResizeFull, rightBottom,
                 rightBottomResizeFull, right, extraRight, rightResizeFull, rightTop, top)
                 .collect(Collectors.toList())
                 .forEach(pane -> pane.setDisable(true));

         full.setText("\uE923");

         isFullScreen = true;
      }
   }

   private void setActionsForInfoBtns() {
      Button logBtn = (Button) root.lookup("#logBtn");
      Pane logSelect = (Pane) root.lookup("#logSelect");

      Button memoryBtn = (Button) root.lookup("#memoryBtn");
      Pane memorySelect = (Pane) root.lookup("#memorySelect");

      Label extraLogLabel = (Label) root.lookup("#extraLogLabel");
      Label extraMemoryLabel = (Label) root.lookup("#extraMemoryLabel");
      extraMemoryLabel.setVisible(false);
      extraLogLabel.setVisible(true);

      memoryBtn.setOnMouseClicked(event -> {
         logSelect.getStyleClass().clear();
         memorySelect.getStyleClass().add("ExtraInfoBtnSelected");
         extraMemoryLabel.setVisible(true);
         extraLogLabel.setVisible(false);
      });

      logBtn.setOnMouseClicked(event -> {
         memorySelect.getStyleClass().clear();
         logSelect.getStyleClass().add("ExtraInfoBtnSelected");
         extraMemoryLabel.setVisible(false);
         extraLogLabel.setVisible(true);
      });
   }

   private void setActionsForKeyboard() {
      GridPane grid = (GridPane) root.lookup("#grid");
      primaryStage.getScene().setOnKeyPressed(key -> {
         if (key.getCode() == KeyCode.DIGIT5 && key.isShiftDown()) {
            ((Button) grid.lookup("#percentOperation")).fire();
            return;
         }
         if (key.getCode() == KeyCode.DIGIT2 && key.isShiftDown()) {
            ((Button) grid.lookup("#sqrtOperation")).fire();
            return;
         }

         if (key.getCode() == KeyCode.Q && !key.isShiftDown()) {
            ((Button) grid.lookup("#square")).fire();
         }
         if (key.getCode() == KeyCode.R && !key.isShiftDown()) {
            ((Button) grid.lookup("#divideByX")).fire();
         }
         if (key.getCode() == KeyCode.DIGIT0 && !key.isShiftDown()) {
            ((Button) grid.lookup("#btn0")).fire();
         }
         if (key.getCode() == KeyCode.DIGIT1 && !key.isShiftDown()) {
            ((Button) grid.lookup("#btn1")).fire();
         }
         if (key.getCode() == KeyCode.DIGIT2 && !key.isShiftDown()) {
            ((Button) grid.lookup("#btn2")).fire();
         }
         if (key.getCode() == KeyCode.DIGIT3 && !key.isShiftDown()) {
            ((Button) grid.lookup("#btn3")).fire();
         }
         if (key.getCode() == KeyCode.DIGIT4 && !key.isShiftDown()) {
            ((Button) grid.lookup("#btn4")).fire();
         }
         if (key.getCode() == KeyCode.DIGIT5 && !key.isShiftDown()) {
            ((Button) grid.lookup("#btn5")).fire();
         }
         if (key.getCode() == KeyCode.DIGIT6 && !key.isShiftDown()) {
            ((Button) grid.lookup("#btn6")).fire();
         }
         if (key.getCode() == KeyCode.DIGIT7 && !key.isShiftDown()) {
            ((Button) grid.lookup("#btn7")).fire();
         }
         if (key.getCode() == KeyCode.DIGIT8 && !key.isShiftDown()) {
            ((Button) grid.lookup("#btn8")).fire();
         }
         if (key.getCode() == KeyCode.DIGIT9 && !key.isShiftDown()) {
            ((Button) grid.lookup("#btn9")).fire();
         }
         if (key.getCode() == KeyCode.BACK_SPACE && !key.isShiftDown()) {
            ((Button) grid.lookup("#backSpace")).fire();
         }
         if (key.getCode() == KeyCode.COMMA) {
            ((Button) grid.lookup("#separateBtn")).fire();
         }
         if (key.getCode() == KeyCode.F9 && !key.isShiftDown()) {
            ((Button) grid.lookup("#negate")).fire();
         }
         if (key.getCode() == KeyCode.EQUALS && !key.isShiftDown()) {
            ((Button) grid.lookup("#equalsOperation")).fire();
         }
         if (key.getCode() == KeyCode.PLUS || (key.getCode() == KeyCode.EQUALS && key.isShiftDown())) {
            ((Button) grid.lookup("#plusOperation")).fire();
         }
         if (key.getCode() == KeyCode.MINUS) {
            ((Button) grid.lookup("#minusOperation")).fire();
         }
         if (key.getCode() == KeyCode.DIVIDE) {
            ((Button) grid.lookup("#divideOperation")).fire();
         }
         if (key.getCode() == KeyCode.MULTIPLY || (key.getCode() == KeyCode.DIGIT8 && key.isShiftDown())) {
            ((Button) grid.lookup("#plusOperation")).fire();
         }
         if (key.getCode() == KeyCode.ESCAPE && !key.isShiftDown()) {
            ((Button) grid.lookup("#c")).fire();
         }
         if (key.getCode() == KeyCode.DELETE && !key.isShiftDown()) {
            ((Button) grid.lookup("#ce")).fire();
         }
      });
   }

   private void setActionForDropDownMenu() {
      Button menuBtn = (Button) root.lookup("#menuBtn");
      AnchorPane mainPane = (AnchorPane) root.lookup("#mainPane");
      Pane hideMenu = (Pane) root.lookup("#hideMenu");
      Pane hideMemoryField = (Pane) root.lookup("#hideMemoryField");

      Button showMemory = (Button) root.lookup("#showMemory");

      hideMenu.setVisible(false);
      hideMemoryField.setVisible(false);

      mainPane.setOnMouseClicked(event -> {
         memorySize(false);
         menuSize(false);
      });
      hideMenu.setOnMouseClicked(event -> menuSize(false));
      hideMemoryField.setOnMouseClicked(event -> memorySize(false));

      menuBtn.setOnMouseClicked(event -> {
         menuSize(!isMenuShown);
      });

      showMemory.setOnMouseClicked(event -> {
         memorySize(!isMemoryShown);
      });
   }

   private void menuSize(boolean show) {
      AnchorPane menu = (AnchorPane) root.lookup("#menu");
      TranslateTransition animation = new TranslateTransition(Duration.millis(50), menu);
      Pane hideMenu = (Pane) root.lookup("#hideMenu");

      if (show) {
         animation.setToX(0.0d);
         isMenuShown = true;
      } else {
         animation.setToX(-260.0d);
         isMenuShown = false;
      }

      hideMenu.setVisible(isMenuShown);
      animation.play();
      menu.setVisible(show);
   }

   private void memorySize(boolean show) {
      AnchorPane memoryField = (AnchorPane) root.lookup("#memoryField");
      TranslateTransition transition = new TranslateTransition(Duration.millis(50), memoryField);
      Pane hideMemoryField = (Pane) root.lookup("#hideMemoryField");

      if (show) {
         transition.setToY(0.0d);
         isMemoryShown = true;
      } else {
         transition.setToY(300.0d);
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