package com.paulek.core.commands.cmds.admin;

import com.paulek.core.commands.Command;
import org.bukkit.command.CommandSender;

public class TphereCMD extends Command {

    public TphereCMD(){
        super("tphere", "teleport a player to you", "/tphere {player}", "core.tphere", new String[]{});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        //TODO Make this command

        return false;
    }
}
