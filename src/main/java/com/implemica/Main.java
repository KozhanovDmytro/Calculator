package com.implemica;

import com.implemica.view.Launcher;
import javafx.application.Application;

import java.math.BigDecimal;
import java.math.MathContext;

public class Main {
    /*
    * Do it firstly.
    * TODO look at rounding mode. When you divide to number 3 so many times.
    * TODO 0.0000111111111111 when you write into label scale isn't 16 characters.
    * TODO 0.0000111111111111 * 0.1 must be with E.
    * TODO make History interface
    * TODO rename variable showOperand in Operand.
    */
    public static void main(String[] args) {
        BigDecimal number = new BigDecimal("1");
        for (int i = 0; i < 7; i++) {
            number = number.divide(new BigDecimal(3), MathContext.DECIMAL64);
            System.out.println(number);
        }
//        Application.launch(Launcher.class);
    }
}
