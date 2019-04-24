package com.paulek.core.commands.cmds.user;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.common.Util;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TpadenyCMD extends Command {

    public TpadenyCMD(Core core) {
        super("tpadeny", "deny teleport", "/tpadeny", "core.cmd.tpadeny", new String[]{}, core);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;

            UUID uuid = player.getUniqueId();

            if (getCore().getTpaStorage().getToAcceptTpahere(uuid) != null) {

                if (Bukkit.getPlayer(getCore().getTpaStorage().getToAcceptTpahere(uuid)) != null)
                    Bukkit.getPlayer(getCore().getTpaStorage().getToAcceptTpahere(uuid)).sendMessage(Util.fixColor(Lang.INFO_TPADENY_REJECTED));

                getCore().getTpaStorage().removeToAcceptTpahere(uuid);
                getCore().getTpaStorage().cancelTaskTpahere(uuid);

                sender.sendMessage(Util.fixColor(Lang.INFO_TPADENY_DENY));

            } else if (getCore().getTpaStorage().getToAcceptTpa(uuid) != null) {

                if (Bukkit.getPlayer(getCore().getTpaStorage().getToAcceptTpa(uuid)) != null)
                    Bukkit.getPlayer(getCore().getTpaStorage().getToAcceptTpa(uuid)).sendMessage(Util.fixColor(Lang.INFO_TPADENY_REJECTED));

                getCore().getTpaStorage().removeToAcceptTpa(uuid);
                getCore().getTpaStorage().cancelTaskTpa(uuid);

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
