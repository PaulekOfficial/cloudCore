package com.paulek.core.basic.data;

import com.paulek.core.basic.Skin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Skins {

    private Map<UUID, Skin> playerSkins;

    public void init(){
        playerSkins = new HashMap<>();
    }

    public Skin getSkin(UUID uuid){
        return playerSkins.get(uuid);
    }

    public void addSkin(UUID uuid, Skin skin){
        playerSkins.put(uuid, skin);
    }
}
