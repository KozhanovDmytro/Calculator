package com.implemica.model.interfaces;

import java.math.BigDecimal;

public interface Memory {

   void add(BigDecimal operand);

   void subtract(BigDecimal operand);

   BigDecimal recall();

   void clear();
}
