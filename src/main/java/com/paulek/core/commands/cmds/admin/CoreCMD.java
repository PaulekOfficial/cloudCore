package com.paulek.core.commands.cmds.admin;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.io.Lang;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CoreCMD extends Command {

    public CoreCMD(Core core) {
        super("core", "all informations about core", "/core", "core.cmd.core", new String[]{"serwer", "Version", "about"}, core);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("reload")) {
                getCore().reloadConfigs();
                getCore().getLang().reloadLang();
                getCore().getKits().reload();
                //getCore().getDrops().reload();
                sender.sendMessage(ColorUtil.fixColor(Lang.INFO_CORE_RELOAD));
            }
            if (args[0].equalsIgnoreCase("stop")) {
                sender.sendMessage(ColorUtil.fixColor(Lang.INFO_CORE_DISABLEING));
                getCore().getPlugin().getServer().shutdown();
            }
        } else {
            sender.sendMessage("§e§l           CloudLandCore");
            sender.sendMessage("§e§lcreated by PaulekOfficial");
            sender.sendMessage("§e§lversion: " + getCore().getVersion());
            sender.sendMessage("§e§lType /core reload | to reload!");
            sender.sendMessage("§e§lType /core stop | to stop the serwer!");
        }
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {

        if(args.length == 1){
            return Arrays.asList("reload", "stop");
        }

        return new ArrayList<>();
    }

}
