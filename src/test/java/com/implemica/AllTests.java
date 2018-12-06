package com.implemica;

import com.implemica.controller.PerformanceTest;
import com.implemica.model.calculator.CalculatorTest;
import com.implemica.model.calculator.ContainerTest;
import com.implemica.model.numerals.ArabicTest;
import com.implemica.view.SystemButtonsTest;
import com.implemica.view.ViewTest;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({ViewTest.class, PerformanceTest.class,
        SystemButtonsTest.class, CalculatorTest.class,
        ContainerTest.class, ArabicTest.class})
public class AllTests {
}
