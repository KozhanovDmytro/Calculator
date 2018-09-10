package com.implemica.model.history;

import com.implemica.model.interfaces.History;
import com.implemica.model.operations.Operation;

import java.util.ArrayDeque;

public class HistoryImpl implements History {

    private ArrayDeque<Operation> operations;

    public HistoryImpl(){
        operations = new ArrayDeque<>();
    }

    @Override
    public void add(Operation operation) {
        operations.add(operation);
    }

    @Override
    public void clear() {
        operations.clear();
    }

    @Override
    public Operation getLast() {
        return operations.getLast();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Operation a : operations) {
            a.buildHistory(result);
        }
        return result.toString();
    }
}
