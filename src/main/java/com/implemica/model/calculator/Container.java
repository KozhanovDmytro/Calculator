package com.implemica.model.calculator;

import com.implemica.model.history.MainHistory;
import com.implemica.model.interfaces.History;
import com.implemica.model.interfaces.SpecialOperation;
import com.implemica.model.operations.Default;
import com.implemica.model.operations.SimpleOperation;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

@Getter
@Setter
public class Container {

    private BigDecimal result = new BigDecimal(BigInteger.ZERO, MathContext.DECIMAL64);

    private SimpleOperation operation = new Default();

    private History<SimpleOperation> history = new MainHistory();

    private boolean madeOperand;

    public void calculate() {
        result = this.operation.calculate(result);
        if(operation instanceof Default && !history.contains(Default.class)){
            history.add(operation);
        }
    }

    /**
     * Function which calculate for {@link SpecialOperation}.
     * @param operation
     * @param isResult
     */
    public void change(SpecialOperation operation, boolean isResult) {
        if(isResult){
            setResult(operation.calculate(getResult()));
        } else {
            getOperation().setOperand(operation.calculate(getOperation().getOperand()));
            getOperation().getOperandHistory().add(operation);

            if(this.operation instanceof Default && getHistory().size() == 0){
                getHistory().add(this.operation);
            }
        }
    }
}
