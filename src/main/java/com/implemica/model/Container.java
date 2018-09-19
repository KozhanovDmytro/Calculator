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
import java.math.MathContext;

@Getter
@Setter
public class Container {

    private BigDecimal result = new BigDecimal(BigInteger.ZERO, MathContext.DECIMAL64);

    private Operation operation = new Default();

    private History history = new HistoryImpl();

    private boolean madeOperand;

    public void calculate() {
        result = this.operation.calculate(result);
        if(operation instanceof Default){
            history.add(operation);
        }
    }

    /**
     * Function wich calculate for {@link SpecialOperation}.
     * @param operation
     * @param isResult
     */
    public void change(SpecialOperation operation, boolean isResult) {
        if(isResult){
            setResult(operation.calculate(getResult()));
        } else {
            getOperation().setOperand(operation.calculate(getOperation().getOperand()));
            getOperation().getLocalHistory().add(operation);
        }
    }
}
