package com.paulek.core.common;

import com.paulek.core.Core;
import org.bukkit.plugin.Plugin;

import java.util.Objects;
import java.util.logging.Level;

public class Version {

    private Plugin plugin;
    private String a;
    private String version;
    private Core core;

    public Version(Core core) {
        this.core = Objects.requireNonNull(core, "Core");
        this.plugin = core.getPlugin();
        this.a = plugin.getServer().getClass().getPackage().getName();
        this.version = a.substring(a.lastIndexOf('.') + 1);
    }

    public String getVersion() {
        return version;
    }

    public void chceckVersion() {
        if (version.contains("v1_14") || version.contains("v1_8")) {
            core.getConsoleLog().info("Hello, your Version is ok! runing: " + version);
        } else {
            core.getConsoleLog().log("Error! Plugin writen to 1.14 Version! Pleace change Version!", Level.WARNING);
            core.getConsoleLog().log("ShutingDown clCore!", Level.INFO);
            plugin.getPluginLoader().disablePlugin(plugin);
        }
    }

}
