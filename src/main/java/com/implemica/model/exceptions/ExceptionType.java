package com.implemica.model.exceptions;

/**
 * That enumeration contains type of exception.
 *
 * @author Dmytro Kozhanov
 */
public enum ExceptionType {
   /**
    * NOTHING - means that exception wasn't thrown. This is needed for
    * notify a class (which use this model) that with model everything OK.
    */
   NOTHING,

   /**
    * OVERFLOW - means that some number out of range.
    *
    *    [-MAX .. -MIN] and 0 and [MIN .. MAX] , where
    *
    * MAX - is the MAX number in model.
    * MIN - is the MIN positive number in model.
    *
    * For range [-MIN .. MIN] that's zone of death where
    * exception must be thrown especially for ZERO.
    *
    * Specific values describes in {@link com.implemica.model.calculator.Container}.
    */
   OVERFLOW,

   /**
    * UNDEFINED_RESULT - Special case for check this condition 0 / 0.
    */
   UNDEFINED_RESULT,

   /**
    * DIVIDE_BY_ZERO - thrown when someone divide some number by ZERO.
    */
   DIVIDE_BY_ZERO,

   /**
    * INVALID_INPUT - throws when square root accepted a negative number.
    */
   INVALID_INPUT
}
