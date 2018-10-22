package com.implemica.view;

import com.implemica.view.util.Side;
import com.implemica.view.util.TestFxBase;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import org.junit.jupiter.api.Test;

public class ViewTest extends TestFxBase {

   /*
   *  TODO drag for corners
   *  TODO check buttons
   */
   @Test
   public void dragTest(){
      // left side
      checkDrag(Side.LEFT, -10, 0);
      checkDrag(Side.LEFT, 10, 0);
      checkDrag(Side.LEFT, -20, 0);
      checkDrag(Side.LEFT, 20, 0);
      checkDrag(Side.LEFT, -50, 0);
      checkDrag(Side.LEFT, 50, 0);
      checkDrag(Side.LEFT, -100, 0);
      checkDrag(Side.LEFT, 100, 0);
      checkDrag(Side.LEFT, -150, 0);
      checkDrag(Side.LEFT, 150, 0);
      checkDrag(Side.LEFT, -200, 0);
      checkDrag(Side.LEFT, 200, 0);
      checkDrag(Side.LEFT, -250, 0);
      checkDrag(Side.LEFT, 250, 0);
      checkDrag(Side.LEFT, -300, 0);
      checkDrag(Side.LEFT, 300, 0);
      checkDrag(Side.LEFT, -350, 0);
      checkDrag(Side.LEFT, 350, 0);
      checkDrag(Side.LEFT, -400, 0);
      checkDrag(Side.LEFT, 400, 0);
      checkDrag(Side.LEFT, -500, 0);
      checkDrag(Side.LEFT, 500, 0);

      // right side
      checkDrag(Side.RIGHT, 10, 0);
      checkDrag(Side.RIGHT, -10, 0);
      checkDrag(Side.RIGHT, 20, 0);
      checkDrag(Side.RIGHT, -20, 0);
      checkDrag(Side.RIGHT, 50, 0);
      checkDrag(Side.RIGHT, -50, 0);
      checkDrag(Side.RIGHT, 100, 0);
      checkDrag(Side.RIGHT, -100, 0);
      checkDrag(Side.RIGHT, 150, 0);
      checkDrag(Side.RIGHT, -150, 0);
      checkDrag(Side.RIGHT, 200, 0);
      checkDrag(Side.RIGHT, -200, 0);
      checkDrag(Side.RIGHT, 250, 0);
      checkDrag(Side.RIGHT, -250, 0);
      checkDrag(Side.RIGHT, 300, 0);
      checkDrag(Side.RIGHT, -300, 0);
      checkDrag(Side.RIGHT, 350, 0);
      checkDrag(Side.RIGHT, -350, 0);
      checkDrag(Side.RIGHT, 400, 0);
      checkDrag(Side.RIGHT, -400, 0);
      checkDrag(Side.RIGHT, 500, 0);
      checkDrag(Side.RIGHT, -500, 0);

      // top side
      checkDrag(Side.TOP, 0, -20);
      checkDrag(Side.TOP, 0, 20);
      checkDrag(Side.TOP, 0, -50);
      checkDrag(Side.TOP, 0, 50);
      checkDrag(Side.TOP, 0, -60);
      checkDrag(Side.TOP, 0, 60);

      // bottom side
      checkDrag(Side.BOTTOM, 0, 20);
      checkDrag(Side.BOTTOM, 0, -20);
      checkDrag(Side.BOTTOM, 0, 50);
      checkDrag(Side.BOTTOM, 0, -50);
      checkDrag(Side.BOTTOM, 0, 60);
      checkDrag(Side.BOTTOM, 0, -60);

      checkDrag(Side.LEFT_TOP, -10, -10);
      checkDrag(Side.LEFT_TOP, 10, 10);
      checkDrag(Side.LEFT_TOP, -50, -10);
      checkDrag(Side.LEFT_TOP, 50, 10);
      checkDrag(Side.LEFT_TOP, -100, -20);
      checkDrag(Side.LEFT_TOP, 100, 20);
      checkDrag(Side.LEFT_TOP, -200, -30);
      checkDrag(Side.LEFT_TOP, 200, 30);
      checkDrag(Side.LEFT_TOP, -500, -50);
      checkDrag(Side.LEFT_TOP, 500, 50);

      checkDrag(Side.RIGHT_TOP, 10, -10);
      checkDrag(Side.RIGHT_TOP, -10, 10);
      checkDrag(Side.RIGHT_TOP, 50, -20);
      checkDrag(Side.RIGHT_TOP, -50, 20);
      checkDrag(Side.RIGHT_TOP, 100, -20);
      checkDrag(Side.RIGHT_TOP, -100, 20);
      checkDrag(Side.RIGHT_TOP, 150, -30);
      checkDrag(Side.RIGHT_TOP, -150, 30);
      checkDrag(Side.RIGHT_TOP, 200, -40);
      checkDrag(Side.RIGHT_TOP, -200, 40);
      checkDrag(Side.RIGHT_TOP, 400, -50);
      checkDrag(Side.RIGHT_TOP, -400, 50);
      checkDrag(Side.RIGHT_TOP, 500, -50);
      checkDrag(Side.RIGHT_TOP, -500, 50);

      checkDrag(Side.RIGHT_BOTTOM, 10, 10);
      checkDrag(Side.RIGHT_BOTTOM, -10, -10);
      checkDrag(Side.RIGHT_BOTTOM, 50, 30);
      checkDrag(Side.RIGHT_BOTTOM, -50, -30);
      checkDrag(Side.RIGHT_BOTTOM, 100, 40);
      checkDrag(Side.RIGHT_BOTTOM, -100, -40);
      checkDrag(Side.RIGHT_BOTTOM, 200, 50);
      checkDrag(Side.RIGHT_BOTTOM, -200, -50);
      checkDrag(Side.RIGHT_BOTTOM, 300, 60);
      checkDrag(Side.RIGHT_BOTTOM, -300, -60);
      checkDrag(Side.RIGHT_BOTTOM, 500, 70);
      checkDrag(Side.RIGHT_BOTTOM, -500, -70);

      checkDrag(Side.LEFT_BOTTOM, -10, 10);
      checkDrag(Side.LEFT_BOTTOM, 10, -10);
      checkDrag(Side.LEFT_BOTTOM, -50, 30);
      checkDrag(Side.LEFT_BOTTOM, 50, -30);
      checkDrag(Side.LEFT_BOTTOM, -100, 40);
      checkDrag(Side.LEFT_BOTTOM, 100, -40);
      checkDrag(Side.LEFT_BOTTOM, -200, 50);
      checkDrag(Side.LEFT_BOTTOM, 200, -50);
      checkDrag(Side.LEFT_BOTTOM, -300, 60);
      checkDrag(Side.LEFT_BOTTOM, 300, -60);
      checkDrag(Side.LEFT_BOTTOM, -500, 70);
      checkDrag(Side.LEFT_BOTTOM, 500, -70);
   }

   @Test
   public void moveWindowTest() {
      double maxX = Screen.getPrimary().getBounds().getMaxX() - 1.0d;
      double maxY = Screen.getPrimary().getBounds().getMaxY() - 1.0d;

      moveWindow(new Point2D[] {
              new Point2D(0.0d, 0.0d),
              new Point2D(maxX, 0.0d),
              new Point2D(maxX, maxY),
              new Point2D(0.0d, maxY),
              new Point2D(0.0d, 0.0d),
              new Point2D(maxX, maxY),
              new Point2D(maxX, 0.0d),
              new Point2D(0.0d, maxY)
      });
   }

   @Test
   public void checkExtraInfoFullTest() {
      AnchorPane extraInfoFull = findBy("#extraInfoFull");
      Button fullScreen = findBy("#full");

      compareValues(0.0d, extraInfoFull.getWidth());
      checkDrag(Side.LEFT, -237.0d, 0.0d);
      compareValues(0.0d, extraInfoFull.getWidth());
      checkDrag(Side.LEFT, -1.0d, 0.0d);
      compareValues(240.0d, extraInfoFull.getWidth());
      checkDrag(Side.LEFT, -1.0d, 0.0d);
      compareValues(241.0d, extraInfoFull.getWidth());
      checkDrag(Side.LEFT, 239.0d, 0.0d);

      compareValues(0.0d, extraInfoFull.getWidth());
      checkDrag(Side.RIGHT, 237.0d, 0.0d);
      compareValues(0.0d, extraInfoFull.getWidth());
      checkDrag(Side.RIGHT, 1.0d, 0.0d);
      compareValues(240.0d, extraInfoFull.getWidth());
      checkDrag(Side.RIGHT, 1.0d, 0.0d);
      compareValues(241.0d, extraInfoFull.getWidth());
      checkDrag(Side.RIGHT, -239.0d, 0.0d);

      compareValues(0.0d, extraInfoFull.getWidth());
      clickOn(fullScreen);
      compareValues(325.0d, extraInfoFull.getWidth());
      clickOn(fullScreen);
   }

   @Test
   public void dropDownPaneTest() {
      Button menu = findBy("#menuBtn");
      Button showMemory = findBy("#showMemory");
      Button btn1 = findBy("#btn1");
      Button addMemory = findBy("#addMemory");
      Button clearMemory = findBy("#clearMemory");

      Pane hideMenu = findBy("#hideMenu");
      Pane hideMemoryField = findBy("#hideMemoryField");

      clickOn(menu);
      clickOn(hideMenu);

      clickOn(btn1);
      clickOn(addMemory);
      clickOn(showMemory);
      clickOn(hideMemoryField);
      clickOn(clearMemory);
   }
}
