package com.paulek.core.basic;

public interface Serializable<T, R> {

    R serializeData(T t);

    T deserializeData(R r);

}
