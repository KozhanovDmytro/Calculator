package com.implemica.model.dto;

import com.implemica.model.exceptions.ExceptionType;
import com.implemica.model.history.MainHistory;

import java.math.BigDecimal;

/**
 * The class uses for make a response from model.
 *
 * @see BigDecimal
 * @see MainHistory
 * @see ExceptionType
 *
 * @author Dmytro Kozhanov
 */
public class ResponseDto {

   private BigDecimal result;

   private BigDecimal operand;

   private MainHistory history;

   private boolean isSeparated;

   private ExceptionType exceptionType;

   /* accessors for each field */

   public BigDecimal getResult() {
      return result;
   }

   public void setResult(BigDecimal result) {
      this.result = result;
   }

   public BigDecimal getOperand() {
      return operand;
   }

   public void setOperand(BigDecimal operand) {
      this.operand = operand;
   }

   public MainHistory getHistory() {
      return history;
   }

   public void setHistory(MainHistory history) {
      this.history = history;
   }

   public boolean isSeparated() {
      return isSeparated;
   }

   public void setSeparated(boolean separated) {
      isSeparated = separated;
   }

   public ExceptionType getExceptionType() {
      return exceptionType;
   }

   public void setExceptionType(ExceptionType exceptionType) {
      this.exceptionType = exceptionType;
   }
}
