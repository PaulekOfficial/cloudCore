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

public class MsgCMD extends Command {

    public MsgCMD(Core core) {
        super("msg", "sends a private message", "/msg {player} {message}", "core.cmd.msg", new String[]{"wiadomosc"}, core);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length != 0) {
            String tosend = args[0];
            if (Bukkit.getPlayer(tosend) != null) {
                UUID uuid = ((Player) sender).getUniqueId();
                Player send = Bukkit.getPlayer(tosend);
                assert send != null;
                this.getCore().getPrivateMessagesStorage().add(send.getUniqueId(), uuid);
                String s = "";
                for (int i = 1; i != args.length; i++) {
                    s += args[i] + " ";
                }
                String message = ColorUtil.fixColor(Lang.INFO_MSG_FORMAT).replace("{message}", s);
                send.sendMessage(message.replace("{from}", sender.getName()));
                sender.sendMessage(message.replace("{from}", sender.getName()));
                for (Player p : Bukkit.getOnlinePlayers()) {
                    UUID ue = p.getUniqueId();
                    if (getCore().getUsersStorage().get(ue).isSocialSpy()) {
                        String m = ColorUtil.fixColor(Lang.INFO_MSG_SPYFORMAT.replace("{from}", sender.getName()).replace("{to}", send.getDisplayName()).replace("{message}", s));
                        p.sendMessage(m);
                    }
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
