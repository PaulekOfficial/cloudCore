package com.paulek.core.basic.data;

import java.util.Collection;

public interface Data<T, U> {

    T load(U u);

    T load(int id);

    void load();

    void save(Collection<T> collection, boolean ignoreNotChanged);

    void save(T t);

    int count();

    //void validateLoadedData();

}
