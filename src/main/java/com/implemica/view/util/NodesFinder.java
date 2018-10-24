package com.implemica.view.util;

import lombok.Getter;

public enum NodesFinder {
   RESULT_LABEL("#resultLabel"),
   RESULT_LABEL_BOX("#resultLabelBox"),
   MAIN_PANE("#mainPane"),

   /*System buttons*/
   CLOSE("#close"),
   FULL("#full"),
   HIDE("#hide"),

   EXTRA_INFO_FULL("#extraInfoFull"),
   EXTRA_INFO_BTNS("#extraInfoBtns"),
   EXTRA_MEMORY_LABEL("#extraMemoryLabel"),

   /*panes for resize*/
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
   RIGHT_RESIZE_FULL("#rightResizeFull"),
   RIGHT_BOTTOM_RESIZE_FULL("#rightBottomResizeFull"),
   BOTTOM_RESIZE_FULL("#bottomResizeFull"),
   SHOW_MEMORY("#showMemory"),
   LOG_BUTTON("#logButton"),

   LOG_BTN("#logBtn"),
   LOG_SELECT("#logSelect"),
   MEMORY_BTN("#memoryBtn"),
   MEMORY_SELECT("#memorySelect"),
   EXTRA_LOG_LABEL("#extraLogLabel"),

   PERCENT_OPERATION("#percentOperation"),
   SQRT_OPERATION("#sqrtOperation"),
   SQUARE("#square"),
   DIVIDE_BY_X("#divideByX"),

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

   BACKSPACE("#backSpace"),
   SEPARATE_BTN("#separateBtn"),
   NEGATE("#negate"),
   EQUALS_OPERATION("#equalsOperation"),
   PLUS_OPERATION("#plusOperation"),
   MINUS_OPERATION("#minusOperation"),
   DIVIDE_OPERATION("#divideOperation"),
   MULTIPLY_OPERATION("#multiplyOperation"),
   C("#c"),
   CE("#CE"),

   MENU_BTN("#menuBtn"),
   HIDE_MENU("#hideMenu"),
   MEMORY_FIELD("#memoryField"),
   HIDE_MEMORY_FIELD("#hideMemoryField"),
   MENU("#menu");




   @Getter
   private String query;

   NodesFinder(String query) {
      this.query = query;
   }

   public static Side getSide(NodesFinder nodesFinder) {
      Side result;
      switch (nodesFinder) {
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
         case RIGHT_RESIZE_FULL:
            result = Side.RIGHT;
            break;
         case RIGHT_BOTTOM_RESIZE_FULL:
            result = Side.RIGHT_BOTTOM;
            break;
         case BOTTOM_RESIZE_FULL:
            result = Side.BOTTOM;
            break;
         default:
            throw new IllegalArgumentException();
      }
      return result;
   }

   public static NodesFinder findByQuery(String query) {
      for (NodesFinder node: NodesFinder.values()) {
         if(node.getQuery().equals("#" + query)){
            return node;
         }
      }
      throw new IllegalArgumentException();
   }
}
