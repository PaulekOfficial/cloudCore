package com.paulek.core.commands.cmds.admin;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GmCMD extends Command {

    public GmCMD(Core core) {
        super("gm", "change your gamemode", "/gm (1,2,3)", "core.cmd.gm", new String[]{"gamemode"}, core);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("§c" + this.getUsage());
        }
        if (args.length == 1) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("survival")) {
                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage(ColorUtil.fixColor(Lang.INFO_GM_CHANGE.replace("{gamemode}", "SURVIVAL")));
                }
                if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("creative")) {
                    player.setGameMode(GameMode.CREATIVE);
                    player.sendMessage(ColorUtil.fixColor(Lang.INFO_GM_CHANGE.replace("{gamemode}", "CREATIVE")));
                }
                if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("adventure")) {
                    player.setGameMode(GameMode.ADVENTURE);
                    player.sendMessage(ColorUtil.fixColor(Lang.INFO_GM_CHANGE.replace("{gamemode}", "ADVENTURE")));
                }
                if (args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("spectator")) {
                    player.setGameMode(GameMode.SPECTATOR);
                    player.sendMessage(ColorUtil.fixColor(Lang.INFO_GM_CHANGE.replace("{gamemode}", "SPECTATOR")));
                }
            } else {
                sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
            }
        }
        if (args.length == 2) {
            Player player = null;
            if (Bukkit.getPlayerExact(args[1]) != null) {
                player = Bukkit.getPlayerExact(args[1]);
            } else {
                sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_GM_NOTONLINE));
            }

            if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("survival")) {
                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(ColorUtil.fixColor(Lang.INFO_GM_CHANGE.replace("{gamemode}", "SURVIVAL")));
                sender.sendMessage(ColorUtil.fixColor(Lang.INFO_GM_CHANGEPLAYER));
            }
            if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("c") || args[0].equalsIgnoreCase("creative")) {
                player.setGameMode(GameMode.CREATIVE);
                player.sendMessage(ColorUtil.fixColor(Lang.INFO_GM_CHANGE.replace("{gamemode}", "CREATIVE")));
                sender.sendMessage(ColorUtil.fixColor(Lang.INFO_GM_CHANGEPLAYER));
            }
            if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("adventure")) {
                player.setGameMode(GameMode.ADVENTURE);
                player.sendMessage(ColorUtil.fixColor(Lang.INFO_GM_CHANGE.replace("{gamemode}", "ADVENTURE")));
                sender.sendMessage(ColorUtil.fixColor(Lang.INFO_GM_CHANGEPLAYER));
            }
            if (args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("spectator")) {
                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage(ColorUtil.fixColor(Lang.INFO_GM_CHANGE.replace("{gamemode}", "SPECTATOR")));
                sender.sendMessage(ColorUtil.fixColor(Lang.INFO_GM_CHANGEPLAYER));
            }
        }
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {

        if(args.length == 1){
            return Arrays.asList("survival", "adventure", "creative", "spectator", "0", "1", "2", "3", "s", "c", "a");
        }

        return new ArrayList<>();
    }

}
