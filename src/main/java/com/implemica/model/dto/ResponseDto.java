package com.implemica.model.dto;

import com.implemica.model.exceptions.ExceptionType;
import com.implemica.model.history.MainHistory;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ResponseDto {

   private BigDecimal result;

   private BigDecimal operand;

   private MainHistory history;

   private boolean isSeparated;

   private ExceptionType exceptionType;

}
