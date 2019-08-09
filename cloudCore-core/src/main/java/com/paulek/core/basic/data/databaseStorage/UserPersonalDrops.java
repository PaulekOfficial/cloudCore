package com.paulek.core.basic.data.databaseStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserPersonalDrops {

    private Map<UUID, Map<String, Boolean>> userPersonalDropsSettings;

    public void init(){
        userPersonalDropsSettings = new HashMap<>();
    }

    public void setUserDropSettings(UUID uuid, String dropName, boolean value){
        userPersonalDropsSettings.get(uuid).put(dropName, value);
    }

}
