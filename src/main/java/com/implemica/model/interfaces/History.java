package com.implemica.model.interfaces;

public interface History<T extends Operation> {

    void add(T operation);

    void clear();

    T getLast();

    int size();

    boolean contains(Class operation);

    void changeLast(T operation);

    String buildHistory();
}
