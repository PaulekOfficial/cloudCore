package com.paulek.core.commands.cmds.admin;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class GcCMD extends Command {

    public GcCMD(Core core) {
        super("gc", "Check server status", "/gc", "core.cmd.gc", new String[]{"perferomance", "status"}, core);
    }

    //TODO to fix
    @Override
    public boolean execute(CommandSender sender, String[] args) {

        int loadedChunks = 0;
        int entities = 0;
        for (World world : Bukkit.getWorlds()) {
            loadedChunks += world.getLoadedChunks().length;
            entities += world.getEntities().size();
        }
        int freeMemory = (int) Runtime.getRuntime().freeMemory() / 1024 / 1024;
        int totalMemory = (int) Runtime.getRuntime().totalMemory() / 1024 / 1024;
        int maxMemory = (int) Runtime.getRuntime().maxMemory() / 1024 / 1024;
        StringBuilder tasks = new StringBuilder();
        for (BukkitTask bukkitTask : Bukkit.getScheduler().getPendingTasks()) {
            StringBuilder sb = new StringBuilder();
            sb.append("taks id: ");
            sb.append(bukkitTask.getTaskId());
            sb.append(" sync: ");
            sb.append(bukkitTask.isSync());
            sb.append(" plugin owner: ");
            sb.append(bukkitTask.getOwner().getName());
            tasks.append("§a" + sb.toString() + System.lineSeparator());
        }

        String messageToSend = Lang.INFO_GC;
        messageToSend = messageToSend.replace("{freememory}", String.valueOf(freeMemory));
        messageToSend = messageToSend.replace("{maxmemory}", String.valueOf(maxMemory));
        messageToSend = messageToSend.replace("{totalmemory}", String.valueOf(totalMemory));
        messageToSend = messageToSend.replace("{entities}", String.valueOf(entities));
        messageToSend = messageToSend.replace("{chunks}", String.valueOf(loadedChunks));
        messageToSend = messageToSend.replace("{tasks}", tasks.toString());
        messageToSend = messageToSend.replace("/n", System.lineSeparator());

        sender.sendMessage(ColorUtil.fixColor(messageToSend));

        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }

}
