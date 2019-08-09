package com.paulek.core.basic.data;

import com.paulek.core.basic.Warrior;

import java.util.HashMap;
import java.util.UUID;

public class CombatStorage {

    private HashMap<UUID, Warrior> marked = new HashMap<UUID, Warrior>();

    public void addMarkedWarrior(Warrior playerObject) {
        marked.put(playerObject.getUuid(), playerObject);
    }


    public boolean isMarked(UUID uuid) {
        return marked.containsKey(uuid);
    }

    public HashMap<UUID, Warrior> getMarked() {
        return marked;
    }

    public void unmark(UUID uuid) {
        marked.remove(uuid);
    }

    public void changeTimeMilisrs(UUID uuid, long time) {
        marked.get(uuid).setCurenttimemilirs(time);
    }
}
