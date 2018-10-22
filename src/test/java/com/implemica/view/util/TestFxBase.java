package com.implemica.view.util;

import com.implemica.view.Launcher;
import javafx.geometry.Point2D;
import javafx.stage.Window;
import org.junit.jupiter.api.BeforeAll;
import org.loadui.testfx.utils.FXTestUtils;

import java.awt.*;
import java.awt.event.InputEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.framework.junit.ApplicationTest.launch;

public class TestFxBase {

   protected static Robot robot;

   @BeforeAll
   public static void setUp() throws Exception {
      launch(Launcher.class);

      robot = new Robot();
   }

   private Point2D getBound(Side side) {
      Window window = getCurrentWindow();
      Point2D point = null;
      switch (side) {
         case LEFT:
            point = new Point2D(window.getX() + 1.0d, window.getY() + window.getHeight() / 2);
            break;
         case RIGHT:
            point = new Point2D(window.getX() + window.getWidth() - 1.0d, window.getY() + window.getHeight() / 2);
            break;
         case TOP:
            point = new Point2D(window.getX() + window.getWidth() / 2, window.getY() + 1.0d);
            break;
         case BOTTOM:
            point = new Point2D(window.getX() + window.getWidth() / 2, window.getY() + window.getHeight() - 1.0d);
            break;
         case LEFT_TOP:
            point = new Point2D(window.getX() + 1.0d, window.getY() + 1.0d);
            break;
         case LEFT_BOTTOM:
            point = new Point2D(window.getX() + 1.0d, window.getY() + window.getHeight() - 1.0d);
            break;
         case RIGHT_TOP:
            point = new Point2D(window.getX() + window.getWidth() - 1.0d, window.getY() + 1.0d);
            break;
         case RIGHT_BOTTOM:
            point = new Point2D(window.getX() + window.getWidth() - 1.0d, window.getY() + window.getHeight() - 1.0d);
            break;
      }
      return point;
   }

   public void checkDrag(Side side, double x, double y) {
      Window window = getCurrentWindow();
      double initialHeight = window.getHeight();
      double initialWidth = window.getWidth();

      drag(getBound(side), x, y);

      FXTestUtils.awaitEvents();

      // check width
      assertEquals(initialWidth + x * side.coefficient()[Coordinates.X.ordinal()], window.getWidth());

      // check height
      assertEquals(initialHeight + y * side.coefficient()[Coordinates.Y.ordinal()], window.getHeight());
   }

   public void drag(Point2D start, double x, double y) {
      robot.mouseMove((int)start.getX(), (int)start.getY());
      robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
      robot.mouseMove((int)(start.getX() + x), (int)(start.getY() + y));
      robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
   }

   private Window getCurrentWindow(){
      return Window.getWindows().get(0);
   }
}
