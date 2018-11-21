package com.implemica.model.interfaces;

import com.implemica.model.operations.operation.Operation;

public interface History<T extends Operation> {

   void add(T operation);

   void clear();

   int size();

   void changeLast(T operation);

   String buildHistory();

   void hideLast();
}
