package com.implemica.model.dto;

import com.implemica.model.exceptions.ExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ResponseDto {

   private String result;

   private String operand;

   private String history;

   private String buildOperand;

   private ExceptionType exceptionType;

}
