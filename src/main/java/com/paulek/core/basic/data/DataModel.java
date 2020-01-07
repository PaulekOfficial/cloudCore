package com.paulek.core.basic.data;

public enum DataModel {

    MYSQL,
    SQLITE,
    FLAT;

    public static DataModel getModelByName(String string) {
        if(string.equalsIgnoreCase("mysql")){
            return DataModel.MYSQL;
        } else if(string.equalsIgnoreCase("flat")){
            return DataModel.FLAT;
        }
        return DataModel.SQLITE;
    }

}
