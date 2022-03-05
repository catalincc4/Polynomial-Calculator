package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.example.Validation.MatcheException;
import org.example.model.Polynomial;


public class Controller {

    private String polynomToWrite = "firstPolynom";

    @FXML
    private Button addButton;

    @FXML
    private TextField answerField;

    @FXML
    private Button delButton;

    @FXML
    private Button derivationButton;

    @FXML
    private Button divideButton;

    @FXML
    private Button eightButton;

    @FXML
    private TextField firstPolynomial;

    @FXML
    private Button fiveButton;

    @FXML
    private Button fourButton;

    @FXML
    private Button integrationButton;

    @FXML
    private Button minusButton;

    @FXML
    private Button multiplicateButton;

    @FXML
    private Button nineButton;

    @FXML
    private Button oneButton;

    @FXML
    private Button plusButton;

    @FXML
    private Button pointButton;

    @FXML
    private Button risePowerButton;

    @FXML
    private TextField secondPolynomial;

    @FXML
    private Button seventButton;

    @FXML
    private Button sixButton;

    @FXML
    private Button slashButton;

    @FXML
    private Button starButton;

    @FXML
    private Button subtractButton;

    @FXML
    private Button threeButton;

    @FXML
    private Button twoButton;

    @FXML
    private Button xButton;

    @FXML
    private Button zeroButton;

    @FXML
    private Text errorField;

    @FXML
    void oneButtonAction(MouseEvent event) {
        polynomPicker().appendText("1");
    }

    @FXML
    void twoButtonAction(MouseEvent event) {
        polynomPicker().appendText("2");
    }

    @FXML
    void threeButtonAction(MouseEvent event) {
        polynomPicker().appendText("3");
    }

    @FXML
    void fourButtonAction(MouseEvent event) {
        polynomPicker().appendText("4");
    }

    @FXML
    void fiveButtonAction(MouseEvent event) {
        polynomPicker().appendText("5");
    }

    @FXML
    void sixButtonAction(MouseEvent event) {
        polynomPicker().appendText("6");
    }

    @FXML
    void sevenButtonAction(MouseEvent event) {
        polynomPicker().appendText("7");
    }

    @FXML
    void eightButtonAction(MouseEvent event) {
        polynomPicker().appendText("8");
    }

    @FXML
    void nineButtonAction(MouseEvent event) {
        polynomPicker().appendText("9");
    }

    @FXML
    void zeroButtonAction(MouseEvent event) {
        polynomPicker().appendText("0");
    }

    @FXML
    void slashButtonAction(MouseEvent event) {
        polynomPicker().appendText("/");
    }

    @FXML
    void pointButtonAction(MouseEvent event) {
        polynomPicker().appendText(".");
    }

    @FXML
    void plusButtonAction(MouseEvent event) {
        polynomPicker().appendText("+");
    }


    @FXML
    void starButtonAction(MouseEvent event) {
        polynomPicker().appendText("*");
    }

    @FXML
    void xButtonAction(MouseEvent event) {
        polynomPicker().appendText("X");
    }

    @FXML
    void minusButtonAction(MouseEvent event) {
        polynomPicker().appendText("-");
    }

    @FXML
    void risePowerButtonAction(MouseEvent event) {
        polynomPicker().appendText("^");
    }

    @FXML
    void delButtonAction(MouseEvent event) {
        int n = polynomPicker().getText().length();
        if (n > 0)
            polynomPicker().deleteText(n - 1, n);
    }

    @FXML
    void addButtonAction(MouseEvent event) {

        try {
            Polynomial polynomial = new Polynomial(" ");
            errorField.setText(" ");
            polynomial.test(firstPolynomial.getText());
            polynomial.test(secondPolynomial.getText());
            polynomial = new Polynomial(firstPolynomial.getText());
            Polynomial polynomial1 = new Polynomial(secondPolynomial.getText());
            answerField.setText(polynomial.add(polynomial1).toString());
        } catch (MatcheException e) {
            errorField.setText(e.getMessage());
        }
    }

    @FXML
    void divideButtonAction(MouseEvent event) {

    }

    @FXML
    void multiplicateButtonAction(MouseEvent event) {
        try {
            Polynomial polynomial = new Polynomial(" ");
            errorField.setText(" ");
            polynomial.test(firstPolynomial.getText());
            polynomial.test(secondPolynomial.getText());
            polynomial = new Polynomial(firstPolynomial.getText());
            Polynomial polynomial1 = new Polynomial(secondPolynomial.getText());
            answerField.setText(polynomial.multiplicate(polynomial1).toString());
        } catch (MatcheException e) {
            errorField.setText(e.getMessage());
        }

    }

    @FXML
    void subtractButtonAction(MouseEvent event) {
        try {
            Polynomial polynomial = new Polynomial(" ");
            errorField.setText(" ");
            polynomial.test(firstPolynomial.getText());
            polynomial.test(secondPolynomial.getText());
            polynomial = new Polynomial(firstPolynomial.getText());
            Polynomial polynomial1 = new Polynomial(secondPolynomial.getText());
            answerField.setText(polynomial.subtract(polynomial1).toString());
        } catch (MatcheException e) {
            errorField.setText(e.getMessage());
        }
    }

    @FXML
    void derivationButtonAction(MouseEvent event) {

        try {
            errorField.setText(" ");
            Polynomial polynomial = new Polynomial(" ");
            polynomial.test(firstPolynomial.getText());
            polynomial = new Polynomial(firstPolynomial.getText());
            polynomial.derivation();
            answerField.setText(polynomial.toString());
        } catch (MatcheException e) {
            errorField.setText(e.getMessage());
        }
    }

    @FXML
    void integrationButtonAction(MouseEvent event) {
        try {
            errorField.setText(" ");
            Polynomial polynomial = new Polynomial(" ");
            polynomial.test(firstPolynomial.getText());
            polynomial = new Polynomial(firstPolynomial.getText());
            polynomial.integration();
            answerField.setText(polynomial.toString());
        } catch (MatcheException e) {
            errorField.setText(e.getMessage());
        }
    }

    @FXML
    void firstPolynomialField(MouseEvent event) {
        polynomToWrite = "firstPolynom";
    }

    @FXML
    void secondPolynomialField(MouseEvent event) {
        polynomToWrite = "secondPolynom";
    }

    public TextField polynomPicker() {
        if (polynomToWrite.equals("firstPolynom")) {
            return firstPolynomial;
        }
        return secondPolynomial;
    }
}
