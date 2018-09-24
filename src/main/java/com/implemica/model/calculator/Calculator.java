package com.implemica.model.calculator;

import com.implemica.model.interfaces.Numeral;
import com.implemica.model.interfaces.SpecialOperation;
import com.implemica.model.numerals.numbers.Number;
import com.implemica.model.operations.Default;
import com.implemica.model.operations.Equals;
import com.implemica.model.operations.SimpleOperation;
import com.implemica.model.operations.special.Percent;
import com.implemica.model.validation.Validator;
import lombok.Getter;

import java.math.BigDecimal;

public class Calculator {

   private Container container;

   private Numeral numeral;

   private Validator validator = new Validator();

   @Getter
   private boolean isShownResult;

   public Calculator(Numeral numeral){
      this.numeral = numeral;
      container = new Container();
      container.setMadeOperand(true);
   }

   public void executeSimpleOperation(SimpleOperation operation) {
      if (!(container.getOperation() instanceof Equals))
         container.calculate();

      if(container.getOperation().isShowOperand()){
         container.getHistory().add(operation);
      } else {
         if(container.getHistory().size() == 0){
            container.getHistory().add(new Default(container.getResult()));
            container.getHistory().add(operation);
         } else {
            container.getHistory().changeLast(operation);
         }
      }

      container.setOperation(operation);
      container.setMadeOperand(true);
   }

   public void executeSpecialOperation(SpecialOperation operation) {
      if(operation instanceof Percent) {
         ((Percent) operation).setResult(container.getResult());
      }
      container.change(operation, isShownResult);
   }

   public void buildOperand(Number number){
      if (container.isMadeOperand() || container.getOperation() instanceof Equals) {
         container.getOperation().buildOperand(numeral.translate(number));
         container.getOperation().setShowOperand(true);
      }
   }

   public void equalsOperation() {
      if (container.isMadeOperand()) {
         if(isShownResult){
            container.getOperation().setOperand(container.getResult());
         }
      } else {
         if (container.getOperation() instanceof Equals) {
            Equals eq = (Equals) container.getOperation();
            if (!isShownResult) {
               container.setResult(container.getOperation().getOperand());
            }
            eq.getLastOperation().setOperand(eq.getLastOperation().getOperand());
         }
      }
      container.calculate();
      Equals equals = new Equals(container.getOperation());
      container.getHistory().clear();
      container.setOperation(equals);

      container.setMadeOperand(false);
   }

   public void separateOperand() {
      container.getOperation().setSeparated(true);
   }

   public boolean backspace(){
      if (container.isMadeOperand()) {
         container.getOperation().removeLast();
         return true;
      }
      return false;
   }

   public void clear(){
      container.getHistory().clear();
      container.setResult(BigDecimal.ZERO);
      container.setOperation(new Default());
      container.setMadeOperand(true);
   }

   public void clearEntry(){
      container.getOperation().setOperand(BigDecimal.ZERO);
   }

   public String showResult(){
      isShownResult = true;
      return validator.showNumber(container.getResult());
   }

   public String showOperand(){
      isShownResult = false;
      return validator.showNumber(container.getOperation().getOperand(), container.getOperation().isSeparated());
   }

   public String showHistory(){
      return container.getHistory().buildHistory();
   }
}
