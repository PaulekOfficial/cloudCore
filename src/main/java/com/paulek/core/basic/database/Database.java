package com.paulek.core.basic.database;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class Database {

    public abstract void init();

    public abstract Connection getConnection() throws SQLException;

}
