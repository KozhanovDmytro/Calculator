package com.implemica.model.exceptions;

/**
 * The class uses for throws exceptions if it needs.
 *
 * @see ExceptionType
 *
 * @author Dmytro Kozhanov
 */
public class CalculatorException extends Exception {

   /** The instance of {@link ExceptionType} which contains type of exception. */
   private ExceptionType exceptionType;

   /**
    * Constructor.
    *
    * @param exceptionType
    * @throws IllegalArgumentException if {@link #exceptionType} equals to
    *  {@link ExceptionType#NOTHING} it means that exception wasn't thrown and
    *  notify that with model everything is OK. That does not matter to use this type
    *  in this exception.
    */
   public CalculatorException(ExceptionType exceptionType) {
      if(exceptionType == ExceptionType.NOTHING) {
         throw new IllegalArgumentException();
      }

      this.exceptionType = exceptionType;
   }

   /**
    * Getter.
    * @return the instance of @link {@link #exceptionType}.
    */
   public ExceptionType getExceptionType() {
      return exceptionType;
   }
}
