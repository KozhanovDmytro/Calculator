package com.implemica.view;

import com.implemica.view.util.Side;
import com.implemica.view.util.TestFxBase;
import org.junit.jupiter.api.Test;

public class ViewTest extends TestFxBase {

   @Test
   public void dragTest(){
      // left side
      checkDrag(Side.LEFT, -10, 0);
      checkDrag(Side.LEFT, 10, 0);
      checkDrag(Side.LEFT, -50, 0);
      checkDrag(Side.LEFT, 50, 0);
      checkDrag(Side.LEFT, -100, 0);
      checkDrag(Side.LEFT, 100, 0);
      checkDrag(Side.LEFT, -500, 0);
      checkDrag(Side.LEFT, 500, 0);

      // right side
      checkDrag(Side.RIGHT, 10, 0);
      checkDrag(Side.RIGHT, -10, 0);
      checkDrag(Side.RIGHT, 50, 0);
      checkDrag(Side.RIGHT, -50, 0);
      checkDrag(Side.RIGHT, 100, 0);
      checkDrag(Side.RIGHT, -100, 0);
      checkDrag(Side.RIGHT, 500, 0);
      checkDrag(Side.RIGHT, -500, 0);

      // top side
      checkDrag(Side.TOP, 0, -10);
      checkDrag(Side.TOP, 0, 10);
      checkDrag(Side.TOP, 0, -20);
      checkDrag(Side.TOP, 0, 20);

      // bottom side
      checkDrag(Side.BOTTOM, 0, 10);
      checkDrag(Side.BOTTOM, 0, -10);
      checkDrag(Side.BOTTOM, 0, 20);
      checkDrag(Side.BOTTOM, 0, -20);

      // left-top side
      checkDrag(Side.LEFT_TOP, -20, -20);
      checkDrag(Side.LEFT_TOP, 20, 20);
      checkDrag(Side.LEFT_TOP, -100, -40);
      checkDrag(Side.LEFT_TOP, 100, 40);

      checkDrag(Side.LEFT_TOP, -10, 0);
      checkDrag(Side.LEFT_TOP, 10, 0);
      checkDrag(Side.LEFT_TOP, -50, 0);
      checkDrag(Side.LEFT_TOP, 50, 0);

      checkDrag(Side.LEFT_TOP, 0, -10);
      checkDrag(Side.LEFT_TOP, 0, 10);
      checkDrag(Side.LEFT_TOP, 0, -50);
      checkDrag(Side.LEFT_TOP, 0, 50);

      // right-top side
      checkDrag(Side.RIGHT_TOP, 80, -40);
      checkDrag(Side.RIGHT_TOP, -80, 40);
      checkDrag(Side.RIGHT_TOP, 100, -10);
      checkDrag(Side.RIGHT_TOP, -100, 10);

      checkDrag(Side.RIGHT_TOP, 50, 0);
      checkDrag(Side.RIGHT_TOP, -50, 0);
      checkDrag(Side.RIGHT_TOP, 100, 0);
      checkDrag(Side.RIGHT_TOP, -100, 0);

      checkDrag(Side.RIGHT_TOP, 0, -20);
      checkDrag(Side.RIGHT_TOP, 0, 20);
      checkDrag(Side.RIGHT_TOP, 0, -60);
      checkDrag(Side.RIGHT_TOP, 0, 60);

      // right-bottom side
      checkDrag(Side.RIGHT_BOTTOM, 25, 50);
      checkDrag(Side.RIGHT_BOTTOM, -25, -50);
      checkDrag(Side.RIGHT_BOTTOM, 50, 25);
      checkDrag(Side.RIGHT_BOTTOM, -50, -25);

      checkDrag(Side.RIGHT_BOTTOM, 60, 0);
      checkDrag(Side.RIGHT_BOTTOM, -60, 0);
      checkDrag(Side.RIGHT_BOTTOM, 120, 0);
      checkDrag(Side.RIGHT_BOTTOM, -120, 0);

      checkDrag(Side.RIGHT_BOTTOM, 0, 20);
      checkDrag(Side.RIGHT_BOTTOM, 0, -20);
      checkDrag(Side.RIGHT_BOTTOM, 0, 50);
      checkDrag(Side.RIGHT_BOTTOM, 0, -50);

      // left-bottom side
      checkDrag(Side.LEFT_BOTTOM, -60, 20);
      checkDrag(Side.LEFT_BOTTOM, 60, -20);
      checkDrag(Side.LEFT_BOTTOM, -20, 80);
      checkDrag(Side.LEFT_BOTTOM, 20, -80);

      checkDrag(Side.LEFT_BOTTOM, -100, 0);
      checkDrag(Side.LEFT_BOTTOM, 100, 0);
      checkDrag(Side.LEFT_BOTTOM, -150, 0);
      checkDrag(Side.LEFT_BOTTOM, 150, 0);

      checkDrag(Side.LEFT_BOTTOM, 0, 20);
      checkDrag(Side.LEFT_BOTTOM, 0, -20);
      checkDrag(Side.LEFT_BOTTOM, 0, 60);
      checkDrag(Side.LEFT_BOTTOM, 0, -60);
   }
}
