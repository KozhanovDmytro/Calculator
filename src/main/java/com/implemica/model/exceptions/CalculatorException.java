package com.implemica.model.exceptions;

/**
 * The class uses for throws exceptions if it needed.
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
    * @param exceptionType desired {@link ExceptionType} needed for information what
    *                      kind of exception was thrown.
    * @throws IllegalArgumentException if {@link #exceptionType} equals to
    *  {@link ExceptionType#NOTHING} it means that exception wasn't thrown and
    *  notify that with model everything is OK. That does not matter to use this type
    *  in this exception.
    */
   public CalculatorException(ExceptionType exceptionType) {
      super(exceptionType + " was thrown. ");

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
