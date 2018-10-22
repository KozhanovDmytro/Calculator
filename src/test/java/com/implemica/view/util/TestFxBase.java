package com.implemica.view.util;

import com.implemica.view.Launcher;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.stage.Window;
import org.junit.jupiter.api.BeforeAll;
import org.loadui.testfx.utils.FXTestUtils;
import org.testfx.api.FxRobot;

import java.awt.*;
import java.awt.event.InputEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.framework.junit.ApplicationTest.launch;

public class TestFxBase {

   private static Robot robotAwt;

   private static FxRobot robotFx;

   @BeforeAll
   public static void setUp() throws Exception {
      launch(Launcher.class);

      robotAwt = new Robot();

      robotFx = new FxRobot();
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
//      double expectedWidth = initialWidth + x * side.coefficient()[Coordinates.X.ordinal()];
//      assertTrue(window.getWidth() <= expectedWidth + 1 && window.getWidth() >= expectedWidth - 1);
      assertEquals(initialWidth + x * side.coefficient()[Coordinates.X.ordinal()], window.getWidth());

      // check height
//      double expectedHeight = initialHeight + y * side.coefficient()[Coordinates.Y.ordinal()];
//      assertTrue(window.getHeight() <= expectedHeight + 1 && window.getHeight() >= expectedHeight - 1);
      assertEquals(initialHeight + y * side.coefficient()[Coordinates.Y.ordinal()], window.getHeight());
   }

   public void drag(Point2D start, double x, double y) {
      robotAwt.mouseMove((int)start.getX(), (int)start.getY());
      robotAwt.mousePress(InputEvent.BUTTON1_DOWN_MASK);
      robotAwt.mouseMove((int)(start.getX() + x), (int)(start.getY() + y));
      robotAwt.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
   }

   public void moveWindow(Point2D[] points) {
      Window window = getCurrentWindow();

      double initialX = window.getX() + window.getWidth() / 2;
      double initialY = window.getY() + 10;

      robotFx.drag(initialX, initialY);

      for(Point2D point : points) {
         robotFx.moveTo(point.getX(), point.getY());
         // check X
         assertEquals(point.getX() - window.getWidth() / 2, window.getX());

         // check Y
         assertEquals(point.getY() - 10, window.getY());
      }
      robotFx.moveTo(initialX, initialY);
      robotFx.drop();
   }

   public <T extends Node> T findBy(String query) {
      Window window = getCurrentWindow();
      return (T) window.getScene().getRoot().lookup(query);
   }

   public void clickOn (Node node) {
      Bounds boundsInScreen = node.localToScreen(node.getBoundsInLocal());

      int centerForNodeByX = (int) (boundsInScreen.getMinX() + (boundsInScreen.getMaxX() - boundsInScreen.getMinX()) / 2.0d);
      int centerForNodeByY = (int) (boundsInScreen.getMinY() + (boundsInScreen.getMaxY() - boundsInScreen.getMinY()) / 2.0d);

      robotAwt.mouseMove(centerForNodeByX, centerForNodeByY);
      robotAwt.mousePress(InputEvent.BUTTON1_DOWN_MASK);
      robotAwt.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

      FXTestUtils.awaitEvents();
   }

   public void compareValues(double expected, double actual) {
      assertEquals(expected, actual);
   }

   private Window getCurrentWindow(){
      return Window.getWindows().get(0);
   }
}
