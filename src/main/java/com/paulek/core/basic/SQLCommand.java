package com.paulek.core.basic;

import com.paulek.core.basic.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public enum SQLCommand {

    CREATE_USERS_TABLE(SQLAction.UPDATE, "CREATE TABLE IF NOT EXISTS `cloud_users` ( `id` INT NOT NULL AUTO_INCREMENT , `uuid` TEXT NOT NULL , `lastAccountName` TEXT NOT NULL , `logoutLocation` LONGTEXT NOT NULL , `lastLocation` LONGTEXT NOT NULL , `ipAddres` MEDIUMTEXT NOT NULL , `homes` LONGTEXT NOT NULL , `lastActivity` TIMESTAMP DEFAULT CURRENT_TIMESTAMP , `socialSpy` TINYINT NOT NULL , `vanish` TINYINT NOT NULL , `tpToogle` TINYINT NOT NULL , `tpsMonitor` TINYINT NOT NULL , `godMode` TINYINT NOT NULL , PRIMARY KEY (`id`))", "CREATE TABLE IF NOT EXISTS `cloud_users` ( `id` INTEGER PRIMARY KEY AUTOINCREMENT , `uuid` TEXT NOT NULL , `lastAccountName` TEXT NOT NULL , `logoutLocation` LONGTEXT NOT NULL , `lastLocation` LONGTEXT NOT NULL , `ipAddres` MEDIUMTEXT NOT NULL , `homes` LONGTEXT NOT NULL , `lastActivity` TIMESTAMP NOT NULL , `socialSpy` TINYINT NOT NULL , `vanish` TINYINT NOT NULL , `tpToogle` TINYINT NOT NULL , `tpsMonitor` TINYINT NOT NULL, `godMode` TINYINT NOT NULL)"),
    CREATE_TIMESTAMPS_TABLE(SQLAction.UPDATE, "CREATE TABLE IF NOT EXISTS `cloud_timestamps` ( `id` INT NOT NULL AUTO_INCREMENT , `uuid` TEXT NOT NULL , `serviceName` TEXT NOT NULL , `className` TEXT NOT NULL , `startTime` TIMESTAMP DEFAULT CURRENT_TIMESTAMP , `endTime` TIMESTAMP DEFAULT CURRENT_TIMESTAMP , `expired` TINYINT NOT NULL , PRIMARY KEY (`id`))", "CREATE TABLE IF NOT EXISTS `cloud_timestamps` ( `id` INTEGER PRIMARY KEY AUTOINCREMENT , `uuid` TEXT NOT NULL , `serviceName` TEXT NOT NULL , `className` TEXT NOT NULL , `startTime` TIMESTAMP NOT NULL , `endTime` TIMESTAMP NOT NULL , `expired` TINYINT NOT NULL)"),
    CREATE_SKINS_TABLE(SQLAction.UPDATE, "CREATE TABLE IF NOT EXISTS `cloud_skins` ( `id` INT NOT NULL AUTO_INCREMENT , `uuid` TEXT NOT NULL , `name` LONGTEXT NOT NULL , `value` LONGTEXT NOT NULL , `signature` LONGTEXT NOT NULL , `lastUpdate` TIMESTAMP DEFAULT CURRENT_TIMESTAMP , PRIMARY KEY (`id`))", "CREATE TABLE IF NOT EXISTS `cloud_skins` ( `id` INTEGER PRIMARY KEY AUTOINCREMENT , `uuid` TEXT NOT NULL , `name` LONGTEXT NOT NULL , `value` LONGTEXT NOT NULL , `signature` LONGTEXT NOT NULL , `lastUpdate` TIMESTAMP NOT NULL)"),
    CREATE_SPAWNS_TABLE(SQLAction.UPDATE, "CREATE TABLE IF NOT EXISTS `cloud_spawns` ( `id` INT NOT NULL AUTO_INCREMENT , `name` INT NOT NULL , `world` TEXT NOT NULL ,`x` DOUBLE NOT NULL , `y` DOUBLE NOT NULL , `z` DOUBLE NOT NULL , `pitch` FLOAT NOT NULL , `yaw` FLOAT NOT NULL , PRIMARY KEY (`id`))", "CREATE TABLE IF NOT EXISTS `cloud_spawns` ( `id` INTEGER PRIMARY KEY AUTOINCREMENT , `name` INT NOT NULL , `world` TEXT NOT NULL ,`x` DOUBLE NOT NULL , `y` DOUBLE NOT NULL , `z` DOUBLE NOT NULL , `pitch` FLOAT NOT NULL , `yaw` FLOAT NOT NULL)"),
    CREATE_RTPBUTTONS_TABLE(SQLAction.UPDATE, "CREATE TABLE IF NOT EXISTS `cloud_buttons` ( `id` INT NOT NULL AUTO_INCREMENT , `world` TEXT NOT NULL , `x` DOUBLE NOT NULL , `y` DOUBLE NOT NULL , `z` DOUBLE NOT NULL , PRIMARY KEY (`id`))", "CREATE TABLE IF NOT EXISTS `cloud_buttons` ( `id` INTEGER PRIMARY KEY AUTOINCREMENT , `world` TEXT NOT NULL , `x` DOUBLE NOT NULL , `y` DOUBLE NOT NULL , `z` DOUBLE NOT NULL)");

    List<String> commands;
    SQLAction action;

    SQLCommand(SQLAction action, String... commands){
        this.action = action;
        this.commands = Arrays.asList(commands);
    }

    public static String getSQLCommand(SQLCommand command, SQLType type){
        return command.commands.get(type.id);
    }

    public static ResultSet command(Database database, SQLCommand command, SQLType type, Object... values) throws SQLException {

        try (Connection connection = database.getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement(getSQLCommand(command, type));

            if (values.length > 0) {
                int i = 0;
                for (Object o : values) {
                    preparedStatement.setObject(i, o);
                    i++;
                }
            }

            if(command.action.equals(SQLAction.UPDATE)){
                preparedStatement.executeUpdate();
            } else if(command.action.equals(SQLAction.QUERY)){
                return preparedStatement.executeQuery();
            } else {
                //Exception unhandled operation
            }

        } catch (SQLException e){
            //TODO Handle exception
        }

        return null;
    }

    public static ResultSet command(Connection connection, SQLCommand command, SQLType type, Object... values) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(getSQLCommand(command, type));

        if (values.length > 0) {
            int i = 0;
            for (Object o : values) {
                preparedStatement.setObject(i, o);
                i++;
            }
        }

        if(command.action.equals(SQLAction.UPDATE)){
            preparedStatement.executeUpdate();
        } else if(command.action.equals(SQLAction.QUERY)){
            return preparedStatement.executeQuery();
        } else {
            //Exception unhandled operation
        }

        return null;
    }

    public enum SQLType {

        MYSQL(0),
        SQLITE(1);

        int id;

        SQLType(int id){
            this.id = id;
        }

    }

    public enum SQLAction {

        UPDATE,
        QUERY;

    }
}
