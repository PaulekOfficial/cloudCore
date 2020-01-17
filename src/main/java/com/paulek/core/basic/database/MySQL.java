package com.paulek.core.basic.database;

import com.paulek.core.Core;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

public class MySQL extends Database{

    private HikariDataSource dataSource;
    private Core core;

    private String host;
    private String port;
    private String database;
    private String user;
    private String password;

    public MySQL(Core core, String host, String port, String database, String user, String password) {
        this.core = Objects.requireNonNull(core, "core");
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    @Override
    public void init(){
        HikariConfig config = new HikariConfig();

        Map<String, String> linkMap = core.getConfiguration().mysql;
        String link = linkMap.get("jdbcUrl");

        config.setJdbcUrl(link.replace("{host}", linkMap.get("host")).replace("{port}", linkMap.get("port")).replace("{database-name}", linkMap.get("database-name")));
        config.setUsername(user);
        config.setPassword(password);
        config.setMaximumPoolSize(Integer.parseInt(linkMap.get("pool-size")));
        config.setConnectionTimeout(30000);

        this.dataSource = new HikariDataSource(config);

        dataSource.getConnectionTestQuery();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
