package com.paulek.core.basic.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.util.DriverDataSource;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class SQLite extends Database{

    private HikariDataSource dataSource;
    private File databaseFile;

    public SQLite(File databaseFile){
        this.databaseFile = databaseFile;
    }

    @Override
    public void init(){

        HikariConfig hikariConfig = new HikariConfig();

        Properties properties = new Properties();

        DriverDataSource driverDataSource = new DriverDataSource("jdbc:sqlite:" + databaseFile.getAbsolutePath(), "org.sqlite.SQLiteDataSource", properties, null, null);

        hikariConfig.setDataSource(driverDataSource);

        dataSource = new HikariDataSource(hikariConfig);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
