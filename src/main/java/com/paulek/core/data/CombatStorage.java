package com.paulek.core.data;

import com.paulek.core.data.objects.PlayerObject;

import java.util.HashMap;
import java.util.UUID;

public class CombatStorage {

    private static HashMap<UUID, PlayerObject> marked = new HashMap<UUID, PlayerObject>();

    public CombatStorage(PlayerObject playerObject){
        marked.put(playerObject.getUuid(), playerObject);
    }


    public static boolean isMarked(UUID uuid){
        return marked.containsKey(uuid);
    }

    public static HashMap<UUID, PlayerObject> getMarked() {
        return marked;
    }

    public static void unmark(UUID uuid) {
        marked.remove(uuid);
    }

    public static void changeTimeMilisrs(UUID uuid, long time){
        marked.get(uuid).setCurenttimemilirs(time);
    }
}
