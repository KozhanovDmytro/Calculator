package com.implemica.view;

import com.implemica.controller.util.Node;
import com.implemica.view.util.TestFxBase;
import javafx.scene.control.Button;
import javafx.stage.Window;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Class for testing system buttons.
 *
 * @author Dmytro Kozhanov
 */
public class SystemButtonsTest extends TestFxBase {

   /**
    * Close window test.
    */
   @Test void windowCloseTest() {
      Button closeBtn = findBy(Node.CLOSE.getName());
      clickOn(closeBtn);

      assertEquals(0, Window.getWindows().size());
   }
}
