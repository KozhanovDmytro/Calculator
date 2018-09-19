package com.implemica.model.interfaces;

import java.math.BigDecimal;

public interface SpecialOperation {

   BigDecimal calculate(BigDecimal operand);

   String buildHistory(String history);
}
