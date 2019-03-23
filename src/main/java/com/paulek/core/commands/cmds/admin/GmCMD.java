package com.paulek.core.commands.cmds.admin;

import com.paulek.core.commands.Command;
import com.paulek.core.common.Util;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GmCMD extends Command{

    public GmCMD(){
        super("gm", "change your gamemode", "/gm (1,2,3)", "core.cmd.gm", new String[] {"gamemode"});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if(args.length < 1){
            sender.sendMessage("§c"+this.getUsage());
        }
        if(args.length == 1){
            if(sender instanceof Player) {
                Player player = (Player)sender;
                if (args[0].equalsIgnoreCase("0")) {
                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage(Util.fixColor(Lang.INFO_GM_CHANGE.replace("{gamemode}", "SURVIVAL")));
                }
                if (args[0].equalsIgnoreCase("1")) {
                    player.setGameMode(GameMode.CREATIVE);
                    player.sendMessage(Util.fixColor(Lang.INFO_GM_CHANGE.replace("{gamemode}", "CREATIVE")));
                }
                if (args[0].equalsIgnoreCase("2")) {
                    player.setGameMode(GameMode.ADVENTURE);
                    player.sendMessage(Util.fixColor(Lang.INFO_GM_CHANGE.replace("{gamemode}", "ADVENTURE")));
                }
                if (args[0].equalsIgnoreCase("3")) {
                    player.setGameMode(GameMode.SPECTATOR);
                    player.sendMessage(Util.fixColor(Lang.INFO_GM_CHANGE.replace("{gamemode}", "SPECTATOR")));
                }
                if (args[0].equalsIgnoreCase("s")) {
                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage(Util.fixColor(Lang.INFO_GM_CHANGE.replace("{gamemode}", "SURVIVAL")));
                }
                if (args[0].equalsIgnoreCase("c")) {
                    player.setGameMode(GameMode.CREATIVE);
                    player.sendMessage(Util.fixColor(Lang.INFO_GM_CHANGE.replace("{gamemode}", "CREATIVE")));
                }
                if (args[0].equalsIgnoreCase("a")) {
                    player.setGameMode(GameMode.ADVENTURE);
                    player.sendMessage(Util.fixColor(Lang.INFO_GM_CHANGE.replace("{gamemode}", "ADVENTURE")));
                }
            } else {
                sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
            }
        }
        if(args.length == 2){
            Player player = null;
            if(Bukkit.getPlayerExact(args[1]) != null){
                player = Bukkit.getPlayerExact(args[1]);
            } else {
                sender.sendMessage(Util.fixColor(Lang.ERROR_GM_NOTONLINE));
            }

            if (args[0].equalsIgnoreCase("0")) {
                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(Util.fixColor(Lang.INFO_GM_CHANGE.replace("{gamemode}", "SURVIVAL")));
                sender.sendMessage(Util.fixColor(Lang.INFO_GM_CHANGEPLAYER));
            }
            if (args[0].equalsIgnoreCase("1")) {
                player.setGameMode(GameMode.CREATIVE);
                player.sendMessage(Util.fixColor(Lang.INFO_GM_CHANGE.replace("{gamemode}", "CREATIVE")));
                sender.sendMessage(Util.fixColor(Lang.INFO_GM_CHANGEPLAYER));
            }
            if (args[0].equalsIgnoreCase("2")) {
                player.setGameMode(GameMode.ADVENTURE);
                player.sendMessage(Util.fixColor(Lang.INFO_GM_CHANGE.replace("{gamemode}", "ADVENTURE")));
                sender.sendMessage(Util.fixColor(Lang.INFO_GM_CHANGEPLAYER));
            }
            if (args[0].equalsIgnoreCase("3")) {
                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage(Util.fixColor(Lang.INFO_GM_CHANGE.replace("{gamemode}", "SPECTATOR")));
                sender.sendMessage(Util.fixColor(Lang.INFO_GM_CHANGEPLAYER));
            }
            if (args[0].equalsIgnoreCase("s")) {
                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(Util.fixColor(Lang.INFO_GM_CHANGE.replace("{gamemode}", "SURVIVAL")));
                sender.sendMessage(Util.fixColor(Lang.INFO_GM_CHANGEPLAYER));
            }
            if (args[0].equalsIgnoreCase("c")) {
                player.setGameMode(GameMode.CREATIVE);
                player.sendMessage(Util.fixColor(Lang.INFO_GM_CHANGE.replace("{gamemode}", "CREATIVE")));
                sender.sendMessage(Util.fixColor(Lang.INFO_GM_CHANGEPLAYER));
            }
            if (args[0].equalsIgnoreCase("a")) {
                player.setGameMode(GameMode.ADVENTURE);
                player.sendMessage(Util.fixColor(Lang.INFO_GM_CHANGE.replace("{gamemode}", "ADVENTURE")));
                sender.sendMessage(Util.fixColor(Lang.INFO_GM_CHANGEPLAYER));
            }
        }
        return false;
    }
}
