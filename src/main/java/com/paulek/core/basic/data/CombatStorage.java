package com.paulek.core.basic.data;

import com.paulek.core.basic.Warrior;

import java.util.HashMap;
import java.util.UUID;

public class CombatStorage {

    private static HashMap<UUID, Warrior> marked = new HashMap<UUID, Warrior>();

    public CombatStorage(Warrior playerObject){
        marked.put(playerObject.getUuid(), playerObject);
    }


    public static boolean isMarked(UUID uuid){
        return marked.containsKey(uuid);
    }

    public static HashMap<UUID, Warrior> getMarked() {
        return marked;
    }

    public static void unmark(UUID uuid) {
        marked.remove(uuid);
    }

    public static void changeTimeMilisrs(UUID uuid, long time){
        marked.get(uuid).setCurenttimemilirs(time);
    }
}
