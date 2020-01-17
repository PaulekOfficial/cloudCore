package com.paulek.core.commands.cmds.user;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.io.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
            LocalDateTime time = getCore().getTeleportRequestsStorage().get(uuid).getD();
            if (time != null) {
                getCore().getTeleportRequestsStorage().delete(uuid);
                sender.sendMessage(ColorUtil.fixColor(Lang.INFO_TPADENY_DENY));
                return true;
            }
            sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_TPADENY_NOTHING));
        } else {
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
        }
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }

}
