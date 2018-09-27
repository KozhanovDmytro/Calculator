package com.implemica.model.calculator;

import com.implemica.model.history.MainHistory;
import com.implemica.model.interfaces.History;
import com.implemica.model.interfaces.SpecialOperation;
import com.implemica.model.operations.Default;
import com.implemica.model.operations.SimpleOperation;
import com.implemica.model.operations.special.Percent;
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
            operation.setShowOperand(true);
        }
    }

    /**
     * Function which calculate for {@link SpecialOperation}.
     */
    public void change(SpecialOperation operation, boolean isResult) {
        if(isResult && !this.operation.isShowOperand()){

            // TODO it's not possible here. Just a shit.
            this.operation.setStringOperand(result.toString());
            this.operation.getOperandHistory().add(operation);

            if(operation instanceof Percent){
                BigDecimal result = operation.calculate(getOperation().getOperand());

                setResult(result);

                this.operation.setShowOperand(false);
            } else {
                this.operation.setOperand(operation.calculate(result));
                this.operation.setShowOperand(true);
            }
        } else {
            getOperation().setOperand(operation.calculate(getOperation().getOperand()));
            getOperation().getOperandHistory().add(operation);

            if(this.operation instanceof Default && getHistory().size() == 0){
                getHistory().add(this.operation);
            }
        }
    }
}
