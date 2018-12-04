package com.implemica.controller.util;

import com.implemica.view.util.Side;

/**
 * The enumeration contains {@link Node}s, his name in property file and id in FXML file.
 *
 * @see Node
 *
 * @author Kozhanov
 */
public enum Node {

   /* Labels. */
   TITLE("#title", "title"),
   MODE("#mode", "mode"),

   /* Labels in menu. */
   MENU_CONVERTER("#menuConverter", "menuConverter"),
   MENU_CALCULATOR("#menuCalculator", "menuCalculator"),

   /* Extra field. */
   NO_MEMORY(null, "noMemory"),
   NO_LOG(null, "noLog"),
   MEMORY(null, "memory"),
   HISTORY(null, "history"),

   /* Exceptions. */
   OVERFLOW(null, "overflow"),
   UNDEFINED_RESULT(null, "undefinedResult"),
   DIVIDE_BY_ZERO(null, "divideByZero"),
   INVALID_INPUT(null, "invalidInput"),

   /* Buttons in menu. */
   STANDARD("#standard", "standard"),
   SCIENTIFIC("#scientific", "scientific"),
   PROGRAMMER("#programmer", "programmer"),
   DATE_CALCULATION("#dateCalculation", "dateCalculation"),
   CURRENCY("#currency", "currency"),
   VOLUME("#volume", "volume"),
   LENGTH("#length", "length"),
   WEIGHT("#weight", "weight"),
   TEMPERATURE("#temperature", "temperature"),
   ENERGY("#energy", "energy"),
   AREA("#area", "area"),
   SPEED("#speed", "speed"),
   TIME("#time", "time"),
   POWER("#power", "power"),
   DATA("#data", "data"),
   PRESSURE("#pressure", "pressure"),
   ANGLE("#angle", "angle"),
   ABOUT("#about", "about"),

   RESULT_LABEL("#resultLabel", null),
   RESULT_LABEL_BOX("#resultLabelBox", null),
   MAIN_PANE("#mainPane", null),

   /* System buttons. */
   CLOSE("#close", "close"),
   FULL("#full", "full"),
   HIDE("#hide", "hide"),

   /* Nodes in extra field. */
   EXTRA_INFO_FULL("#extraInfoFull", null),
   EXTRA_MEMORY_LABEL("#extraMemoryLabel", "extraMemoryLabel"),
   LOG_BTN("#logBtn", "logBtn"),
   LOG_SELECT("#logSelect", null),
   MEMORY_BTN("#memoryBtn", "memoryBtn"),
   MEMORY_SELECT("#memorySelect", null),
   EXTRA_LOG_LABEL("#extraLogLabel", "extraLogLabel"),

   /* Panes for resize. */
   LEFT_RESIZE("#leftResize", null),
   EXTRA_LEFT_RESIZE("#extraLeftResize", null),
   RIGHT_RESIZE("#rightResize", null),
   EXTRA_RIGHT_RESIZE("#extraRightResize", null),
   TOP_RESIZE("#topResize", null),
   BOTTOM_RESIZE("#bottomResize", null),
   LEFT_BOTTOM_RESIZE("#leftBottomResize", null),
   RIGHT_BOTTOM_RESIZE("#rightBottomResize", null),
   LEFT_TOP_RESIZE("#leftTopResize", null),
   RIGHT_TOP_RESIZE("#rightTopResize", null),
   BOTTOM_RESIZE_FULL("#bottomResizeFull", null),
   SHOW_MEMORY("#showMemory", "showMemory"),
   LOG_BUTTON("#logButton", "logButton"),

   /* Special operation. */
   PERCENT_OPERATION("#percentOperation", "percentOperation"),
   SQRT_OPERATION("#sqrtOperation", "sqrtOperation"),
   SQUARE("#squareOperation", "squareOperation"),
   DIVIDE_BY_X("#divideByX", "divideByX"),

   /* Buttons for build operand.  */
   BTN0("#btn0", "btn0"),
   BTN1("#btn1", "btn1"),
   BTN2("#btn2", "btn2"),
   BTN3("#btn3", "btn3"),
   BTN4("#btn4", "btn4"),
   BTN5("#btn5", "btn5"),
   BTN6("#btn6", "btn6"),
   BTN7("#btn7", "btn7"),
   BTN8("#btn8", "btn8"),
   BTN9("#btn9", "btn9"),

   /* Buttons for correct operand. */
   BACKSPACE("#backSpace", "backSpace"),
   SEPARATE_BTN("#separateBtn", "separateBtn"),
   NEGATE("#negateOperation", "negateOperation"),

   /* Simple operation. */
   EQUALS_OPERATION("#equalsOperation", "equalsOperation"),
   PLUS_OPERATION("#plusOperation", "plusOperation"),
   MINUS_OPERATION("#minusOperation", "minusOperation"),
   DIVIDE_OPERATION("#divideOperation", "divideOperation"),
   MULTIPLY_OPERATION("#multiplyOperation", "multiplyOperation"),

   /* Clear buttons. */
   C("#clear", "clear"),
   CE("#clearEntry", "clearEntry"),

   /* Another nodes. */
   MENU_BTN("#menuBtn", "menuBtn"),
   HIDE_MENU("#hideMenu", null),
   MEMORY_FIELD("#memoryField", null),
   HIDE_MEMORY_FIELD("#hideMemoryField", null),
   MENU("#menu", null),
   MEMORY_CLEAR("#clearMemory", "clearMemory"),
   MEMORY_RECALL("#recallMemory", "recallMemory"),
   MEMORY_ADD("#addMemory", "addMemory"),
   MEMORY_SUBTRACT("#subtractMemory", "subtractMemory"),
   HISTORY_LABEL("#historyLabel", "historyLabel");

   /** Name of node. */
   private String name;

   private String textFromProperty;

   /**
    * Accessor.
    * @return name of node
    */
   public String getName() {
      return name;
   }

   public String getTextFromProperty() {
      return textFromProperty;
   }

   /**
    * Constructor.
    * @param name name of node.
    */
   Node(String name, String getTextFromProperty) {
      this.name = name;
      this.textFromProperty = getTextFromProperty;
   }

   /**
    * Gets side current pane for resize.
    *
    * @see Side
    *
    * @return side for resize.
    */
   public Side getSide() {
      Side result;
      switch (this) {
         case LEFT_RESIZE:
            result = Side.LEFT;
            break;
         case EXTRA_LEFT_RESIZE:
            result = Side.LEFT;
            break;
         case RIGHT_RESIZE:
            result = Side.RIGHT;
            break;
         case EXTRA_RIGHT_RESIZE:
            result = Side.RIGHT;
            break;
         case TOP_RESIZE:
            result = Side.TOP;
            break;
         case BOTTOM_RESIZE:
            result = Side.BOTTOM;
            break;
         case LEFT_BOTTOM_RESIZE:
            result = Side.LEFT_BOTTOM;
            break;
         case RIGHT_BOTTOM_RESIZE:
            result = Side.RIGHT_BOTTOM;
            break;
         case LEFT_TOP_RESIZE:
            result = Side.LEFT_TOP;
            break;
         case RIGHT_TOP_RESIZE:
            result = Side.RIGHT_TOP;
            break;
         case BOTTOM_RESIZE_FULL:
            result = Side.BOTTOM;
            break;
         default:
            throw new IllegalArgumentException();
      }
      return result;
   }

   /**
    * Finds {@link Node} by name.
    * @param name name of node
    * @return found {@link Node}
    */
   public static Node findByQuery(String name) {
      for (Node node: Node.values()) {
         if(node.getName() == null) {
            continue;
         }
         if(node.getName().equals("#" + name)){
            return node;
         }
      }
      throw new IllegalArgumentException();
   }
}
