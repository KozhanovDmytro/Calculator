package com.implemica.model.history;

import com.implemica.model.interfaces.History;
import com.implemica.model.interfaces.Operation;
import com.implemica.model.operations.SimpleOperation;

import java.util.ArrayDeque;

public class MainHistory implements History<SimpleOperation> {

    private ArrayDeque<SimpleOperation> operations;

    public MainHistory(){
        operations = new ArrayDeque<>();
    }

    @Override
    public void add(SimpleOperation operation) {
        operations.add(operation);
    }

    @Override
    public void clear() {
        operations.clear();
    }

    @Override
    public SimpleOperation getLast() {
        return operations.size() != 0 ? operations.getLast() : null;
    }

    @Override
    public int size() {
        return operations.size();
    }

    @Override
    public boolean contains(Class operation) {
        boolean result = false;
        for (SimpleOperation so : operations) {
            if(operation.equals(so.getClass())) {
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public void changeLast(SimpleOperation operation) {
        operations.removeLast();
        add(operation);
    }

    public String buildHistory() {
        StringBuilder result = new StringBuilder();
        for (SimpleOperation a : operations) {
            result.append(a.buildHistory());
        }
        return result.toString();
    }

    @Override
    public void hideLast() {
        if(operations.size() > 0)
            operations.getLast().setShowOperand(false);
    }
}
