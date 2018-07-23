package com.paulek.core.data;

import com.paulek.core.data.objects.User;

import java.util.HashMap;
import java.util.UUID;

public class UserStorage {

    private static HashMap<UUID, User> users = new HashMap<UUID, User>();

    public static HashMap<UUID, User> getUsers() {
        return users;
    }

    public static void setUsers(HashMap<UUID, User> users) {
        UserStorage.users = users;
    }

    public static User getUser(UUID uuid){
        return users.get(uuid);
    }
}
