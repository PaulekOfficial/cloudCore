package com.paulek.core.commands.cmds.user;

import com.paulek.core.basic.data.TpaStorage;
import com.paulek.core.commands.Command;
import com.paulek.core.common.Util;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TpadenyCMD extends Command {

    public TpadenyCMD() {
        super("tpadeny", "deny teleport", "/tpadeny", "core.cmd.tpadeny", new String[]{});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(sender instanceof Player){

            Player player = (Player)sender;

            UUID uuid = player.getUniqueId();

            if(TpaStorage.getToAcceptTpahere(uuid) != null){

                if(Bukkit.getPlayer(TpaStorage.getToAcceptTpahere(uuid)) != null) Bukkit.getPlayer(TpaStorage.getToAcceptTpahere(uuid)).sendMessage(Util.fixColor(Lang.INFO_TPADENY_REJECTED));

                TpaStorage.removeToAcceptTpahere(uuid);
                TpaStorage.cancelTaskTpahere(uuid);

                sender.sendMessage(Util.fixColor(Lang.INFO_TPADENY_DENY));

            } else if(TpaStorage.getToAcceptTpa(uuid) != null){

                if(Bukkit.getPlayer(TpaStorage.getToAcceptTpa(uuid)) != null) Bukkit.getPlayer(TpaStorage.getToAcceptTpa(uuid)).sendMessage(Util.fixColor(Lang.INFO_TPADENY_REJECTED));

                TpaStorage.removeToAcceptTpa(uuid);
                TpaStorage.cancelTaskTpa(uuid);

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
