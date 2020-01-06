package com.paulek.core.basic.data;

public interface Data<T, U> {

    T load(U u);

    void load();

    void save(boolean ignoreNotChanged);

    //void validateLoadedData();

}
