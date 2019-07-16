package com.paulek.core.commands.cmds.user;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import org.bukkit.command.CommandSender;

public class SkinCMD extends Command {

    public SkinCMD(Core core) {
        super("skin", "set yours skin", "/skin {set, clear} {nick}", "core.cmd.skin", new String[]{}, core);
    }

    //TODO to fix
    @Override
    public boolean execute(CommandSender sender, String[] args) {

//        if(sender instanceof Player){
//
//            Player player = (Player)sender;
//
//            if(args.length >= 1){
//
//                if(args[0].equalsIgnoreCase("clear")){
//
//                    if(args.length == 1) {
//
//                        if(!Util.testPremium(player.getDisplayName())) {
//
//                            if (Config.SKINS_HIDENONPREMIUM) {
//
//                                if(Config.SKINS_SKINSFORNONPREMIUM.size() == 0){
//                                    sender.sendMessage(ColorUtil.fixColor(Lang.INFO_SKIN_CLEAR));
//                                    return false;
//                                }
//
//                                Random random = new Random();
//
//                                int rand = random.nextInt(Config.SKINS_SKINSFORNONPREMIUM.size());
//
//                                String nick = Config.SKINS_SKINSFORNONPREMIUM.get(rand);
//
//                                Skin skin = new Skin(nick, null);
//
//                                Users.getUser(player.getUniqueId()).setSkinsetManually(false);
//                                Users.getUser(player.getUniqueId()).setSkin("");
//                                Users.getUser(player.getUniqueId()).setSignature("");
//                                SkinUtil.applySkin(player, skin);
//
//                                sender.sendMessage(ColorUtil.fixColor(Lang.INFO_SKIN_CLEAR));
//
//                            } else {
//
//                                Skin skin = new Skin("Steve");
//
//                                Users.getUser(player.getUniqueId()).setSkinsetManually(false);
//                                Users.getUser(player.getUniqueId()).setSkin(" ");
//                                Users.getUser(player.getUniqueId()).setSignature(" ");
//                                SkinUtil.applySkin(player, skin);
//
//                                sender.sendMessage(ColorUtil.fixColor(Lang.INFO_SKIN_CLEAR));
//                            }
//                        } else {
//
//                            Skin skin = new Skin(player.getDisplayName());
//                            Users.getUser(player.getUniqueId()).setSkinsetManually(false);
//                            Users.getUser(player.getUniqueId()).setSkin(" ");
//                            Users.getUser(player.getUniqueId()).setSignature(" ");
//                            Users.getUser(player.getUniqueId()).setSkinSenility(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 2);
//                            SkinUtil.applySkin(player, skin);
//
//                            sender.sendMessage(ColorUtil.fixColor(Lang.INFO_SKIN_CLEAR));
//
//                        }
//
//                    } else {
//                        sender.sendMessage(getUsage());
//                    }
//
//                } else if(args[0].equalsIgnoreCase("set")){
//
//                    if(args.length == 2) {
//
//                        String skin_string = args[1];
//
//                        if(Util.testPremium(skin_string)) {
//
//                            boolean safe = true;
//                            Skin skin = null;
//
//                            try {
//
//                                skin = new Skin(skin_string, null);
//
//                            } catch (Exception e){
//                                safe = false;
//                                sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_SKIN_ERROR));
//                            }
//                            if(safe) {
//                                SkinUtil.applySkin(player, skin);
//                                Users.getUser(player.getUniqueId()).setSkinsetManually(true);
//                                Users.getUser(player.getUniqueId()).setSkin(skin.getValue());
//                                Users.getUser(player.getUniqueId()).setSignature(skin.getSignature());
//
//                                sender.sendMessage(ColorUtil.fixColor(Lang.INFO_SKIN_CHANGED));
//                            }
//                        } else {
//                            sender.sendMessage(ColorUtil.fixColor(Lang.ERROR_SKIN_NOTPREMIUM));
//                        }
//
//                    } else {
//                        sender.sendMessage(getUsage());
//                    }
//
//                } else {
//                    sender.sendMessage(getUsage());
//                }
//
//            } else {
//                sender.sendMessage(getUsage());
//            }
//
//        } else {
//            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
//        }

        return false;
    }
}
