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



        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }

}
