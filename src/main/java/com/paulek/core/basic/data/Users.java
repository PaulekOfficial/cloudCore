package com.paulek.core.basic.data;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.paulek.core.Core;
import com.paulek.core.basic.Skin;
import com.paulek.core.basic.User;
import com.paulek.core.basic.database.Database;
import com.paulek.core.common.*;
import com.paulek.core.common.io.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Users{

    private Map<UUID, User> users = new HashMap<UUID, User>();
    private Core core;
    private Database database;

    public Users(Core core, Database database) {
        this.core = Objects.requireNonNull(core, "Core");
        this.database = database;
    }

    public void init(){
        database.init();

        //TODO Load all players from database
    }

    public void saveUser(User user){
        //TODO Save user content
        try(Connection connection = database.getConnection()){



        } catch (SQLException exception){
            exception.printStackTrace();
        }

    }

    public Map<UUID, User> getUsers() {
        return users;
    }

    public User getUser(UUID uuid) {
        return users.get(uuid);
    }
}
