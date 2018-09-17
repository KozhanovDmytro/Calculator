package com.implemica.model;

import com.implemica.model.history.HistoryImpl;
import com.implemica.model.interfaces.History;
import com.implemica.model.interfaces.SpecialOperation;
import com.implemica.model.operations.Default;
import com.implemica.model.operations.Operation;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
public class Container {

    private BigDecimal result = new BigDecimal(BigInteger.ZERO);

    private Operation operation = new Default();

    private History history = new HistoryImpl();

    private boolean madeOperand;

    public void calculate() {
        result = this.operation.calculate(result);
        if(!operation.getOperand().equals(BigDecimal.ZERO))
            history.add(this.operation);
    }

    public void change(SpecialOperation operation, boolean isResult) {
        if(isResult){
            setResult(operation.calculate(getResult()));
            // TODO write into a local (operand) history
        } else {
            getOperation().setOperand(operation.calculate(getOperation().getOperand()));
        }
    }
}
