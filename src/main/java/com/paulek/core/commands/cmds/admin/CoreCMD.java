package com.paulek.core.commands.cmds.admin;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.data.configs.Config;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.utils.Util;
import org.bukkit.command.CommandSender;

public class CoreCMD extends Command {

    public CoreCMD(){
        super("core", "all informations about core", "/core", "core.system", new String[] {"serwer", "version", "about"});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if(args.length > 0) {
            if (args[0].equalsIgnoreCase("reload")) {
                Config.reloadConfig();
                Lang.reloadLang();
                sender.sendMessage(Util.fixColor(Lang.INFO_CORE_RELOAD));
            }
            if (args[0].equalsIgnoreCase("stop")) {
                sender.sendMessage(Util.fixColor(Lang.INFO_CORE_DISABLEING));
                Core.getPlugin().getServer().shutdown();
            }
        } else {
            sender.sendMessage("§e§l           CloudLandCore");
            sender.sendMessage("§e§lcreated by PaulekOfficial");
            sender.sendMessage("§e§lversion: " + Core.getVersion());
            sender.sendMessage("§e§lactual tps: " + " ");
            sender.sendMessage("§e§lType /core reload | to reload!");
            sender.sendMessage("§e§lType /core stop | to stop the serwer!");
        }
        return false;
    }
}
