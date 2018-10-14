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

      checkDrag(Side.LEFT_TOP, -20, -20);
      checkDrag(Side.LEFT_TOP, -40, -25);

      checkDrag(Side.RIGHT_TOP, 10, -10);
      checkDrag(Side.RIGHT_TOP, 50, -10);

      checkDrag(Side.RIGHT_BOTTOM, 10, 10);

      checkDrag(Side.LEFT_BOTTOM, -10, 10);
   }
}
