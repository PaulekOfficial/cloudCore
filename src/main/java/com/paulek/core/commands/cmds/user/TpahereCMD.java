package com.paulek.core.commands.cmds.user;

import com.paulek.core.Core;
import com.paulek.core.basic.TeleportRequstType;
import com.paulek.core.basic.TriplePackage;
import com.paulek.core.commands.Command;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.LocationUtil;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TpahereCMD extends Command {

    public TpahereCMD(Core core) {
        super("tpahere", "teleport to you", "/tpahere {player}", "core.cmd.tpahere", new String[]{}, core);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
            return false;
        }
        final Player player = (Player) sender;
        if (args.length >= 1) {
            Player p = Bukkit.getPlayer(args[0]);
            if (p != null) {
                if (LocationUtil.isUserTpToogle(player, getCore())) {
                    return false;
                }
                getCore().getTeleportRequestsStorage().add(p.getUniqueId(), new TriplePackage<>(player.getUniqueId(), LocalDateTime.now(), TeleportRequstType.TPAHERE));
                sender.sendMessage(ColorUtil.fixColor(Lang.INFO_TPAHERE_REQUEST));
                p.sendMessage(ColorUtil.fixColor(Lang.INFO_TPAHERE_REQUESTPLAYER));
                return true;
            }
            sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_TPAHERE_NOPLAYER));
        }
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if(args.length == 1) {
            List<String> playerList = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                playerList.add(player.getDisplayName());
            }
            return playerList;
        }
        return new ArrayList<>();
    }
}
