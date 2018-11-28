package com.implemica.controller.util;

import com.implemica.model.history.History;
import com.implemica.model.operations.operation.SimpleOperation;
import com.implemica.model.operations.operation.SpecialOperation;
import com.implemica.model.operations.special.Percent;
import com.implemica.model.validation.Validator;

import java.util.LinkedList;

public class HistoryParser {

   private Validator validator = new Validator();

   public String parse(History history) {
      StringBuilder result = new StringBuilder();

      for (SimpleOperation simpleOperation : history.getOperations()) {
         parseSpecialOperation(simpleOperation, result);
      }

      return result.toString();
   }

   private void parseSpecialOperation(SimpleOperation simpleOperation, StringBuilder result) {
      result.append(simpleOperation.getCharacter());

      if(isPercent(simpleOperation.getOperandHistory())) {
         result.append(validator.showNumberForHistory(simpleOperation.getOperand()));
      } else {
         buildHistoryWithoutPercent(simpleOperation, result);
      }
   }

   private void buildHistoryWithoutPercent(SimpleOperation simpleOperation, StringBuilder result) {
      for (SpecialOperation specialOperation : simpleOperation.getOperandHistory()) {
         result.append(specialOperation.getFirstPartHistory());
      }

      if(simpleOperation.isShowOperand()) {
         result.append(validator.showNumberForHistory(simpleOperation.getInitialOperand()));
      }

      for (SpecialOperation specialOperation : simpleOperation.getOperandHistory()) {
         result.append(specialOperation.getSecondPartHistory());
      }
   }

   private boolean isPercent(LinkedList<SpecialOperation> operations) {
      if(operations.size() == 0) {
         return false;
      } else {
         return operations.getFirst() instanceof Percent;
      }
   }
}
