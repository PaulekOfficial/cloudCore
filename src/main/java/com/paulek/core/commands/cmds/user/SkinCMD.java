package com.paulek.core.commands.cmds.user;

import com.paulek.core.Core;
import com.paulek.core.basic.skin.Skin;
import com.paulek.core.commands.Command;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.MojangApiUtil;
import com.paulek.core.common.io.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SkinCMD extends Command {

    public SkinCMD(Core core) {
        super("skin", "set yours skin", "/skin {set, clear} {nick}", "core.cmd.skin", new String[]{}, core);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(sender instanceof Player){

            Player player = (Player)sender;

            if(args.length >= 1){

                if(args[0].equalsIgnoreCase("clear")){

                    if(args.length == 1) {
                        Skin skin = getCore().getSkinsStorage().get(player.getUniqueId());
                        skin.setLastUpdate(LocalDateTime.MIN);
                        skin.setManuallySet(false);
                        getCore().getSkinsStorage().save(skin);
                        getCore().getSkinsStorage().delete(player.getUniqueId());
                        skin = getCore().getSkinsStorage().get(player.getUniqueId());
                        if(skin != null) {
                            skin.setDirty(true);
                            getCore().getSkinsStorage().add(player.getUniqueId(), skin);
                            skin.applySkinForPlayers(player);
                            skin.updateSkinForPlayer(player);
                        }
                        sender.sendMessage(ColorUtil.fixColor(Lang.INFO_SKIN_CLEAR));
                    } else {
                        sender.sendMessage(getUsage());
                    }

                } else if(args[0].equalsIgnoreCase("set")){

                    if(args.length == 2) {
                        String skin_string = args[1];
                        Skin skin = MojangApiUtil.getPremiumSkin(skin_string, getCore());
                        if(skin != null) {
                            skin.setDirty(true);
                            skin.setManuallySet(true);
                            skin.setLastUpdate(LocalDateTime.now().plusYears(1));
                            getCore().getSkinsStorage().add(player.getUniqueId(), skin);
                            skin.updateSkinForPlayer(player);
                            skin.applySkinForPlayers(player);
                            sender.sendMessage(ColorUtil.fixColor(Lang.INFO_SKIN_CHANGED));
                        } else {
                            sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_SKIN_NOTPREMIUM));
                        }
                    } else {
                        sender.sendMessage(getUsage());
                    }
                } else {
                    sender.sendMessage(getUsage());
                }
            } else {
                sender.sendMessage(getUsage());
            }
        } else {
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
        }
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args){
        if(args.length == 1){
            return Arrays.asList("set", "clear");
        }
        return new ArrayList<>();
    }
}
