package com.implemica.model.interfaces;

import com.implemica.model.operations.Operation;

public interface History {

    void add(Operation operation);

    void clear();

    Operation getLast();
}
