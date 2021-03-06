package com.paulek.core.commands.cmds.user;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RCMD extends Command {

    public RCMD(Core core) {
        super("r", "reply to a message", "/r {message}", "core.cmd.r", new String[]{}, core);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length != 0) {
            UUID uuid = ((Player) sender).getUniqueId();
            UUID targetPlayerUUID = getCore().getPrivateMessagesStorage().get(uuid);
            if (targetPlayerUUID != null) {
                StringBuilder s = new StringBuilder();
                for (int i = 0; i != args.length; i++) {
                    s.append(args[i]).append(" ");
                }
                String message = ColorUtil.fixColor(Lang.INFO_MSG_FORMAT.replace("{message}", s.toString()));
                Player player = Bukkit.getPlayer(targetPlayerUUID);
                if (player != null) {
                    player.sendMessage(message.replace("{from}", sender.getName()));
                    sender.sendMessage(message.replace("{from}", sender.getName()));
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        UUID u = p.getUniqueId();
                        if (getCore().getUsersStorage().get(u).isSocialSpy()) {
                            String m = ColorUtil.fixColor(Lang.INFO_MSG_SPYFORMAT.replace("{from}", sender.getName()).replace("{to}", player.getDisplayName()).replace("{message}", s.toString()));
                            p.sendMessage(m);
                        }
                    }
                } else {
                    sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_MSG_NOTHINGTOANSWER));
                }
            } else {
                sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_MSG_PLAYEROFF));
            }
        } else {
            sender.sendMessage(getUsage());
        }
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }

}
