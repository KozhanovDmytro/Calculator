package com.implemica.controller.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * This class needed for interpret BigDecimal number to comfortable for user.
 *
 * @see BigDecimal
 * @see DecimalFormat
 * @see DecimalFormatSymbols
 *
 * @author Dmytro Kozhanov
 */
public class Validator {

   /** Pattern for number without exponent separator. */
   private final String PATTERN_FOR_NUMBER = ",###.################";

   /** Pattern for history number without exponent separator. */
   private final String PATTERN_FOR_HISTORY_NUMBER = "###.################";

   /** Pattern for number with exponent separator. */
   private final String PATTERN_FOR_EXPONENT = "#.################E0";

   /** Exponent separator for integer number. */
   private final String INTEGER_EXPONENT_SEPARATOR = "e+";

   /** Exponent separator for decimal number. */
   private final String DECIMAL_EXPONENT_SEPARATOR = "e";

   /** Comma. */
   public final String SEPARATOR = ".";

   /** Space. */
   private final char SPACE = ' ';

   /** This pattern using for build pattern for decimal number like that 0.000 */
   private final String PATTERN_FOR_BUILD_NEW_PATTERN = ",##0.";

   /** This pattern using for build pattern*/
   private final char ADDITIONAL_PART = '0';

   /** Just empty string. */
   private final String NOTHING = "";

   /**
    * That numbers mean start position of range where the number show as
    * a plain number without {@link #DECIMAL_EXPONENT_SEPARATOR}
    */
   private final BigDecimal START_PLAIN_NUMBER = new BigDecimal("1e-3");

   /**
    * That numbers mean end position of range where the number show as
    * a plain number without {@link #DECIMAL_EXPONENT_SEPARATOR}
    */
   private final BigDecimal END_PLAIN_NUMBER = new BigDecimal("1e16");

   /** Minimum integer digits. */
   private final int MINIMUM_INTEGER_DIGITS = 0;

   /** Maximum integer digits. */
   private final int MAXIMUM_INTEGER_DIGITS = 16;

   /** Minimum fraction digits. */
   private final int MINIMUM_FRACTION_DIGITS = 0;

   /** Maximum fraction digits. */
   private final int MAXIMUM_FRACTION_DIGITS = 16;

   /**
    * Shows comfortable number for user.
    *
    * @param number number to show.
    * @return comfortable number for user
    */
   public String showNumber(BigDecimal number) {
      number = number.round(MathContext.DECIMAL64);

      DecimalFormat df = new DecimalFormat();
      resolvePattern(number, df, false);

      return df.format(number);
   }

   /**
    * Shows comfortable built operand for user.
    *
    * @param number number to show.
    * @param separator flag which mean that the last character of number is comma.
    * @return comfortable number for user
    */
   private String builtOperand(BigDecimal number, boolean separator) {
      DecimalFormat df = new DecimalFormat(PATTERN_FOR_NUMBER, buildDecimalFormatSymbols());

      basicSettingsForDecimalFormatter(df);

      if (number.scale() > MINIMUM_FRACTION_DIGITS) {
         df.applyPattern(getPatternForDecimalNumber(number.scale()));
      }

      return df.format(number) + (separator ? ',' : NOTHING);
   }

   public String builtOperand(StringBuilder number) {
      boolean isSeparator = number.charAt(number.length() - 1) == '.';

      return builtOperand(new BigDecimal(number.toString()), isSeparator);
   }

   /**
    * Function gets pattern for decimal number.
    *
    * @param scale counts of character after comma.
    * @return built pattern.
    */
   private String getPatternForDecimalNumber(int scale) {
      String result = PATTERN_FOR_NUMBER;
      if(scale <= 16) {
         StringBuilder newPattern = new StringBuilder(PATTERN_FOR_BUILD_NEW_PATTERN);
         for (int i = 0; i < scale; i++) {
            newPattern.append(ADDITIONAL_PART);
         }
         result = newPattern.toString();
      }

      return result;
   }

   /**
    * Shows comfortable number for history.
    *
    * @param number number to show.
    * @return comfortable number for history
    */
   String showNumberForHistory(BigDecimal number) {
      number = number.round(MathContext.DECIMAL64);

      DecimalFormat df = new DecimalFormat();
      df.setGroupingSize(0);
      resolvePattern(number, df, true);

      return df.format(number);
   }

   /**
    * This function resolve which pattern will be use to show comfortable number and
    * set it to the instance of {@link DecimalFormat}.
    *
    * @param number number to show.
    * @param df instance of {@link DecimalFormat}
    * @param isHistory the flag which mean that this function uses for history ot not.
    */
   private void resolvePattern(BigDecimal number, DecimalFormat df, boolean isHistory) {
      DecimalFormatSymbols dfs = buildDecimalFormatSymbols();

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
         df.applyPattern(isHistory ? PATTERN_FOR_HISTORY_NUMBER : PATTERN_FOR_NUMBER);
      }

      df.setDecimalFormatSymbols(dfs);
   }

   /**
    * Function make a settings for instance of {@link DecimalFormat}
    * @param df the instance of {@link DecimalFormat}
    */
   private void basicSettingsForDecimalFormatter(DecimalFormat df) {
      df.setMinimumIntegerDigits(MINIMUM_INTEGER_DIGITS);
      df.setMaximumIntegerDigits(MAXIMUM_INTEGER_DIGITS);
      df.setMinimumFractionDigits(MINIMUM_FRACTION_DIGITS);
      df.setMaximumFractionDigits(MAXIMUM_FRACTION_DIGITS);
   }

   /**
    * Creates an instance {@link DecimalFormatSymbols}
    *
    * @return an instance.
    */
   private DecimalFormatSymbols buildDecimalFormatSymbols() {
      DecimalFormatSymbols dfs = new DecimalFormatSymbols();
      dfs.setGroupingSeparator(SPACE);
      dfs.setDecimalSeparator(',');

      return dfs;
   }
}