package com.implemica.model.calculator;

import com.implemica.model.exceptions.InvalidInputException;
import com.implemica.model.exceptions.OverflowException;
import com.implemica.model.exceptions.UndefinedResultException;
import com.implemica.model.history.MainHistory;
import com.implemica.model.interfaces.Memory;
import com.implemica.model.interfaces.SpecialOperation;
import com.implemica.model.memory.MemoryImpl;
import com.implemica.model.operations.Default;
import com.implemica.model.operations.SimpleOperation;
import com.implemica.model.operations.special.Negate;
import com.implemica.model.operations.special.Percent;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
public class Container {

    private BigDecimal result = new BigDecimal(BigInteger.ZERO);

    private SimpleOperation operation = new Default();

    private MainHistory history = new MainHistory();

    private Memory memory = new MemoryImpl();

    private boolean madeOperand;

    public void calculate() throws OverflowException, UndefinedResultException, InvalidInputException {
        result = this.operation.calculate(result);
        result = checkForZero(result);
        checkOverflow(result);

        if(operation instanceof Default && !history.contains(Default.class)){
            history.add(operation);
            operation.setShowOperand(true);
        }
    }

    public void change(SpecialOperation operation, boolean isResult) throws UndefinedResultException, OverflowException, InvalidInputException {
        if(isResult && !this.operation.isShowOperand()){
            this.operation.setInitialOperand(result);
            this.operation.getOperandHistory().add(operation);

            BigDecimal operand;

            if(operation instanceof Percent){
                operand = operation.calculate(getOperation().getOperand());
            } else {
                operand = operation.calculate(result);
            }

            this.operation.setOperand(operand);
        } else {
            if(this.operation instanceof Default && getHistory().size() == 0){
                getHistory().add(this.operation);
                this.operation.setShowOperand(true);
            }

            getOperation().getOperandHistory().add(operation);
            getOperation().setOperand(operation.calculate(getOperation().getOperand()));
        }
        setMadeOperand(operation instanceof Negate);
        this.operation.setShowOperand(true);
        this.operation.setOperand(checkForZero(this.operation.getOperand()));
        checkOverflow(this.operation.getOperand());
    }

    private void checkOverflow(BigDecimal number) throws OverflowException {
        if(number.abs().compareTo(new BigDecimal("1e10000")) >= 0 ||
                (number.abs().compareTo(BigDecimal.ZERO)) > 0 &&
                        number.abs().compareTo(new BigDecimal("1e-10000")) <= 0) {
            throw new OverflowException(number);
        }
    }

    private BigDecimal checkForZero(BigDecimal number) {
        if(number.abs().compareTo(new BigDecimal("1e-19950")) < 0) {
            return BigDecimal.ZERO;
        } else {
            return number;
        }
    }
}
