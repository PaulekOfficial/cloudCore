package com.paulek.core.common;

import com.paulek.core.Core;
import org.bukkit.plugin.Plugin;

import java.util.Objects;
import java.util.logging.Level;

public class Version {

    private Plugin plugin;
    private String a = plugin.getServer().getClass().getPackage().getName();
    private String version = a.substring(a.lastIndexOf('.') + 1);
    private Core core;

    public Version(Core core) {
        this.core = Objects.requireNonNull(core, "Core");
        plugin = core.getPlugin();
    }

    public String getVersion() {
        return version;
    }

    public void chceckVersion() {
        if (!version.contains("v1_13")) {
            core.getConsoleLog().log("Error! Plugin writen to 1.13ś Version! Pleace change Version!", Level.WARNING);
            core.getConsoleLog().log("ShutingDown clCore!", Level.INFO);
            plugin.getPluginLoader().disablePlugin(plugin);
        } else {
            core.getConsoleLog().info("Hello, your Version is ok! runing: " + version);
        }
    }

}
