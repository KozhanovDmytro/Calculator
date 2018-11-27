package com.implemica.model.validation;

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
   public final char SEPARATOR = ',';

   /** Space. */
   private final char SPACE = ' ';

   /** Sharp character for build pattern. It needed for {@link #ADDITIONAL_PART} for digital number. */
   private final char SHARP = '#';

   /** That's needed for build operand for number like that: 0.00000 */
   private final char ADDITIONAL_PART = '1';

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
   public String builtOperand(BigDecimal number, boolean separator) {
      String result;
      DecimalFormat df = new DecimalFormat();

      DecimalFormatSymbols dfs = new DecimalFormatSymbols();
      dfs.setGroupingSeparator(SPACE);
      dfs.setDecimalSeparator(SEPARATOR);

      basicSettingsForDecimalFormatter(df);

      df.setDecimalFormatSymbols(dfs);

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

   /**
    * Shows comfortable number for history.
    *
    * @param number number to show.
    * @return comfortable number for history
    */
   public String showNumberForHistory(BigDecimal number) {
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
}