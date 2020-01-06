package com.paulek.core.basic.data.cache.models;

import com.paulek.core.basic.Serializable;

import java.io.File;

public interface FlatDataModel<T> extends Serializable<T, File> {

    @Override
    File serializeData(T t);

    @Override
    T deserializeData(File f);

}
