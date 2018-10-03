package com.implemica.model.interfaces;

import com.implemica.model.exceptions.UndefinedResultException;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface Operation {

   BigDecimal calculate(BigDecimal result) throws UndefinedResultException;

}
