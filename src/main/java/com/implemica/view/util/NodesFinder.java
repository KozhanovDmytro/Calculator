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
   LOG_BUTTON("#logButton");

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
