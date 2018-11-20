package com.implemica.model.interfaces;

public interface History<T extends Operation> {

    void add(T operation);

    void clear();

    int size();

    void changeLast(T operation);

    String buildHistory();

    void hideLast();
}
