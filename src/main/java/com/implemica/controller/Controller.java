package com.implemica.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

import com.implemica.model.Container;
import com.implemica.model.interfaces.Numeral;
import com.implemica.model.numerals.Arabic;
import com.implemica.model.numerals.numbers.Number;
import com.implemica.model.operations.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button c;

    @FXML
    private Button ce;

    @FXML
    private Button backSpace;

    @FXML
    private Button percentOperation;

    @FXML
    private Button sqrtOperation;

    @FXML
    private Button equalsOperation;

    @FXML
    private Button square;

    @FXML
    private Button divideByX;

    @FXML
    private Button divideOperation;

    @FXML
    private Button multiplyOperation;

    @FXML
    private Button minusOperation;

    @FXML
    private Button plusOperation;

    @FXML
    private Button negate;

    @FXML
    private Button divideLabelBtn;

    @FXML
    private Label resultLabel;

    @FXML
    private Label historyLabel;

    @FXML
    private Button btn0;

    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private Button btn3;

    @FXML
    private Button btn4;

    @FXML
    private Button btn5;

    @FXML
    private Button btn6;

    @FXML
    private Button btn7;

    @FXML
    private Button btn8;

    @FXML
    private Button btn9;

    private static HashSet<Node> newNodes = new HashSet<>();

    private Container container = new Container();

    private Numeral numeral = new Arabic();

    @FXML
    void initialize() {
        // TODO this must be at another method.
        container.setMadeOperand(true);

        actionsForNumberButtons();
        actionsForOperationButtons();
        actionsForCleanOperations();
    }

    private void actionsForOperationButtons() {

        plusOperation.setOnAction(event -> actionForOperations(new Plus()));
        minusOperation.setOnAction(event -> actionForOperations(new Minus()));
        multiplyOperation.setOnAction(event -> actionForOperations(new Multiply()));
        divideOperation.setOnAction(event -> actionForOperations(new Divide()));

        equalsOperation.setOnAction(event -> {
            if (container.isMadeOperand()) {
                container.getOperation().setOperand(new BigDecimal(container.showNumberForSystem(resultLabel.getText())));
            } else {
                if (container.getOperation() instanceof Equals) {
                    Equals eq = (Equals) container.getOperation();
                    container.setResult(new BigDecimal(container.showNumberForSystem(resultLabel.getText())));
                    eq.getLastOperation().setOperand(eq.getLastOperation().getOperand());
                }
            }
            container.calculate();
            showResult();
            Operation equals = new Equals(container.getOperation());
            container.getHistory().clear();
            updateHistory();
            container.setOperation(equals);
            container.getHistory().add(equals);

            container.setMadeOperand(false);
        });
    }

    private void actionForOperations(Operation operation) {
        if (!(container.getOperation() instanceof Equals))
            container.calculate();
        showResult();
        updateHistory();
        container.setOperation(operation);
        container.setMadeOperand(true);
    }

    private void actionsForNumberButtons() {
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
    }

    private void actionForBuildOperand(Number number) {
        if (container.isMadeOperand() || container.getOperation() instanceof Equals) {
            container.getOperation().buildOperand(numeral.translate(number));
            showOperand();
        }
    }

    private void actionsForCleanOperations() {

        backSpace.setOnAction(event -> {
            if (container.isMadeOperand()){
                container.getOperation().removeLast();
                showOperand();
            }
        });

        c.setOnAction(event -> {
            container.getHistory().clear();
            container.setResult(BigDecimal.ZERO);
            container.setOperation(new Default());
            showOperand();
        });

        ce.setOnAction(event -> {
            container.getOperation().setOperand(BigDecimal.ZERO);
            showOperand();
        });
    }

    private void showResult() {
        resultLabel.setText(container.showComfortableNumber(container.getResult()));
    }

    private void showOperand() {
        resultLabel.setText(container.showComfortableNumber(container.getOperation().getOperand()));
    }

    private void updateHistory() {
        historyLabel.setText(container.getHistory().toString());
    }

    public static void addNode(Node node) {
        newNodes.add(node);
    }


    public static void setActions() {
        for (Node node : newNodes) {
            if (node.getId().equals("tripleX")) {
                ((Button) node).setOnAction(event -> System.out.println("Boom!"));
            }
        }
    }

}

