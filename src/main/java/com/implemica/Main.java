package com.implemica;

import com.implemica.view.Launcher;
import javafx.application.Application;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Main {
    /*
    * Do it firstly.
    * TODO rewrite tests. It must be more readable.
    * TODO check this case: "0,0000000000000001 sqr sqr sqr sqr sqr sqr M+ sqrt sqrt sqrt sqrt sqr sqr sqr sqr M- MR"
    */
    public static void main(String[] args) {
        Application.launch(Launcher.class);
    }
}
