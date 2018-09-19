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
        return operations.size() != 0 ? operations.getLast() : null;
    }

    @Override
    public void changeLast(Operation operation) {
        operations.removeLast();
        add(operation);
    }

    public String buildHistory() {
        StringBuilder result = new StringBuilder();
        for (Operation a : operations) {
            result.append(a.buildHistory());
        }
        return result.toString();
    }
}
