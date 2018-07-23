package com.paulek.core.utils;

import com.paulek.core.Core;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class version {

    private Plugin plugin = Core.getPlugin();
    private String a = plugin.getServer().getClass().getPackage().getName();
    private String version = a.substring(a.lastIndexOf('.') + 1);

    public String getVersion() {
        return version;
    }

    public void chceckVersion(){
        if(!version.contains("v1_12")){
            consoleLog.log("Error! Plugin writen to 1.12 version! Pleace change version!" , Level.WARNING);
            consoleLog.log("ShutingDown clCore!", Level.INFO);
            plugin.getPluginLoader().disablePlugin(plugin);
        } else {
            consoleLog.info("Hello, your version is ok! runing: " + version);
        }
    }

}
