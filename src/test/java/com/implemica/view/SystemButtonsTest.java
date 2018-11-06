package com.implemica.view;

import com.implemica.view.util.NodesFinder;
import com.implemica.view.util.TestFxBase;
import javafx.scene.control.Button;
import javafx.stage.Window;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SystemButtonsTest extends TestFxBase {

   @Test
   void windowCloseTest() {
      Button closeBtn = findBy(NodesFinder.CLOSE.getQuery());
      clickOn(closeBtn);

      assertEquals(0, Window.getWindows().size());
   }
}
