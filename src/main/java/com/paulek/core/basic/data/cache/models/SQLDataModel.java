package com.paulek.core.basic.data.cache.models;

import com.paulek.core.basic.Serializable;

import java.sql.ResultSet;

public interface SQLDataModel<T> extends Serializable<T, ResultSet> {

    @Override
    ResultSet serializeData(T t);

    @Override
    T deserializeData(ResultSet resultSet);
}
