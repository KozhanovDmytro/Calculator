package com.implemica.view.util;

public enum Side {
   LEFT,
   RIGHT,
   TOP,
   BOTTOM,

   LEFT_TOP,
   LEFT_BOTTOM,
   RIGHT_TOP,
   RIGHT_BOTTOM;

   public int coefficient(Coordinates coordinates) {
      int x = 1;
      int y = 1;
      switch (this) {
         case LEFT:
            x = -1;
            y = 0;
            break;
         case RIGHT:
            x = 1;
            y = 0;
            break;
         case TOP:
            y = -1;
            x = 0;
            break;
         case BOTTOM:
            y = 1;
            x = 0;
            break;
         case LEFT_TOP:
            x = -1;
            y = -1;
            break;
         case LEFT_BOTTOM:
            x = -1;
            y = 1;
            break;
         case RIGHT_TOP:
            x = 1;
            y = -1;
            break;
         case RIGHT_BOTTOM:
            x = 1;
            y = 1;
            break;
      }
      if (coordinates == Coordinates.X) {
         return x;
      } else if (coordinates == Coordinates.Y) {
         return y;
      } else {
         throw new IllegalArgumentException();
      }
   }
}
