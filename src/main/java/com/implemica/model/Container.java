package com.implemica.model;

import com.implemica.model.history.HistoryImpl;
import com.implemica.model.interfaces.History;
import com.implemica.model.operations.Default;
import com.implemica.model.operations.Operation;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;

@Getter
@Setter
public class Container {

    private BigDecimal result = new BigDecimal(BigInteger.ZERO);

    private Operation operation = new Default();

    private History history = new HistoryImpl();

    private boolean madeOperand;

    public boolean isMadeOperand() {
        return madeOperand;
    }

    public void calculate() {
        result = this.operation.calculate(result);
        if(!operation.getOperand().equals(BigDecimal.ZERO))
            history.add(this.operation);
    }

    public String showComfortableNumber(BigDecimal number) {
        StringBuilder stringNumber = new StringBuilder(number.toString());
        String result;

        if(this.getOperation().isSeparated()){
            DecimalFormat df = new DecimalFormat("#.");
            return df.format(this.getOperation().getOperand());
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

    public void negateResult(){
        result = result.negate();
    }
}
