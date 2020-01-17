package com.paulek.core.basic.data.cache.models.mysql;

import com.paulek.core.basic.User;
import com.paulek.core.basic.Vector3D;
import com.paulek.core.basic.data.Data;
import com.paulek.core.basic.data.cache.models.SQLDataModel;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.UUID;

public class MySQLSpawnsData implements Data<Vector3D, String>, SQLDataModel<Vector3D> {

    @Override
    public Vector3D load(String s) {
        return null;
    }

    @Override
    public Vector3D load(int id) {
        return null;
    }

    @Override
    public void load() {

    }

    @Override
    public void save(Collection<Vector3D> collection, boolean ignoreNotChanged) {

    }

    @Override
    public void save(Vector3D vector3D) {

    }

    @Override
    public void delete(String s) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public ResultSet serializeData(Vector3D vector3D) {
        return null;
    }

    @Override
    public Vector3D deserializeData(ResultSet resultSet) {
        return null;
    }
}
