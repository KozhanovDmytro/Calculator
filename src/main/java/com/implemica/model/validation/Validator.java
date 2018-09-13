package com.implemica.model.validation;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Validator {

   private final String PATTERN_FOR_NUMBER = ",###.################";

   private final String PATTERN_FOR_EXPONENT = "#.E0";

   private DecimalFormat df;

   public String showNumber(BigDecimal number){
      return showNumber(number, false);
   }

   public String showNumber(BigDecimal number, boolean separator){
      // TODO this.
      if(separator){
         df = new DecimalFormat("#.");
         return df.format(number);
      }

      if(number.abs().compareTo(new BigDecimal(10000000000000000L)) < 0)
         df = new DecimalFormat(PATTERN_FOR_NUMBER);
      else
         df = new DecimalFormat(PATTERN_FOR_EXPONENT);

      DecimalFormatSymbols dfs = new DecimalFormatSymbols();

      dfs.setExponentSeparator("e");


      df.setDecimalFormatSymbols(dfs);
      return df.format(number).replace((char) 160, (char) 32);
   }

   public String showComfortableNumber(BigDecimal number, boolean isSeparated) {
      StringBuilder stringNumber = new StringBuilder(number.toString());
      String result;

      if(isSeparated){
         DecimalFormat df = new DecimalFormat("#.");
         return df.format(number);
      }

      if(!stringNumber.toString().contains(".")) {
         StringBuilder reverse = stringNumber.reverse();
         for (int i = 0; i < reverse.length(); i++) {
            if (i % 4 == 0) {
               reverse.insert(i, " ");
            }
         }
         result = reverse.reverse().delete(reverse.length() - 1, reverse.length()).toString();
      } else {
         // TODO format for E and set rounding mode
         result = stringNumber.toString().replaceAll("\\.", ",");
      }

      return result;
   }

   public String showNumberForSystem(String number){
      return number.replaceAll("\\s", "").replaceAll(",", ".");
   }
}
