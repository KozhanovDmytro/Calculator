package com.implemica.model.dto;

import com.implemica.model.exceptions.ExceptionType;
import com.implemica.model.history.History;

import java.math.BigDecimal;

/**
 * The class uses for make a response from model.
 *
 * @see BigDecimal
 * @see History
 * @see ExceptionType
 *
 * @author Dmytro Kozhanov
 */
public class ResponseDto {

   /** This field stores result. */
   private BigDecimal result;

   /** This field stores current operand. */
   private BigDecimal operand;

   /** This field stores history. */
   private History history;

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

   public History getHistory() {
      return history;
   }

   public void setHistory(History history) {
      this.history = history;
   }
}
