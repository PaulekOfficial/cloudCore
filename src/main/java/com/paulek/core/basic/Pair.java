package com.paulek.core.basic;

public class Pair<T, R> {

    private T t;
    private R r;

    public Pair(T t, R r) {
        this.t = t;
        this.r = r;
    }

    public T getT() {
        return t;
    }

    public R getR() {
        return r;
    }
}
