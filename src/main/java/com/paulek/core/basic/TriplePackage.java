package com.paulek.core.basic;

public class TriplePackage<T, D, R> {

    private T t;
    private D d;
    private R r;

    public TriplePackage(T t, D d, R r) {
        this.t = t;
        this.d = d;
        this.r = r;
    }

    public T getT() {
        return t;
    }

    public D getD() {
        return d;
    }

    public R getR() {
        return r;
    }
}
