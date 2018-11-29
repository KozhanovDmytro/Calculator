package com.implemica.view.util;

/**
 * Side on which do resize.
 *
 * @see Coordinates
 *
 * @author Dmytro Kozhanov
 */
public enum Side {
   LEFT,
   RIGHT,
   TOP,
   BOTTOM,

   LEFT_TOP,
   LEFT_BOTTOM,
   RIGHT_TOP,
   RIGHT_BOTTOM;

   /**
    * Function intended for resize and show which way to do resize.
    * @param coordinates coordinates
    * @return desired coordinates
    */
   public int coefficient(Coordinates coordinates) {
      int x = 1;
      int y = 1;
      switch (this) {
         case LEFT:
            x = -1;
            y = 0;
            break;
         case RIGHT:
            y = 0;
            break;
         case TOP:
            y = -1;
            x = 0;
            break;
         case BOTTOM:
            x = 0;
            break;
         case LEFT_TOP:
            x = -1;
            y = -1;
            break;
         case LEFT_BOTTOM:
            x = -1;
            break;
         case RIGHT_TOP:
            y = -1;
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
