package com.paulek.core.commands.cmds.user;

import com.paulek.core.commands.Command;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TpadenyCMD extends Command {

    public TpadenyCMD() {
        super("tpadeny", "deny teleport", "/tpadeny", "core.tpadeny", new String[]{});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(sender instanceof Player){

            Player player = (Player)sender;

            UUID uuid = player.getUniqueId();

            if(TpahereCMD.getWaiting_to_accept().containsKey(uuid)){

                if(Bukkit.getPlayer(TpahereCMD.getWaiting_to_accept().get(uuid)) != null) Bukkit.getPlayer(TpahereCMD.getWaiting_to_accept().get(uuid)).sendMessage(Util.fixColor(Lang.INFO_TPADENY_REJECTED));

                TpahereCMD.getWaiting_to_accept().remove(uuid);

                int id = TpahereCMD.getWaiting_to_accept_id().get(uuid);
                TpahereCMD.getWaiting_to_accept_id().remove(uuid);

                Bukkit.getScheduler().cancelTask(id);

                sender.sendMessage(Util.fixColor(Lang.INFO_TPADENY_DENY));

            } else if(TpaCMD.getWaiting_to_accept().containsKey(uuid)){

                if(Bukkit.getPlayer(TpaCMD.getWaiting_to_accept().get(uuid)) != null) Bukkit.getPlayer(TpaCMD.getWaiting_to_accept().get(uuid)).sendMessage(Util.fixColor(Lang.INFO_TPADENY_REJECTED));

                TpaCMD.getWaiting_to_accept().remove(uuid);

                int id = TpaCMD.getWaiting_to_accept_id().get(uuid);
                TpaCMD.getWaiting_to_accept_id().remove(uuid);

                Bukkit.getScheduler().cancelTask(id);

                sender.sendMessage(Util.fixColor(Lang.INFO_TPADENY_DENY));

            } else {
                sender.sendMessage(Util.fixColor(Lang.ERROR_TPADENY_NOTHING));
            }

        } else {
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
        }


        return false;
    }
}
