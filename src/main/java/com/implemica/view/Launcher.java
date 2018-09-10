package com.implemica.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Launcher extends Application {

    private Stage primaryStage;

    private Point2D startDrag;

    private Point2D stagePosition;

    private boolean isFullScreen;

    private boolean isHalfFullScreen;

    private boolean isDragging;

    private Parent root;

    @Override
    public void start(Stage stage) throws Exception {
        setSettingsForStage(stage);

        setMoveActionForWindow();
        setActionsForMainPainAndButtons();
        setResizeActionForStage();
        setActionsForInfoBtns();

        setActionForResultLabel();

        this.primaryStage.show();
    }

    private void setActionForResultLabel() {
        // TODO think about that.
        Label resultLabel = (Label) root.lookup("#resultLabel");
        HBox resultLabelBox = (HBox) root.lookup("#resultLabelBox");

        resultLabel.textProperty().addListener((observable, oldValue, newValue) -> {
            double distance = Math.abs(resultLabel.getWidth() - resultLabelBox.getWidth());
            double size = resultLabel.getFont().getSize();
            if(distance < 25){
                resultLabel.setFont(new Font("Segoe UI Semibold", size - 4));
            }
        });

        resultLabelBox.widthProperty().addListener((observable, oldValue, newValue) -> {
            double distance = Math.abs(resultLabel.getWidth() - resultLabelBox.getWidth());
            double size = resultLabel.getFont().getSize();
            if(newValue.intValue() >= oldValue.intValue()){
                if(distance > 25 && distance < 35){
                    if(size + 4 <= 48) {
                        resultLabel.setFont(new Font("Segoe UI Semibold", size + 4));
                    }
                }
            } else {
                if(distance < 25){
                    resultLabel.setFont(new Font("Segoe UI Semibold", size - 4));
                }
            }
        });
    }

    private void setSettingsForStage(Stage stage)throws Exception{
        this.primaryStage = stage;
        root = FXMLLoader.load(getClass().getResource("/root.fxml"));
        root.getStylesheets().add("css/style.css");

        Scene scene = new Scene(root, 321, 500);

        this.primaryStage.initStyle(StageStyle.UNDECORATED);
        this.primaryStage.setScene(scene);

        this.primaryStage.setMinHeight(500);
        this.primaryStage.setMinWidth(320);

        this.primaryStage.setMaxWidth(Screen.getPrimary().getBounds().getMaxX());
        this.primaryStage.setMaxHeight(Screen.getPrimary().getBounds().getMaxY());

        setUserAgentStylesheet(STYLESHEET_CASPIAN);
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

        close.setOnMouseClicked(event -> primaryStage.close());
        hide.setOnMouseClicked(event -> primaryStage.setIconified(!primaryStage.isIconified()));
        full.setOnMouseClicked(event -> setFullScreen());

        mainPane.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(event.getClickCount() == 2){
                    setFullScreen();
                }
            }
        });
    }

    private void setResizeActionForStage() {
        AnchorPane extraInfoFull = (AnchorPane) root.lookup("#extraInfoFull");
        HBox extraInfoBtns = (HBox) root.lookup("#extraInfoBtns");
        Label extraInfoLabel = (Label) root.lookup("#extraInfoLabel");

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

                    // TODO fix a trembling.
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
                    double width = primaryStage.getWidth() + event.getX();

                    if (width >= primaryStage.getMinWidth() && width <= primaryStage.getMaxWidth())
                        primaryStage.setWidth(event.getSceneX());
                    if (startDrag.getY() > primaryStage.getY() + primaryStage.getMinHeight() - 5) {
                        if (primaryStage.getMinHeight() <= height && height <= primaryStage.getMaxHeight()) {
                            primaryStage.setHeight(height);
                            startDrag = new Point2D(event.getScreenX(), event.getScreenY());
                        }
                    }
                }));

        leftBottom.setOnMouseDragged(event -> {
            double width = primaryStage.getWidth() - event.getScreenX() + primaryStage.getX();
            double height = primaryStage.getHeight() + (event.getScreenY() - startDrag.getY());

            if (primaryStage.getMinHeight() <= height && height <= primaryStage.getMaxHeight()) {
                primaryStage.setHeight(height);
                startDrag = new Point2D(event.getScreenX(), event.getScreenY());
            }

            if (width >= primaryStage.getMinWidth() && width <= primaryStage.getMaxWidth()) {
                primaryStage.setWidth(primaryStage.getX() - event.getScreenX() + primaryStage.getWidth());
                primaryStage.setX(event.getScreenX());
            }
        });

        rightTop.setOnMouseDragged(event -> {
            double width = primaryStage.getWidth() + event.getX();
            double height = primaryStage.getHeight() + (startDrag.getY() - event.getScreenY());

            if (primaryStage.getMinHeight() <= height && height <= primaryStage.getMaxHeight()) {
                primaryStage.setY(primaryStage.getY() + (event.getScreenY() - startDrag.getY()));
                primaryStage.setHeight(height);
                startDrag = new Point2D(event.getScreenX(), event.getScreenY());
            }

            if (width >= primaryStage.getMinWidth() && width <= primaryStage.getMaxWidth())
                primaryStage.setWidth(event.getSceneX());
        });

        leftTop.setOnMouseDragged(event -> {
            double width = primaryStage.getWidth() - event.getScreenX() + primaryStage.getX();
            double height = primaryStage.getHeight() + (startDrag.getY() - event.getScreenY());

            if (primaryStage.getMinHeight() <= height && height <= primaryStage.getMaxHeight()) {
                primaryStage.setY(primaryStage.getY() + (event.getScreenY() - startDrag.getY()));
                primaryStage.setHeight(height);
                startDrag = new Point2D(event.getScreenX(), event.getScreenY());
            }

            if (width >= primaryStage.getMinWidth() && width <= primaryStage.getMaxWidth()) {
                primaryStage.setWidth(primaryStage.getX() - event.getScreenX() + primaryStage.getWidth());
                primaryStage.setX(event.getScreenX());
            }
        });

        primaryStage.widthProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() >= 560 && !isFullScreen) {
                replaceNodesInGrid();
                double plusPx = newValue.intValue() - 560;
                if (240 + plusPx * 0.5 <= 325)
                    extraInfoFull.setPrefWidth(240 + plusPx * 0.5);
            } else if (extraInfoFull.getWidth() != 0) {
                extraInfoFull.setPrefWidth(0);
            }
        });

        primaryStage.heightProperty().addListener((observable, oldValue, newValue) -> replaceNodesInGrid());


        extraInfoFull.widthProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() >= 240 && newValue.intValue() <= 260 && extraInfoLabel.getText().length() > 19) {
                StringBuilder result = new StringBuilder(extraInfoLabel.getText());
                result.setCharAt(18, '\n');
                extraInfoLabel.setText(result.toString());
            } else {
                extraInfoLabel.setText(extraInfoLabel.getText().replace('\n', ' '));
            }

            Stream.of(right, rightBottom)
                    .collect(Collectors.toList())
                    .forEach(pane -> {
                        if(!isFullScreen) {
                            pane.setVisible(false);
                            pane.setDisable(true);
                        }
                    });

            Stream.of(rightResizeFull, rightBottomResizeFull, bottomResizeFull)
                    .collect(Collectors.toList())
                    .forEach(pane -> {
                        if(!isFullScreen) {
                            pane.setVisible(true);
                            pane.setDisable(false);
                        }
                    });


            extraInfoFull.setVisible(true);
            extraInfoFull.setDisable(false);
            extraInfoBtns.setVisible(true);
            extraInfoBtns.setDisable(false);
        });
    }

    private void replaceNodesInGrid(){
        GridPane grid = (GridPane) root.lookup("#grid");
        if(primaryStage.getWidth() >= 1025 && primaryStage.getHeight() >= 640 && !isHalfFullScreen){
            Label historyLabel = (Label) grid.lookup("#historyLabel");
            HBox resultLabelBox = (HBox) grid.lookup("#resultLabelBox");
            HBox memoryBox = (HBox) grid.lookup("#MemoryBox");
            Button percentOperation = (Button) grid.lookup("#percentOperation");
            Button sqrtOperation = (Button) grid.lookup("#sqrtOperation");
            Button square = (Button) grid.lookup("#square");
            Button divideByX = (Button) grid.lookup("#divideByX");
            Button ce = (Button) grid.lookup("#ce");
            Button c = (Button) grid.lookup("#c");
            Button backSpace = (Button) grid.lookup("#backSpace");
            Button divideOperation = (Button) grid.lookup("#divideOperation");
            Button btn7 = (Button) grid.lookup("#btn7");
            Button btn8 = (Button) grid.lookup("#btn8");
            Button btn9 = (Button) grid.lookup("#btn9");
            Button multiplyOperation = (Button) grid.lookup("#multiplyOperation");
            Button btn4 = (Button) grid.lookup("#btn4");
            Button btn5 = (Button) grid.lookup("#btn5");
            Button btn6 = (Button) grid.lookup("#btn6");
            Button minusOperation = (Button) grid.lookup("#minusOperation");
            Button btn1 = (Button) grid.lookup("#btn1");
            Button btn2 = (Button) grid.lookup("#btn2");
            Button btn3 = (Button) grid.lookup("#btn3");
            Button plusOperation = (Button) grid.lookup("#plusOperation");
            Button negate = (Button) grid.lookup("#negate");
            Button btn0 = (Button) grid.lookup("#btn0");
            Button divideLabelBtn = (Button) grid.lookup("#divideLabelBtn");
            Button equalsOperation = (Button) grid.lookup("#equalsOperation");

            grid.getChildren().clear();

            ColumnConstraints constraints = new ColumnConstraints(10, 100, Double.MAX_VALUE);
            constraints.setFillWidth(true);
            constraints.setHgrow(Priority.SOMETIMES);

            grid.getColumnConstraints().add(constraints);
            grid.getRowConstraints().remove(8);

            grid.add(historyLabel, 0, 0, 5, 1);
            grid.add(resultLabelBox, 0, 1, 5, 1);
            grid.add(memoryBox, 0, 2, 5, 1);

            grid.add(percentOperation, 0, 3);
            grid.add(ce, 1, 3);
            grid.add(c, 2, 3);
            grid.add(backSpace, 3, 3);
            grid.add(divideOperation, 4, 3);

            grid.add(sqrtOperation, 0, 4);
            grid.add(btn7, 1, 4);
            grid.add(btn8, 2, 4);
            grid.add(btn9, 3, 4);
            grid.add(multiplyOperation, 4, 4);

            grid.add(square, 0, 5);
            grid.add(btn4, 1, 5);
            grid.add(btn5, 2, 5);
            grid.add(btn6, 3, 5);
            grid.add(minusOperation, 4, 5);

            Button tripleX = new Button("x³");
            tripleX.setId("tripleX");
            tripleX.setMaxWidth(Double.MAX_VALUE);
            tripleX.setMaxHeight(Double.MAX_VALUE);
            tripleX.getStyleClass().addAll("MainBtn", "RegularBorderBtn", "RegularBtn", "OtherBtn");

            grid.add(tripleX, 0, 6);
            grid.add(btn1, 1, 6);
            grid.add(btn2, 2, 6);
            grid.add(btn3, 3, 6);
            grid.add(plusOperation, 4, 6);

            grid.add(divideByX, 0, 7);
            grid.add(negate, 1, 7);
            grid.add(btn0, 2, 7);
            grid.add(divideLabelBtn, 3, 7);
            grid.add(equalsOperation, 4, 7);

            isHalfFullScreen = true;
        } else if ((primaryStage.getWidth() <= 1025 || primaryStage.getHeight() <= 640) && isHalfFullScreen) {
            Label historyLabel = (Label) grid.lookup("#historyLabel");
            HBox resultLabelBox = (HBox) grid.lookup("#resultLabelBox");
            HBox memoryBox = (HBox) grid.lookup("#MemoryBox");
            Button percentOperation = (Button) grid.lookup("#percentOperation");
            Button sqrtOperation = (Button) grid.lookup("#sqrtOperation");
            Button square = (Button) grid.lookup("#square");
            Button divideByX = (Button) grid.lookup("#divideByX");
            Button ce = (Button) grid.lookup("#ce");
            Button c = (Button) grid.lookup("#c");
            Button backSpace = (Button) grid.lookup("#backSpace");
            Button divideOperation = (Button) grid.lookup("#divideOperation");
            Button btn7 = (Button) grid.lookup("#btn7");
            Button btn8 = (Button) grid.lookup("#btn8");
            Button btn9 = (Button) grid.lookup("#btn9");
            Button multiplyOperation = (Button) grid.lookup("#multiplyOperation");
            Button btn4 = (Button) grid.lookup("#btn4");
            Button btn5 = (Button) grid.lookup("#btn5");
            Button btn6 = (Button) grid.lookup("#btn6");
            Button minusOperation = (Button) grid.lookup("#minusOperation");
            Button btn1 = (Button) grid.lookup("#btn1");
            Button btn2 = (Button) grid.lookup("#btn2");
            Button btn3 = (Button) grid.lookup("#btn3");
            Button plusOperation = (Button) grid.lookup("#plusOperation");
            Button negate = (Button) grid.lookup("#negate");
            Button btn0 = (Button) grid.lookup("#btn0");
            Button divideLabelBtn = (Button) grid.lookup("#divideLabelBtn");
            Button equalsOperation = (Button) grid.lookup("#equalsOperation");

            grid.getChildren().clear();
            grid.getColumnConstraints().remove(4);
            grid.getRowConstraints().add(new RowConstraints(40, 40, Double.MAX_VALUE));

            grid.add(historyLabel, 0, 0, 5, 1);
            grid.add(resultLabelBox, 0, 1, 5, 1);
            grid.add(memoryBox, 0, 2, 5, 1);

            grid.add(percentOperation, 0, 3);
            grid.add(sqrtOperation, 1, 3);
            grid.add(square, 2, 3);
            grid.add(divideByX, 3, 3);

            grid.add(ce, 0, 4);
            grid.add(c, 1, 4);
            grid.add(backSpace, 2, 4);
            grid.add(divideOperation, 3, 4);

            grid.add(btn7, 0, 5);
            grid.add(btn8, 1, 5);
            grid.add(btn9, 2, 5);
            grid.add(multiplyOperation, 3, 5);

            grid.add(btn4, 0, 6);
            grid.add(btn5, 1, 6);
            grid.add(btn6, 2, 6);
            grid.add(minusOperation, 3, 6);

            grid.add(btn1, 0, 7);
            grid.add(btn2, 1, 7);
            grid.add(btn3, 2, 7);
            grid.add(plusOperation, 3, 7);

            grid.add(negate, 0, 8);
            grid.add(btn0, 1, 8);
            grid.add(divideLabelBtn, 2, 8);
            grid.add(equalsOperation, 3, 8);

            isHalfFullScreen = false;
        }
    }

    private void setFullScreen(){
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

            ((AnchorPane)root.lookup("#extraInfoFull")).setPrefWidth(325);
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

    private void setActionsForInfoBtns(){
        Button logBtn = (Button) root.lookup("#logBtn");
        Pane logSelect = (Pane) root.lookup("#logSelect");

        Button memoryBtn = (Button) root.lookup("#memoryBtn");
        Pane memorySelect = (Pane) root.lookup("#memorySelect");

        Label extraInfoLabel = (Label) root.lookup("#extraInfoLabel");

        memoryBtn.setOnMouseClicked(event -> {
            logSelect.getStyleClass().clear();
            memorySelect.getStyleClass().add("ExtraInfoBtnSelected");
            extraInfoLabel.setText("В памяти ничего не сохранено");
        });

        logBtn.setOnMouseClicked(event -> {
            memorySelect.getStyleClass().clear();
            logSelect.getStyleClass().add("ExtraInfoBtnSelected");
            extraInfoLabel.setText("Журнала еще нет");
        });
    }
}