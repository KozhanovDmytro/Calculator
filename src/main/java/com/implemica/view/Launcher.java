package com.implemica.view;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
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

      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/root.fxml"));

      root = loader.load();
      root.getStylesheets().add("/css/style.css");
      Scene scene = new Scene(root, 322, 500);

      primaryStage.initStyle(StageStyle.UNDECORATED);
      primaryStage.setScene(scene);

      primaryStage.setMinHeight(500);
      primaryStage.setMinWidth(322);

      primaryStage.setMaxWidth(Screen.getPrimary().getBounds().getMaxX());
      primaryStage.setMaxHeight(Screen.getPrimary().getBounds().getMaxY());

      primaryStage.getIcons().add(new Image("icons/icon.png"));

      setUserAgentStylesheet(STYLESHEET_CASPIAN);
   }

   private void setActionForResultLabel() {
      Label resultLabel = (Label) root.lookup("#resultLabel");
      HBox resultLabelBox = (HBox) root.lookup("#resultLabelBox");

//      resultLabel.widthProperty()
//              .addListener((observable, oldValue, newValue) -> calculateSizeForResultLabel(resultLabel, resultLabelBox));
      resultLabelBox.widthProperty()
              .addListener((observable, oldValue, newValue) -> calculateSizeForResultLabel(resultLabel, resultLabelBox));
      resultLabel.textProperty()
              .addListener((observable, oldValue, newValue) -> {
                 calculateSizeForResultLabel(resultLabel, resultLabelBox);
              });
   }

   private void calculateSizeForResultLabel(Label resultLabel, HBox resultLabelBox) {
      double fontSize = resultLabel.getFont().getSize();
      Text text = new Text(resultLabel.getText());
      text.setFont(resultLabel.getFont());
      double width = text.getBoundsInLocal().getWidth();
      if (width + 10 > resultLabelBox.getWidth() || resultLabel.getText().length() >= 13) {
         fontSize = fontSize * resultLabelBox.getWidth() / (width + 10);
      } else {
         fontSize = 48.0d;
      }
      if (fontSize <= 48)
         resultLabel.setFont(new Font(resultLabel.getFont().getName(), fontSize));
   }

   private void setMoveActionForWindow() {
      AnchorPane mainPane = (AnchorPane) root.lookup("#mainPane");
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
      Button close = (Button) root.lookup("#close");
      Button full = (Button) root.lookup("#full");
      Button hide = (Button) root.lookup("#hide");
      AnchorPane mainPane = (AnchorPane) root.lookup("#mainPane");

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

      mainPane.setOnMouseClicked(event -> {
         if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 2) {
               setFullScreen();
            }
         }
      });
   }

   private void setResizeActionForStage() {
      AnchorPane extraInfoFull = (AnchorPane) root.lookup("#extraInfoFull");
      HBox extraInfoBtns = (HBox) root.lookup("#extraInfoBtns");
      Label extraMemoryLabel = (Label) root.lookup("#extraMemoryLabel");

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

      Stream.of(left, extraLeft, right, extraRight, top, bottom, leftBottom,
              rightBottom, leftTop, rightTop, rightResizeFull, rightBottomResizeFull, bottomResizeFull)
              .collect(Collectors.toList())
              .forEach(pane -> {
                 pane.setOnMousePressed(event -> {
                    isDragging = true;
                    startDrag = new Point2D(event.getScreenX(), event.getScreenY());
                 });
                 pane.setOnMouseReleased(event -> isDragging = false);
              });

      Stream.of(right, extraRight, rightResizeFull)
              .collect(Collectors.toList())
              .forEach(pane -> pane.setOnMouseDragged(event -> {
                 double width = primaryStage.getWidth() + (event.getScreenX() - startDrag.getX());
                 if (primaryStage.getMinWidth() <= width && width <= primaryStage.getMaxWidth()) {
                    primaryStage.setWidth(width);
                    startDrag = new Point2D(event.getScreenX(), event.getScreenY());
                 }
              }));

      Stream.of(left, extraLeft)
              .collect(Collectors.toList())
              .forEach(pane -> pane.setOnMouseDragged(event -> {
                 double width = primaryStage.getWidth() + (startDrag.getX() - event.getScreenX());

                 if (primaryStage.getMinWidth() <= width && width <= primaryStage.getMaxWidth()) {
                    primaryStage.setX(primaryStage.getX() + (event.getScreenX() - startDrag.getX()));
                    primaryStage.setWidth(width);
                    startDrag = new Point2D(event.getScreenX(), event.getScreenY());
                 }
              }));

      Stream.of(bottom, bottomResizeFull)
              .collect(Collectors.toList())
              .forEach(pane -> pane.setOnMouseDragged(event -> {
                 double height = primaryStage.getHeight() + (event.getScreenY() - startDrag.getY());
                 if (primaryStage.getMinHeight() <= height && height <= primaryStage.getMaxHeight()) {
                    primaryStage.setHeight(height);
                    startDrag = new Point2D(event.getScreenX(), event.getScreenY());
                 }
              }));

      top.setOnMouseDragged(event -> {
         double height = primaryStage.getHeight() + (startDrag.getY() - event.getScreenY());
         if (primaryStage.getMinHeight() <= height && height <= primaryStage.getMaxHeight()) {
            primaryStage.setY(primaryStage.getY() + (event.getScreenY() - startDrag.getY()));
            primaryStage.setHeight(height);
            startDrag = new Point2D(event.getScreenX(), event.getScreenY());
         }
      });

      Stream.of(rightBottom, rightBottomResizeFull)
              .collect(Collectors.toList())
              .forEach(pane -> pane.setOnMouseDragged(event -> {
                 double height = primaryStage.getHeight() + (event.getScreenY() - startDrag.getY());
                 double width = primaryStage.getWidth() + (event.getScreenX() - startDrag.getX());

                 if (primaryStage.getMinWidth() <= width && width <= primaryStage.getMaxWidth()) {
                    primaryStage.setWidth(width);
                    startDrag = new Point2D(event.getScreenX(), startDrag.getY());
                 }

                 if (primaryStage.getMinHeight() <= height && height <= primaryStage.getMaxHeight()) {
                    primaryStage.setHeight(height);
                    startDrag = new Point2D(startDrag.getX(), event.getScreenY());
                 }
              }));

      leftBottom.setOnMouseDragged(event -> {
         double width = primaryStage.getWidth() + (startDrag.getX() - event.getScreenX());
         double height = primaryStage.getHeight() + (event.getScreenY() - startDrag.getY());

         if (primaryStage.getMinHeight() <= height && height <= primaryStage.getMaxHeight()) {
            primaryStage.setHeight(height);
            startDrag = new Point2D(startDrag.getX(), event.getScreenY());
         }

         if (primaryStage.getMinWidth() <= width && width <= primaryStage.getMaxWidth()) {
            primaryStage.setX(primaryStage.getX() + (event.getScreenX() - startDrag.getX()));
            primaryStage.setWidth(width);
            startDrag = new Point2D(event.getScreenX(), startDrag.getY());
         }
      });

      rightTop.setOnMouseDragged(event -> {
         double width = primaryStage.getWidth() + (event.getScreenX() - startDrag.getX());
         double height = primaryStage.getHeight() + (startDrag.getY() - event.getScreenY());

         if (primaryStage.getMinWidth() <= width && width <= primaryStage.getMaxWidth()) {
            primaryStage.setWidth(width);
            startDrag = new Point2D(event.getScreenX(), startDrag.getY());
         }

         if (primaryStage.getMinHeight() <= height && height <= primaryStage.getMaxHeight()) {
            primaryStage.setY(primaryStage.getY() + (event.getScreenY() - startDrag.getY()));
            primaryStage.setHeight(height);
            startDrag = new Point2D(startDrag.getX(), event.getScreenY());
         }


      });

      leftTop.setOnMouseDragged(event -> {
         double width = primaryStage.getWidth() + (startDrag.getX() - event.getScreenX());
         double height = primaryStage.getHeight() + (startDrag.getY() - event.getScreenY());

         if (primaryStage.getMinWidth() <= width && width <= primaryStage.getMaxWidth()) {
            primaryStage.setX(primaryStage.getX() + (event.getScreenX() - startDrag.getX()));
            primaryStage.setWidth(width);
            startDrag = new Point2D(event.getScreenX(), startDrag.getY());
         }

         if (primaryStage.getMinHeight() <= height && height <= primaryStage.getMaxHeight()) {
            primaryStage.setY(primaryStage.getY() + (event.getScreenY() - startDrag.getY()));
            primaryStage.setHeight(height);
            startDrag = new Point2D(startDrag.getX(), event.getScreenY());
         }


      });

      primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
         if (newValue.intValue() >= 560 && !isFullScreen) {
            double plusPx = newValue.intValue() - 560;
            if (240 + plusPx * 0.5 <= 325)
               extraInfoFull.setPrefWidth(240 + plusPx * 0.5);
         } else if (extraInfoFull.getWidth() != 0) {
            extraInfoFull.setPrefWidth(0);
         }
      });

      extraInfoFull.widthProperty().addListener((observable, oldValue, newValue) -> {
         Button showMemory = (Button) root.lookup("#showMemory");
         Button logButton = (Button) root.lookup("#logButton");

         Stream.of(right, rightBottom)
                 .collect(Collectors.toList())
                 .forEach(pane -> {
                    if (!isFullScreen) {
                       pane.setVisible(false);
                       pane.setDisable(true);
                    }
                 });

         Stream.of(rightResizeFull, rightBottomResizeFull, bottomResizeFull)
                 .collect(Collectors.toList())
                 .forEach(pane -> {
                    if (!isFullScreen) {
                       pane.setVisible(true);
                       pane.setDisable(false);
                    }
                 });

         if (extraInfoFull.getPrefWidth() == 0.0d) {
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

   private void setResizeControl(Pane pane, final String direction) {
      pane.setOnMouseDragged(new EventHandler<MouseEvent>() {
         public void handle(MouseEvent mouseEvent) {
            if (mouseEvent.isPrimaryButtonDown()) {
               double width = primaryStage.getWidth();
               double height = primaryStage.getHeight();

               // Horizontal resize.
               if (direction.endsWith("left")) {
                  if ((width > primaryStage.getMinWidth()) || (mouseEvent.getX() < 0)) {
                     primaryStage.setWidth(width - mouseEvent.getScreenX() + primaryStage.getX());
                     primaryStage.setX(mouseEvent.getScreenX());
                  }
               } else if ((direction.endsWith("right"))
                       && ((width > primaryStage.getMinWidth()) || (mouseEvent.getX() > 0))) {
                  primaryStage.setWidth(width + mouseEvent.getX());
               }

               // Vertical resize.
               if (direction.startsWith("top")) {
                  if (isDragging) {
                     primaryStage.setHeight(startDrag.getY());
                     isDragging = false;
                  } else if ((height > primaryStage.getMinHeight()) || (mouseEvent.getY() < 0)) {
                     primaryStage.setHeight(height - mouseEvent.getScreenY() + primaryStage.getY());
                     primaryStage.setY(mouseEvent.getScreenY());
                  }
               } else if (direction.startsWith("bottom")) {
                  if (isDragging) {
                     primaryStage.setY(startDrag.getY());
                     isDragging = false;
                  } else if ((height > primaryStage.getMinHeight()) || (mouseEvent.getY() > 0)) {
                     primaryStage.setHeight(height + mouseEvent.getY());
                  }
               }
            }
         }
      });
   }
}