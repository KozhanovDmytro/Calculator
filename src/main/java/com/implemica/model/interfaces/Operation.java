package com.implemica.model.interfaces;

import com.implemica.model.exceptions.InvalidInputException;
import com.implemica.model.exceptions.UndefinedResultException;

import java.math.BigDecimal;

public interface Operation {

   BigDecimal calculate(BigDecimal result) throws UndefinedResultException, InvalidInputException;

}
