package com.paulek.core.basic;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQL {

    private HikariDataSource dataSource;

    public MySQL(String host, String port, String database, String user, String password) {

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
        config.setMaximumPoolSize(10);
        config.setConnectionTimeout(30000);

        this.dataSource = new HikariDataSource(config);

    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
