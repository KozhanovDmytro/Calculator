package com.implemica.controller.util;

import lombok.Getter;

public enum Field {
   TITLE("title"),
   MODE("mode"),

   STANDARD("standard"),
   SCIENTIFIC("scientific"),
   PROGRAMMER("programmer"),
   DATE_CALCULATION("dateCalculation"),
   CURRENCY("currency"),
   VOLUME("volume"),
   LENGTH("length"),
   WEIGHT("weight"),
   TEMPERATURE("temperature"),
   ENERGY("energy"),
   AREA("area"),
   SPEED("speed"),
   TIME("time"),
   POWER("power"),
   DATA("data"),
   PRESSURE("pressure"),
   ANGLE("angle"),

   ABOUT("about"),
   MENU_CONVERTER("menuConverter"),
   MENU_CALCULATOR("menuCalculator"),
   NO_MEMORY("noMemory"),
   NO_LOG("noLog"),
   MEMORY("memory"),
   HISTORY("history"),

   OVERFLOW("overflow"),
   UNDEFINED_RESULT("undefinedResult"),
   DIVIDE_BY_ZERO("divideByZero"),
   INVALID_INPUT("invalidInput");


   @Getter
   private String name;

   Field(String name) {
      this.name = name;
   }
}
