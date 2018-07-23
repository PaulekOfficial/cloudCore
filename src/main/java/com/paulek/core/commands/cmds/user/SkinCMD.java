package com.paulek.core.commands.cmds.user;

import com.paulek.core.commands.Command;
import com.paulek.core.data.UserStorage;
import com.paulek.core.data.configs.Config;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.data.objects.Skin;
import com.paulek.core.utils.SkinUtil;
import com.paulek.core.utils.Util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class SkinCMD extends Command {

    public SkinCMD(){
        super("skin", "set yours skin", "/skin {set, clear} {nick}", "core.skin", new String[]{});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(sender instanceof Player){

            Player player = (Player)sender;

            if(args.length >= 1){

                if(args[0].equalsIgnoreCase("clear")){

                    if(args.length == 1) {

                        if(!Util.testPremium(player.getDisplayName())) {

                            if (Config.SETTINGS_SKINS_ENABLENOPREMIUMRANDOMSKIN) {

                                if(Config.SETTINGS_NONPREMIUMSKINS.size() == 0){
                                    sender.sendMessage(Util.fixColor(Lang.INFO_SKIN_CLEAR));
                                    return false;
                                }

                                Random random = new Random();

                                int rand = random.nextInt(Config.SETTINGS_NONPREMIUMSKINS.size());

                                String nick = Config.SETTINGS_NONPREMIUMSKINS.get(rand);

                                Skin skin = new Skin(nick, null);

                                UserStorage.getUser(player.getUniqueId()).setSkinset_manually(false);
                                UserStorage.getUser(player.getUniqueId()).setSkin("");
                                UserStorage.getUser(player.getUniqueId()).setSignature("");
                                SkinUtil.applySkin(player, skin);

                                sender.sendMessage(Util.fixColor(Lang.INFO_SKIN_CLEAR));

                            } else {
                                Skin skin = new Skin("Steve");

                                UserStorage.getUser(player.getUniqueId()).setSkinset_manually(false);
                                UserStorage.getUser(player.getUniqueId()).setSkin(" ");
                                UserStorage.getUser(player.getUniqueId()).setSignature(" ");
                                SkinUtil.applySkin(player, skin);

                                sender.sendMessage(Util.fixColor(Lang.INFO_SKIN_CLEAR));
                            }
                        } else {

                            Skin skin = new Skin(player.getDisplayName());
                            UserStorage.getUser(player.getUniqueId()).setSkinset_manually(false);
                            UserStorage.getUser(player.getUniqueId()).setSkin(" ");
                            UserStorage.getUser(player.getUniqueId()).setSignature(" ");
                            UserStorage.getUser(player.getUniqueId()).setSkin_senility(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 2);
                            SkinUtil.applySkin(player, skin);

                            sender.sendMessage(Util.fixColor(Lang.INFO_SKIN_CLEAR));

                        }

                    } else {
                        sender.sendMessage(getUsage());
                    }

                } else if(args[0].equalsIgnoreCase("set")){

                    if(args.length == 2) {

                        String skin_string = args[1];

                        if(Util.testPremium(skin_string)) {

                            boolean safe = true;
                            Skin skin = null;

                            try {

                                skin = new Skin(skin_string, null);

                            } catch (Exception e){
                                safe = false;
                                sender.sendMessage(Util.fixColor(Lang.ERROR_SKIN_ERROR));
                            }
                            if(safe) {
                                SkinUtil.applySkin(player, skin);
                                UserStorage.getUser(player.getUniqueId()).setSkinset_manually(true);
                                UserStorage.getUser(player.getUniqueId()).setSkin(skin.getValue());
                                UserStorage.getUser(player.getUniqueId()).setSignature(skin.getSignature());

                                sender.sendMessage(Util.fixColor(Lang.INFO_SKIN_CHANGED));
                            }
                        } else {
                            sender.sendMessage(Util.fixColor(Lang.ERROR_SKIN_NOTPREMIUM));
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
