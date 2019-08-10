package com.paulek.core.commands.cmds.admin;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.ReflectionUtils;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class PingCMD extends Command {

    public PingCMD(Core core) {
        super("ping", "get player ping", "/ping {player}", "core.cmd.ping", new String[]{}, core);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if (args.length == 0) {

            if (sender instanceof Player) {

                sender.sendMessage(ColorUtil.fixColor(Lang.INFO_PING_FORMAT.replace("{player}", sender.getName()).replace("{ping}", "0")));

                return false;
            }

            Player player = (Player) sender;

            try {

                if (getPlayerPing(player, sender)) return false;

            } catch (Exception e){
                e.printStackTrace();
            }

        } else if (args.length == 1) {

            if (Bukkit.getPlayer(args[0]) != null) {

                Player player = Bukkit.getPlayer(args[0]);

                try {

                    if (getPlayerPing(player, sender)) return false;

                } catch (Exception e){
                    e.printStackTrace();
                }

            } else {

                sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_PING_PLAYEROFFINLE));

                return false;
            }

        }

        sender.sendMessage(getUsage());

        return false;
    }

    private boolean getPlayerPing(Player player, CommandSender sender) throws Exception{

        Object craftPlayer = ReflectionUtils.getNMSPlayer(player);

        Method getHandle = ReflectionUtils.getMethod(craftPlayer.getClass(), "getHandle", null);

        Object entityPlayer = getHandle.invoke(craftPlayer);

        try {

            Field ping = entityPlayer.getClass().getDeclaredField("ping");

            String ping_string = String.valueOf(ping.getInt(entityPlayer));

            sender.sendMessage(ColorUtil.fixColor(Lang.INFO_PING_FORMAT.replace("{player}", player.getDisplayName()).replace("{ping}", ping_string)));

        } catch (Exception e) {

            sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_PING_GET));

            return true;
        }
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {

        if(args.length == 1){
            List<String> playerList = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                playerList.add(player.getDisplayName());
            }
            return playerList;
        }

        return new ArrayList<>();
    }

}
