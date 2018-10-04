package com.implemica.model.calculator;

import com.implemica.model.exceptions.OverflowException;
import com.implemica.model.exceptions.UndefinedResultException;
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
import java.math.RoundingMode;

@Getter
@Setter
public class Container {

    private BigDecimal result = new BigDecimal(BigInteger.ZERO, MathContext.DECIMAL128);

    private SimpleOperation operation = new Default();

    private History<SimpleOperation> history = new MainHistory();

    private boolean madeOperand;

    public void calculate() throws OverflowException, UndefinedResultException {
        result = this.operation.calculate(result);
        checkOverflow(result);

        if(operation instanceof Default && !history.contains(Default.class)){
            history.add(operation);
            operation.setShowOperand(true);
        }
    }

    /**
     * Function which calculate for {@link SpecialOperation}.
     */
    public void change(SpecialOperation operation, boolean isResult) throws UndefinedResultException, OverflowException {
        if(isResult && !this.operation.isShowOperand()){

            // TODO it's not possible here. Just a shit.
            this.operation.setStringOperand(result.toString());
            this.operation.getOperandHistory().add(operation);

            BigDecimal operand;

            if(operation instanceof Percent){
                operand = operation.calculate(getOperation().getOperand());
            } else {
                operand = operation.calculate(result);
            }

            checkOverflow(operand);

            this.operation.setOperand(operand);
            this.operation.setShowOperand(true);
        } else {
            getOperation().setOperand(operation.calculate(getOperation().getOperand()));
            getOperation().getOperandHistory().add(operation);

            if(this.operation instanceof Default && getHistory().size() == 0){
                getHistory().add(this.operation);
            }
        }
    }

    private void checkOverflow(BigDecimal number) throws OverflowException {
        if(result.abs().compareTo(new BigDecimal("1e10000")) >= 0 ||
                (result.abs().compareTo(BigDecimal.ZERO)) > 0 &&
                        result.abs().compareTo(new BigDecimal("1e-10000")) <= 0) {
            throw new OverflowException(result);
        }
    }
}
