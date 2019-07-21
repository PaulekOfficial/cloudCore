package com.paulek.core.commands.cmds.user;

import com.paulek.core.Core;
import com.paulek.core.basic.Skin;
import com.paulek.core.commands.Command;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.MojangApiUtil;
import com.paulek.core.common.io.Config;
import com.paulek.core.common.io.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.util.Random;

public class SkinCMD extends Command {

    public SkinCMD(Core core) {
        super("skin", "set yours skin", "/skin {set, clear} {nick}", "core.cmd.skin", new String[]{}, core);
    }

    //TODO to fix
    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(sender instanceof Player){

            Player player = (Player)sender;

            if(args.length >= 1){

                if(args[0].equalsIgnoreCase("clear")){

                    if(args.length == 1) {

                        if(MojangApiUtil.getPremiumUuid(player.getDisplayName()) == null) {

                            if (getCore().getConfiguration().skinsNonPremium) {

                                if(getCore().getConfiguration().skinsList.size() == 0){
                                    sender.sendMessage(ColorUtil.fixColor(Lang.INFO_SKIN_CLEAR));
                                    return false;
                                }

                                Random random = new Random();

                                int rand = random.nextInt(getCore().getConfiguration().skinsList.size());

                                String nick = getCore().getConfiguration().skinsList.get(rand);

                                Skin skin = MojangApiUtil.getPremiumSkin(nick, getCore());
                                if(skin != null) {
                                    skin.setDirty(true);
                                    getCore().getSkinsStorage().addSkin(player.getUniqueId(), skin);
                                    skin.applySkinForPlayers(player);
                                    skin.updateSkinForPlayer(player);
                                }

                                sender.sendMessage(ColorUtil.fixColor(Lang.INFO_SKIN_CLEAR));

                            } else {

                                Skin skin = MojangApiUtil.getPremiumSkin("Steve", getCore());
                                if(skin != null) {
                                    skin.setDirty(true);
                                    getCore().getSkinsStorage().addSkin(player.getUniqueId(), skin);
                                    skin.applySkinForPlayers(player);
                                    skin.updateSkinForPlayer(player);
                                }

                                sender.sendMessage(ColorUtil.fixColor(Lang.INFO_SKIN_CLEAR));
                            }
                        } else {

                            Skin skin = MojangApiUtil.getPremiumSkin(player.getDisplayName(), getCore());
                            if(skin != null) {
                                skin.setDirty(true);
                                getCore().getSkinsStorage().addSkin(player.getUniqueId(), skin);
                                skin.applySkinForPlayers(player);
                                skin.updateSkinForPlayer(player);
                            }

                            sender.sendMessage(ColorUtil.fixColor(Lang.INFO_SKIN_CLEAR));

                        }

                    } else {
                        sender.sendMessage(getUsage());
                    }

                } else if(args[0].equalsIgnoreCase("set")){

                    if(args.length == 2) {

                        String skin_string = args[1];

                        Skin skin = MojangApiUtil.getPremiumSkin(skin_string, getCore());

                        if(skin != null) {

                            skin.setDirty(true);
                            skin.setLastUpdate(LocalDateTime.now().plusYears(1));
                            getCore().getSkinsStorage().addSkin(player.getUniqueId(), skin);
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
}
