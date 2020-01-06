package com.paulek.core.basic.data;

public interface Cache<T, U>{

    T get(U u);

    void add(U u,T t);

    void delete(U u);

}
