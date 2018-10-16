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
   }
}
