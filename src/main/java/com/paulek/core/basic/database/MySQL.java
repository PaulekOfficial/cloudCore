package com.paulek.core.basic.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQL extends Database{

    private HikariDataSource dataSource;

    private String host;
    private String port;
    private String database;
    private String user;
    private String password;

    public MySQL(String host, String port, String database, String user, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    @Override
    public void init(){
        HikariConfig config = new HikariConfig();

        StringBuilder link = new StringBuilder();
        link.append("jdbc:mysql://");
        link.append(host);
        link.append(":");
        link.append(port);
        link.append("/");
        link.append(database);
        link.append("?useUnicode=true&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");

        config.setJdbcUrl(link.toString());
        config.setUsername(user);
        config.setPassword(password);
        config.setMaximumPoolSize(4);
        config.setConnectionTimeout(30000);

        this.dataSource = new HikariDataSource(config);

        dataSource.getConnectionTestQuery();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
