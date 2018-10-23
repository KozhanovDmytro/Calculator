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
   BOTTOM_RESIZE_FULL("#bottomResizeFull");






   @Getter
   private String query;

   NodesFinder(String query) {
      this.query = query;
   }
}
