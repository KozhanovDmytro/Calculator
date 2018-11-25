package com.implemica.controller.util;

/**
 * The enumeration of text fields for each {@link javafx.scene.control.Control}
 * on view side.
 *
 * @see com.implemica.view.Launcher
 *
 * @author Dmytro Kozhanov
 */
public enum Field {
   // labels
   TITLE("title"),
   MODE("mode"),

   // buttons in menu
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

   // labels in menu
   MENU_CONVERTER("menuConverter"),
   MENU_CALCULATOR("menuCalculator"),

   // extra field
   NO_MEMORY("noMemory"),
   NO_LOG("noLog"),
   MEMORY("memory"),
   HISTORY("history"),

   // exceptions
   OVERFLOW("overflow"),
   UNDEFINED_RESULT("undefinedResult"),
   DIVIDE_BY_ZERO("divideByZero"),
   INVALID_INPUT("invalidInput");

   private String name;

   Field(String name) {
      this.name = name;
   }

   public String getName() {
      return name;
   }
}
