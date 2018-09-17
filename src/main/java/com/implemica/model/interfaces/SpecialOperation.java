package com.implemica.model.interfaces;

import java.math.BigDecimal;

public interface SpecialOperation {

   BigDecimal calculate(BigDecimal operand);

   StringBuilder buildHistory(StringBuilder history);
}
