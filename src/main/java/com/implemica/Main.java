package com.implemica;

import com.implemica.view.Launcher;
import javafx.application.Application;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Main {
    /*
    * Do it firstly.
    * TODO look at rounding mode. When you divide to number 3 so many times.
    * TODO problem with not rounding mode. Use MathContext with your custom 'e'.
    * TODO 0.0000111111111111 when you write into label scale isn't 16 characters.
    * TODO 0.0000111111111111 * 0.1 must be with E.
    * TODO make History interface
    * TODO rename variable showOperand in Operand.
    */
    public static void main(String[] args) {
        Application.launch(Launcher.class);
    }
}
