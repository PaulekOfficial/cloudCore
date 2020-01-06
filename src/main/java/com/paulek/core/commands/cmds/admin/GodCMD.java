package com.paulek.core.commands.cmds.admin;

import com.paulek.core.Core;
import com.paulek.core.basic.User;
import com.paulek.core.commands.Command;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GodCMD extends Command {

    public GodCMD(Core core){
        super("god", "Sets godmode for player", "/god <player>", "core.cmd.god", new String[]{}, Objects.requireNonNull(core, "Core"));
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(args.length == 2){

            Player player = Bukkit.getPlayer(args[0]);

            if(player == null){
                sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_GOD_PLAYERNOTFOUND));
                return false;
            }
            User user = getCore().getUsersStorage().get(player.getUniqueId());
            if(user.isGodMode()){
                user.setGodMode(false);
                user.setDirty(true);

                player.sendMessage(ColorUtil.fixColor(Lang.INFO_GOD_DISABLE));
                sender.sendMessage(ColorUtil.fixColor(Lang.INFO_GOD_DISABLEPLAYER.replace("{player}", player.getDisplayName())));

                return false;
            }
            user.setGodMode(true);
            user.setDirty(true);

            player.setHealth(20);
            player.setFoodLevel(20);
            player.setFireTicks(0);
            player.setSaturation(10);
            player.getActivePotionEffects().clear();

            player.sendMessage(ColorUtil.fixColor(Lang.INFO_GOD_ENABLE));
            sender.sendMessage(ColorUtil.fixColor(Lang.INFO_GOD_ENABLEPLAYER.replace("{player}", player.getDisplayName())));

            return false;
        } else if (args.length == 0){

            if(!(sender instanceof Player)){
                sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_GOD_MUSTBEPLAYER));
            }

            Player player = (Player)sender;

            User user = getCore().getUsersStorage().get(player.getUniqueId());
            if(user.isGodMode()){
                user.setGodMode(false);
                user.setDirty(true);

                player.sendMessage(ColorUtil.fixColor(Lang.INFO_GOD_DISABLE));

                return false;
            }
            user.setGodMode(true);
            user.setDirty(true);

            player.setHealth(20);
            player.setFoodLevel(20);
            player.setFireTicks(0);
            player.setSaturation(10);
            player.getActivePotionEffects().clear();

            player.sendMessage(ColorUtil.fixColor(Lang.INFO_GOD_ENABLE));
            return false;
        }

        sender.sendMessage(ColorUtil.fixColor(getUsage()));

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
