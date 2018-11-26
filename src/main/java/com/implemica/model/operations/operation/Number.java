package com.implemica.model.operations.operation;

/**
 * The enumeration of of digit.
 *
 * @author Dmytro Kozhanov
 */
public enum Number {
   ZERO,
   ONE,
   TWO,
   THREE,
   FOUR,
   FIVE,
   SIX,
   SEVEN,
   EIGHT,
   NINE;

   /**
    * Function for translating digit to specific character.
    * It's needed for build operand.
    *
    * @return representation digit as character.
    */
   public char translate() {
      switch (this) {
         case ZERO:  return '0';
         case ONE:   return '1';
         case TWO:   return '2';
         case THREE: return '3';
         case FOUR:  return '4';
         case FIVE:  return '5';
         case SIX:   return '6';
         case SEVEN: return '7';
         case EIGHT: return '8';
         case NINE:  return '9';
      }
      throw new IllegalArgumentException();
   }
}
