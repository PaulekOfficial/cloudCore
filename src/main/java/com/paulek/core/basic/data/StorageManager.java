package com.paulek.core.basic.data;

import com.paulek.core.Core;
import com.paulek.core.basic.database.Database;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StorageManager implements Runnable {

    List<Storage> storages;
    private Core core;
    private Database database;

    public StorageManager(Core core, Database database){
        storages = new ArrayList<>();
        this.core = Objects.requireNonNull(core, "Core");
        this.database = database;
    }

    public void init(){
        Bukkit.getScheduler().runTaskTimerAsynchronously(core.getPlugin(), this, 20, 5*60*20);
    }

    public void addManagedStorage(Storage storage){
        storages.add(storage);
    }

    @Override
    public void run(){
        core.getConsoleLog().info("Saving all server data");
        saveAllData();
    }

    public void saveAllData(){
        for(Storage storage : storages){
            storage.saveDirtyObjects(database);
        }
    }
}
