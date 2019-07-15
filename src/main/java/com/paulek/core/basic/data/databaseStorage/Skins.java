package com.paulek.core.basic.data.databaseStorage;

import com.paulek.core.Core;
import com.paulek.core.basic.Skin;
import com.paulek.core.basic.data.Storage;
import com.paulek.core.basic.database.Database;
import org.bukkit.Bukkit;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class Skins extends Storage {

    private Map<UUID, Skin> playerSkins;
    private Core core;

    public Skins(Core core){
        this.core = Objects.requireNonNull(core, "Core");
    }

    @Override
    public void init(){
        playerSkins = new HashMap<>();
        loadFromDatabase(core.getDatabase());
    }

    public Skin getSkin(UUID uuid){
        return playerSkins.get(uuid);
    }

    public void addSkin(UUID uuid, Skin skin){
        playerSkins.put(uuid, skin);
    }

    @Override
    public void saveDirtyObjects(Database database) {
        if(playerSkins == null || playerSkins.isEmpty()) return;

        for(UUID uuid : playerSkins.keySet()){
            Skin skin = playerSkins.get(uuid);
            if(skin.isDirty()){
                List<Object> list = new ArrayList<>();
                list.add(uuid);
                list.add(skin);
                saveObjectToDatabase(list, database);
            }
            skin.setDirty(false);
        }
    }

    @Override
    public void saveAllToDatabase(Database database) {
        for(UUID uuid : playerSkins.keySet()){
            Skin skin = playerSkins.get(uuid);
            List<Object> list = new ArrayList<>();
            list.add(uuid);
            list.add(skin);
            saveObjectToDatabase(list, database);
        }
    }

    @Override
    public void loadFromDatabase(Database database) {
        try(Connection connection = database.getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM cloud_skins");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){

                UUID uuid = UUID.fromString(resultSet.getString("uuid"));
                String name = resultSet.getString("name");
                String value = resultSet.getString("value");
                String signature = resultSet.getString("signature");
                LocalDateTime lastUpdate = resultSet.getTimestamp("lastUpdate").toLocalDateTime();

                playerSkins.put(uuid, new Skin(name, value, signature, lastUpdate, core));

            }

            preparedStatement.close();

        } catch (SQLException exception){
            exception.printStackTrace();
        }
    }

    @Override
    public void saveObjectToDatabase(Object object, Database database) {
        List list = (List) object;
        try(Connection connection = database.getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO cloud_skins (uuid, name, value, signature, lastUpdate) VALUES (?, ?, ?, ?, ?) " + core.getUpdateMethod() + " name=?, value=?, signature=?, lastUpdate=?");

            preparedStatement.setString(1, list.get(0).toString());

            Skin skin = (Skin) list.get(1);

            preparedStatement.setString(2, skin.getName());
            preparedStatement.setString(3, skin.getValue());
            preparedStatement.setString(4, skin.getSignature());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(skin.getLastUpdate()));

            preparedStatement.setString(6, skin.getName());
            preparedStatement.setString(7, skin.getValue());
            preparedStatement.setString(8, skin.getSignature());
            preparedStatement.setTimestamp(9, Timestamp.valueOf(skin.getLastUpdate()));

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException exception){
            exception.printStackTrace();
        }
    }
}
