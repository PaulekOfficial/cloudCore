package com.paulek.core.basic.data.databaseStorage;

import com.paulek.core.Core;
import com.paulek.core.basic.Timestamp;
import com.paulek.core.basic.data.Storage;
import com.paulek.core.basic.database.Database;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

public class Timestamps extends Storage {

    private List<Timestamp> userTimestamps;
    private Core core;

    public Timestamps(Core core){
        this.core = Objects.requireNonNull(core, "core");
    }

    @Override
    public void init(){
        userTimestamps = new ArrayList<>();
        loadFromDatabase(core.getDatabase());
    }

    public Timestamp getTimestamp(UUID uuid, String serviceName, String className){
        for(Timestamp timestamp : userTimestamps){
            if(timestamp.getServiceName().equalsIgnoreCase(serviceName) && timestamp.getUuid().equals(uuid) && timestamp.getClassName().equalsIgnoreCase(className)){
                return timestamp;
            }
        }
        return null;
    }

    public void addTimestamp(Timestamp timestamp){
        for(Timestamp ts : userTimestamps){
            if(ts.getUuid().equals(timestamp.getUuid()) && ts.getServiceName().equalsIgnoreCase(timestamp.getServiceName())){
                removeTimestamp(ts.getUuid(), ts.getServiceName());
            }
        }
        userTimestamps.add(timestamp);
    }

    public void removeTimestamp(UUID uuid, String serviceName){
        Iterator iterator = userTimestamps.iterator();
        while (iterator.hasNext()) {
            Timestamp timestamp = (Timestamp) iterator.next();
            if (timestamp.getServiceName().equalsIgnoreCase(serviceName) && timestamp.getUuid().equals(uuid)) {
                iterator.remove();
            }
        }
    }

    @Override
    public void saveAllToDatabase(Database database) {
        for(Timestamp timestamp : userTimestamps){
            saveObjectToDatabase(timestamp, database);
        }
    }

    @Override
    public void saveDirtyObjects(Database database) {
        if(userTimestamps == null || userTimestamps.isEmpty()) return;

        for(Timestamp timestamp : userTimestamps){
            if(timestamp.isDirty()){
                saveObjectToDatabase(timestamp, database);
                timestamp.setDirty(false);
            }
        }
    }

    @Override
    public void loadFromDatabase(Database database) {
        try(Connection connection = database.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM cloud_timestamps");

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){

                if(resultSet.getBoolean("expired")) return;

                UUID uuid = UUID.fromString(resultSet.getString(1));
                String serviceName = resultSet.getString(2);
                String className = resultSet.getString(3);
                LocalDateTime startTime = resultSet.getTimestamp(4).toLocalDateTime();
                LocalDateTime endTime = resultSet.getTimestamp(5).toLocalDateTime();

                Timestamp timestamp = new Timestamp(uuid, serviceName, className, startTime, endTime);

                userTimestamps.add(timestamp);
            }

            preparedStatement.close();

        } catch (SQLException exception){
            exception.printStackTrace();
        }
    }

    @Override
    public void saveObjectToDatabase(Object object, Database database) {
        Timestamp timestamp = (Timestamp) object;
        try (Connection connection = database.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO cloud_timestamps (uuid, serviceName, className, startTime, endTime, expired) VALUES(?, ?, ?, ?, ?, ?) " + core.getUpdateMethod() + " uuid=?, serviceName=?, className=?, startTime=?, endTime=?, expired=?");

            preparedStatement.setString(1, timestamp.getUuid().toString());
            preparedStatement.setString(2, timestamp.getServiceName());
            preparedStatement.setString(4, timestamp.getClassName());
            preparedStatement.setTimestamp(5, java.sql.Timestamp.valueOf(timestamp.getStartTime()));
            preparedStatement.setTimestamp(6, java.sql.Timestamp.valueOf(timestamp.getEndTime()));
            preparedStatement.setBoolean(7, !timestamp.applicable());

            preparedStatement.setString(8, timestamp.getUuid().toString());
            preparedStatement.setString(9, timestamp.getServiceName());
            preparedStatement.setString(10, timestamp.getClassName());
            preparedStatement.setTimestamp(11, java.sql.Timestamp.valueOf(timestamp.getStartTime()));
            preparedStatement.setTimestamp(12, java.sql.Timestamp.valueOf(timestamp.getEndTime()));
            preparedStatement.setBoolean(13, !timestamp.applicable());

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
