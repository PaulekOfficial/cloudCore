package com.paulek.core.basic.data;

import com.paulek.core.basic.Timestamp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Timestamps {

    private Map<UUID, List<Timestamp>> userTimestamps;

    public void init(){
        userTimestamps = new HashMap<>();
    }

    public Timestamp getTimestamp(UUID uuid, String serviceName){

        for(Timestamp timestamp : userTimestamps.get(uuid)){
            if(timestamp.getServiceName().equalsIgnoreCase(serviceName)) return timestamp;
        }

        return null;
    }

    public void addTimestamp(UUID uuid, Timestamp timestamp){
        userTimestamps.get(uuid).add(timestamp);
    }

    public void removeTimestamp(UUID uuid, Timestamp timestamp){
        userTimestamps.get(uuid).remove(timestamp);
    }

}
