package com.implemica.model.validation;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Validator {

   private final String PATTERN_FOR_NUMBER = ",###.#################";

   private final String PATTERN_FOR_EXPONENT = "#.E0";

   private final String INTEGER_EXPONENT_SEPARATOR = "e+";

   private final String DECIMAL_EXPONENT_SEPARATOR = "e";

   public String showNumber(BigDecimal number){
      return showNumber(number, false);
   }

   public String showNumber(BigDecimal number, boolean separator){
      String result;

      DecimalFormatSymbols dfs = new DecimalFormatSymbols(new Locale("ru", "Ru"));
      dfs.setGroupingSeparator(' ');
      dfs.setDecimalSeparator(',');

      DecimalFormat df = new DecimalFormat();
      df.setGroupingSize(3);
      df.setParseBigDecimal(true);

      df.setMinimumIntegerDigits(0);
      df.setMaximumIntegerDigits(16);
      df.setMinimumFractionDigits(0);
      df.setMaximumFractionDigits(16);

      df.setDecimalSeparatorAlwaysShown(separator);

      if(number.compareTo(new BigDecimal("1e16")) >= 0){
         dfs.setExponentSeparator(INTEGER_EXPONENT_SEPARATOR);
         df.applyPattern(PATTERN_FOR_EXPONENT);
      } else if(number.scale() > 16) {
         dfs.setExponentSeparator(DECIMAL_EXPONENT_SEPARATOR);
         df.applyPattern(PATTERN_FOR_EXPONENT);
      } else if(number.scale() > 0){
         df.applyPattern(PATTERN_FOR_NUMBER);
      }

      df.setDecimalFormatSymbols(dfs);

      if(number.scale() > 0 && number.scale() <= 16){
         BigDecimal temp = new BigDecimal(number.toPlainString() + "1");
         result = df.format(temp);
         result = result.substring(0, result.length() - 1);
      } else {
         result = df.format(number);
      }

      return result;
   }

   public String showNumberForSystem(String number){
      return number.replaceAll("\\s", "").replaceAll(",", ".");
   }
}
