package com.paulek.core.data;

import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.UUID;

public class TpaStorage {

    private static HashMap<UUID, Integer> WAITING_TO_ACCEPT_TASK_TPA = new HashMap<UUID, Integer>();
    private static HashMap<UUID, UUID> WAITING_TO_ACCEPT_TPA = new HashMap<UUID, UUID>();

    private static HashMap<UUID, Integer> WAITING_TO_ACCEPT_TASK_TPA_HERE = new HashMap<UUID, Integer>();
    private static HashMap<UUID, UUID> WAITING_TO_ACCEPT_TPA_HERE = new HashMap<UUID, UUID>();


    public static UUID getToAcceptTpa(UUID uuid){
        return WAITING_TO_ACCEPT_TPA.get(uuid);
    }

    public static void addToAcceptTpa(UUID a, UUID b){
        WAITING_TO_ACCEPT_TPA.put(a, b);
    }

    public static void removeToAcceptTpa(UUID uuid){
        if(WAITING_TO_ACCEPT_TPA.containsKey(uuid)) WAITING_TO_ACCEPT_TPA.remove(uuid);
    }

    public static void cancelTaskTpa(UUID uuid){
        if(WAITING_TO_ACCEPT_TASK_TPA.containsKey(uuid)){

            try{
                Bukkit.getScheduler().cancelTask(WAITING_TO_ACCEPT_TASK_TPA.get(uuid));
            } catch (Exception e){}

        }
        WAITING_TO_ACCEPT_TASK_TPA.remove(uuid);
    }

    public static void addTaskTpahere(UUID uuid, int id){
        WAITING_TO_ACCEPT_TASK_TPA_HERE.put(uuid, id);
    }

    public static UUID getToAcceptTpahere(UUID uuid){
        return WAITING_TO_ACCEPT_TPA_HERE.get(uuid);
    }

    public static void addToAcceptTpahere(UUID a, UUID b){
        WAITING_TO_ACCEPT_TPA_HERE.put(a, b);
    }

    public static void removeToAcceptTpahere(UUID uuid){
        if(WAITING_TO_ACCEPT_TPA.containsKey(uuid)) WAITING_TO_ACCEPT_TPA_HERE.remove(uuid);
    }

    public static void cancelTaskTpahere(UUID uuid){
        if(WAITING_TO_ACCEPT_TASK_TPA_HERE.containsKey(uuid)){

            try{
                Bukkit.getScheduler().cancelTask(WAITING_TO_ACCEPT_TASK_TPA_HERE.get(uuid));
            } catch (Exception e){}

        }
        WAITING_TO_ACCEPT_TASK_TPA_HERE.remove(uuid);
    }

    public static void addTaskTpa(UUID uuid, int id){
        WAITING_TO_ACCEPT_TASK_TPA.put(uuid, id);
    }

}
