package com.paulek.core.commands.cmds.admin;

import com.paulek.core.commands.Command;
import com.paulek.core.common.Util;
import com.paulek.core.common.io.Lang;
import net.minecraft.server.v1_13_R2.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitTask;

public class GcCMD extends Command {

    public GcCMD(){
        super("gc", "Check server status", "/gc", "core.cmd.gc", new String[]{"perferomance", "status"});
    }

    //TODO to fix
    @Override
    public boolean execute(CommandSender sender, String[] args) {

        int loadedChunks =  0;
        int entities = 0;
        for(World world : Bukkit.getWorlds()){
            loadedChunks += world.getLoadedChunks().length;
            entities += world.getEntities().size();
        }
        int freeMemory = (int) Runtime.getRuntime().freeMemory() / 1024 / 1024;
        int totalMemory = (int) Runtime.getRuntime().totalMemory() / 1024 / 1024;
        int maxMemory = (int) Runtime.getRuntime().maxMemory() / 1024 / 1024;
        double tps = MinecraftServer.getServer().recentTps[0];
        StringBuilder tasks = new StringBuilder();
        for(BukkitTask bukkitTask : Bukkit.getScheduler().getPendingTasks()){
            StringBuilder sb = new StringBuilder();
            sb.append("taks id: ");
            sb.append(bukkitTask.getTaskId());
            sb.append(" sync: ");
            sb.append(bukkitTask.isSync());
            sb.append(" cancelled: ");
            sb.append(bukkitTask.isCancelled());
            sb.append(" plugin owner: ");
            sb.append(bukkitTask.getOwner().getName());
            tasks.append("§a" + sb.toString() + System.lineSeparator());
        }

        String messageToSend = Lang.INFO_GC;
        messageToSend = messageToSend.replace("{tps}", String.valueOf(tps));
        messageToSend = messageToSend.replace("{freememory}", String.valueOf(freeMemory));
        messageToSend = messageToSend.replace("{maxmemory}", String.valueOf(maxMemory));
        messageToSend = messageToSend.replace("{totalmemory}", String.valueOf(totalMemory));
        messageToSend = messageToSend.replace("{entities}", String.valueOf(entities));
        messageToSend = messageToSend.replace("{chunks}", String.valueOf(loadedChunks));
        messageToSend = messageToSend.replace("{tasks}", tasks.toString());
        messageToSend = messageToSend.replace("/n", System.lineSeparator());

        sender.sendMessage(Util.fixColor(messageToSend));

        return false;
    }
}
