package com.implemica.view.util;

/**
 * The enumeration contains name of nodes.
 */
public enum NodesFinder {
   RESULT_LABEL("#resultLabel"),
   RESULT_LABEL_BOX("#resultLabelBox"),
   MAIN_PANE("#mainPane"),

   /* System buttons. */
   CLOSE("#close"),
   FULL("#full"),
   HIDE("#hide"),

   /* Nodes in extra field. */
   EXTRA_INFO_FULL("#extraInfoFull"),
   EXTRA_INFO_BTNS("#extraInfoBtns"),
   EXTRA_MEMORY_LABEL("#extraMemoryLabel"),
   LOG_BTN("#logBtn"),
   LOG_SELECT("#logSelect"),
   MEMORY_BTN("#memoryBtn"),
   MEMORY_SELECT("#memorySelect"),
   EXTRA_LOG_LABEL("#extraLogLabel"),

   /*panes for resize. */
   LEFT_RESIZE("#leftResize"),
   EXTRA_LEFT_RESIZE("#extraLeftResize"),
   RIGHT_RESIZE("#rightResize"),
   EXTRA_RIGHT_RESIZE("#extraRightResize"),
   TOP_RESIZE("#topResize"),
   BOTTOM_RESIZE("#bottomResize"),
   LEFT_BOTTOM_RESIZE("#leftBottomResize"),
   RIGHT_BOTTOM_RESIZE("#rightBottomResize"),
   LEFT_TOP_RESIZE("#leftTopResize"),
   RIGHT_TOP_RESIZE("#rightTopResize"),
   BOTTOM_RESIZE_FULL("#bottomResizeFull"),
   SHOW_MEMORY("#showMemory"),
   LOG_BUTTON("#logButton"),

   /* special operation. */
   PERCENT_OPERATION("#percentOperation"),
   SQRT_OPERATION("#sqrtOperation"),
   SQUARE("#square"),
   DIVIDE_BY_X("#divideByX"),

   /* Buttons for build operand.  */
   BTN0("#btn0"),
   BTN1("#btn1"),
   BTN2("#btn2"),
   BTN3("#btn3"),
   BTN4("#btn4"),
   BTN5("#btn5"),
   BTN6("#btn6"),
   BTN7("#btn7"),
   BTN8("#btn8"),
   BTN9("#btn9"),

   /* Buttons for correct operand. */
   BACKSPACE("#backSpace"),
   SEPARATE_BTN("#separateBtn"),
   NEGATE("#negate"),

   /* Simple operation. */
   EQUALS_OPERATION("#equalsOperation"),
   PLUS_OPERATION("#plusOperation"),
   MINUS_OPERATION("#minusOperation"),
   DIVIDE_OPERATION("#divideOperation"),
   MULTIPLY_OPERATION("#multiplyOperation"),

   /* Clear buttons. */
   C("#clear"),
   CE("#clearEntry"),

   /* Another nodes. */
   MENU_BTN("#menuBtn"),
   HIDE_MENU("#hideMenu"),
   MEMORY_FIELD("#memoryField"),
   HIDE_MEMORY_FIELD("#hideMemoryField"),
   MENU("#menu"),
   MEMORY_CLEAR("#clearMemory"),
   MEMORY_RECALL("#recallMemory"),
   MEMORY_ADD("#addMemory"),
   MEMORY_SUBTRACT("#subtractMemory"),
   HISTORY_LABEL("#historyLabel");

   /** Name of node. */
   private String name;

   /**
    * Accessor.
    * @return name of node
    */
   public String getName() {
      return name;
   }

   /**
    * Constructor.
    * @param name name of node.
    */
   NodesFinder(String name) {
      this.name = name;
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
    * Finds {@link NodesFinder} by name.
    * @param name
    * @return found {@link NodesFinder}
    */
   public static NodesFinder findByQuery(String name) {
      for (NodesFinder node: NodesFinder.values()) {
         if(node.getName().equals("#" + name)){
            return node;
         }
      }
      throw new IllegalArgumentException();
   }
}
