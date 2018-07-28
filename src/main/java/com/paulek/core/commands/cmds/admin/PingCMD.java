package com.paulek.core.commands.cmds.admin;

import com.paulek.core.commands.Command;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.utils.Util;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class PingCMD extends Command {

    public PingCMD(){
        super("ping", "get player ping", "/ping {player}", "core.ping", new String[]{});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(args.length == 0) {

            if(sender instanceof Player){

                sender.sendMessage(Util.fixColor(Lang.INFO_PING_FORMAT.replace("{player}", sender.getName()).replace("{ping}", "0")));

                return false;
            }

            Player player = (Player) sender;

            EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();

            try {

                Field ping = entityPlayer.getClass().getDeclaredField("ping");

                String ping_string = String.valueOf(ping.getInt(entityPlayer));

                sender.sendMessage(Util.fixColor(Lang.INFO_PING_FORMAT.replace("{player}", player.getDisplayName()).replace("{ping}", ping_string)));

            } catch (Exception e) {

                sender.sendMessage(Util.fixColor(Lang.ERROR_PING_GET));

                return false;
            }

        } else if(args.length == 1) {

            if(Bukkit.getPlayer(args[0]) != null) {

                Player player = Bukkit.getPlayer(args[0]);

                EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();

                try {

                    Field ping = entityPlayer.getClass().getDeclaredField("ping");

                    String ping_string = String.valueOf(ping.getInt(entityPlayer));

                    sender.sendMessage(Util.fixColor(Lang.INFO_PING_FORMAT.replace("{player}", player.getDisplayName()).replace("{ping}", ping_string)));

                } catch (Exception e) {

                    sender.sendMessage(Util.fixColor(Lang.ERROR_PING_GET));

                    return false;
                }

            } else {

                sender.sendMessage(Util.fixColor(Lang.ERROR_PING_PLAYEROFFINLE));

                return false;
            }

        }

        sender.sendMessage(getUsage());

        return false;
    }
}
