package com.implemica.model.interfaces;

import java.math.BigDecimal;

public interface OperandHistory {

   void add(SpecialOperation operation);

   void clear();

   String buildHistory(BigDecimal operand);
}
