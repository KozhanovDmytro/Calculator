package com.implemica.view.util;

import com.implemica.view.Launcher;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.stage.Window;
import org.junit.jupiter.api.BeforeAll;
import org.testfx.api.FxRobot;
import org.testfx.robot.Motion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.framework.junit.ApplicationTest.launch;

public class TestFxBase {

   protected static FxRobot robot = new FxRobot();

   @BeforeAll
   public static void setUp() throws Exception {
      launch(Launcher.class);
      robot = robot.targetWindow(robot.listTargetWindows().get(0));
   }

   private Point2D getBound(Side side) {
      Window window = robot.targetWindow();
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

   public void click(String query) {
      robot.clickOn(query);
   }

   public void checkDrag(Side side, double x, double y) {
      double initialHeight = robot.targetWindow().getHeight();
      double initialWidth = robot.targetWindow().getWidth();

      drag(getBound(side), x, y);

      // check width
      assertEquals(initialWidth + x * side.coefficient()[Coordinates.X.ordinal()], robot.targetWindow().getWidth());

      // check height
      assertEquals(initialHeight + y * side.coefficient()[Coordinates.Y.ordinal()], robot.targetWindow().getHeight());
   }

   private void drag(Point2D start, double x, double y) {
      robot.drag(start.getX(), start.getY(), MouseButton.PRIMARY);
      robot.moveBy(x, y);
      robot.drop();
   }


}
