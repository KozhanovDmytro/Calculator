package com.implemica.model.validation;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Validator {

   private final String PATTERN_FOR_NUMBER = ",###.################";

   private final String PATTERN_FOR_EXPONENT = "#.################E0";

   private final String INTEGER_EXPONENT_SEPARATOR = "e+";

   private final String DECIMAL_EXPONENT_SEPARATOR = "e";

   public String showNumber(BigDecimal number){
      number = number.round(MathContext.DECIMAL64);

      DecimalFormatSymbols dfs = new DecimalFormatSymbols();
      dfs.setGroupingSeparator(' ');
      dfs.setDecimalSeparator(',');

      DecimalFormat df = new DecimalFormat();
      df.setGroupingSize(3);

      df.setMinimumIntegerDigits(0);
      df.setMaximumIntegerDigits(16);
      df.setMinimumFractionDigits(0);
      df.setMaximumFractionDigits(16);

//      df.setDecimalSeparatorAlwaysShown(separator);

      if(number.compareTo(new BigDecimal("1e16")) >= 0){
         dfs.setExponentSeparator(INTEGER_EXPONENT_SEPARATOR);
         df.applyPattern(PATTERN_FOR_EXPONENT);
         df.setDecimalSeparatorAlwaysShown(true);
      } else if(number.scale() > 16 && number.compareTo(new BigDecimal("1e-3")) <= 0) {
         dfs.setExponentSeparator(DECIMAL_EXPONENT_SEPARATOR);
         df.applyPattern(PATTERN_FOR_EXPONENT);
         df.setDecimalSeparatorAlwaysShown(true);
      } else {
         df.applyPattern(PATTERN_FOR_NUMBER);
      }

      df.setDecimalFormatSymbols(dfs);

      return df.format(number);
   }

   public String builtOperand(BigDecimal number, boolean separator){
      String result;

      DecimalFormatSymbols dfs = new DecimalFormatSymbols(new Locale("ru", "Ru"));
      dfs.setGroupingSeparator(' ');
      dfs.setDecimalSeparator(',');

      DecimalFormat df = new DecimalFormat();
      df.setGroupingSize(3);

      df.setMinimumIntegerDigits(0);
      df.setMaximumIntegerDigits(16);
      df.setMinimumFractionDigits(0);
      df.setMaximumFractionDigits(16);

//      df.setDecimalSeparatorAlwaysShown(separator);
      df.applyPattern(PATTERN_FOR_NUMBER);

      df.setDecimalFormatSymbols(dfs);

      // for 0.000000000001
      if(number.scale() > 0 && number.scale() <= 16){
         df.applyPattern(PATTERN_FOR_NUMBER + "#");
         BigDecimal temp = new BigDecimal(number.toPlainString() + "1");
         result = df.format(temp);
         result = result.substring(0, result.length() - 1);
      } else {
         result = df.format(number);
      }

      return result + (separator ? ',' : "");
   }

   public String showNumber(String stringOperand) {
      stringOperand = stringOperand.replace(" ", "")
                                    .replace(",", "")
                                    .replace("\\+", "");

      return showNumber(new BigDecimal(stringOperand)).replace(" ", "");
   }
}
