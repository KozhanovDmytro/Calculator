package com.implemica.model.validation;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Validator {

   private final String PATTERN_FOR_NUMBER = ",###.################";

   private final String PATTERN_FOR_NUMBER_WITHOUT_GROUP = "###.################";

   private final String PATTERN_FOR_EXPONENT = "#.################E0";

   private final String INTEGER_EXPONENT_SEPARATOR = "e+";

   private final String DECIMAL_EXPONENT_SEPARATOR = "e";

   public final char SEPARATOR = ',';

   private final char SPACE = ' ';

   private final char SHARP = '#';

   private final char ADDITIONAL_PART = '1';

   private final String NOTHING = "";

   /**
    * That numbers mean start and end position of range where the number show as
    * a plain number without {@link #DECIMAL_EXPONENT_SEPARATOR}
    */
   private final BigDecimal START_PLAIN_NUMBER = new BigDecimal("1e-3");
   private final BigDecimal END_PLAIN_NUMBER = new BigDecimal("1e16");

   private final int MINIMUM_INTEGER_DIGITS = 0;

   private final int MAXIMUM_INTEGER_DIGITS = 16;

   private final int MINIMUM_FRACTION_DIGITS = 0;

   private final int MAXIMUM_FRACTION_DIGITS = 16;

   public String showNumber(BigDecimal number) {
      number = number.round(MathContext.DECIMAL64);

      DecimalFormat df = new DecimalFormat();
      resolvePattern(number, df, false);

      return df.format(number);
   }

   public String builtOperand(BigDecimal number, boolean separator) {
      String result;
      DecimalFormat df = new DecimalFormat();

      DecimalFormatSymbols dfs = new DecimalFormatSymbols();
      dfs.setGroupingSeparator(SPACE);
      dfs.setDecimalSeparator(SEPARATOR);

      basicSettingsForDecimalFormatter(df);

      df.setDecimalFormatSymbols(dfs);

      df.applyPattern(PATTERN_FOR_NUMBER);

      // for 0.000000000001
      if (number.scale() > MINIMUM_FRACTION_DIGITS) {
         df.applyPattern(PATTERN_FOR_NUMBER + SHARP);
         BigDecimal temp = new BigDecimal(number.toPlainString() + ADDITIONAL_PART);
         result = df.format(temp);
         result = result.substring(0, result.length() - 1);
      } else {
         result = df.format(number);
      }

      return result + (separator ? SEPARATOR : NOTHING);
   }

   public String showNumberForHistory(BigDecimal number) {
      number = number.round(MathContext.DECIMAL64);

      DecimalFormat df = new DecimalFormat();
      df.setGroupingSize(0);
      resolvePattern(number, df, true);

      return df.format(number);
   }

   private DecimalFormat resolvePattern(BigDecimal number, DecimalFormat df, boolean isHistory) {
      DecimalFormatSymbols dfs = new DecimalFormatSymbols();
      dfs.setGroupingSeparator(SPACE);
      dfs.setDecimalSeparator(SEPARATOR);

      basicSettingsForDecimalFormatter(df);

      if (number.abs().compareTo(END_PLAIN_NUMBER) >= 0) {
         dfs.setExponentSeparator(INTEGER_EXPONENT_SEPARATOR);
         df.applyPattern(PATTERN_FOR_EXPONENT);
         df.setDecimalSeparatorAlwaysShown(true);

      } else if (number.scale() > MAXIMUM_FRACTION_DIGITS && number.abs().compareTo(START_PLAIN_NUMBER) <= MINIMUM_FRACTION_DIGITS) {
         dfs.setExponentSeparator(DECIMAL_EXPONENT_SEPARATOR);
         df.applyPattern(PATTERN_FOR_EXPONENT);
         df.setDecimalSeparatorAlwaysShown(true);

      } else {
         df.applyPattern(isHistory ? PATTERN_FOR_NUMBER_WITHOUT_GROUP : PATTERN_FOR_NUMBER);
      }

      df.setDecimalFormatSymbols(dfs);
      return df;
   }

   private void basicSettingsForDecimalFormatter(DecimalFormat df) {
      df.setMinimumIntegerDigits(MINIMUM_INTEGER_DIGITS);
      df.setMaximumIntegerDigits(MAXIMUM_INTEGER_DIGITS);
      df.setMinimumFractionDigits(MINIMUM_FRACTION_DIGITS);
      df.setMaximumFractionDigits(MAXIMUM_FRACTION_DIGITS);
   }
}