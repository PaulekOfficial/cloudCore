package com.paulek.core.commands.cmds.admin;

import com.paulek.core.commands.Command;
import com.paulek.core.common.Util;
import com.paulek.core.common.io.Config;
import com.paulek.core.common.io.Lang;
import org.bukkit.command.CommandSender;

public class WhitelistCMD extends Command {

    public WhitelistCMD(){
        super("whitelist", "menage your whitelist", "/whitelist {set/add/remove/motd} {on/off|player|motd}", "core.cmd.whitelist", new String[]{});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(args.length >= 1) {

            if (args[0].equalsIgnoreCase("motd")) {

                if (args.length == 1) {

                    sender.sendMessage(Util.fixColor(Lang.INFO_WHITELIST_MOTD.replace("{motd}", Config.WHITELIST_MOD)));

                } else {

                    StringBuilder stringBuilder = new StringBuilder();

                    for (int i = 1; i == args.length; i++) {

                        stringBuilder.append(args[i]);
                        stringBuilder.append(" ");

                    }

                    Config.WHITELIST_MOD = stringBuilder.toString();
                    Config.saveConfig();
                    Config.reloadConfig();

                    sender.sendMessage(Util.fixColor(Lang.INFO_WHITELIST_SETMOTD.replace("{motd}", stringBuilder.toString())));

                }

            } else if (args[0].equalsIgnoreCase("set")) {

                if (args.length == 2) {

                    if (args[1].equalsIgnoreCase("on")) {

                        if (!Config.WHITELIST_ENABLE) {

                            Config.WHITELIST_ENABLE = true;
                            Config.saveConfig();
                            Config.reloadConfig();

                            sender.sendMessage(Util.fixColor(Lang.INFO_WHITELIST_ON));

                        } else {

                            sender.sendMessage(Util.fixColor(Lang.ERROR_WHITELIST_ON));

                            return false;
                        }

                    } else if (args[1].equalsIgnoreCase("off")) {

                        if (Config.WHITELIST_ENABLE) {

                            Config.WHITELIST_ENABLE = false;
                            Config.saveConfig();
                            Config.reloadConfig();

                            sender.sendMessage(Util.fixColor(Lang.INFO_WHITELIST_OFF));

                        } else {

                            sender.sendMessage(Util.fixColor(Lang.ERROR_WHITELIST_OFF));

                            return false;
                        }

                    } else {

                        sender.sendMessage(getUsage());

                        return false;
                    }

                } else {
                    sender.sendMessage(getUsage());
                    return false;
                }


            } else if (args[0].equalsIgnoreCase("add")) {

                if (args.length == 2) {

                    String nick = args[1];

                    if (!Config.WHITELIST_ALLOWEDPLAYERS.contains(nick)) {

                        Config.WHITELIST_ALLOWEDPLAYERS.add(nick);
                        Config.saveConfig();
                        Config.reloadConfig();

                        sender.sendMessage(Util.fixColor(Lang.INFO_WHITELIST_ADD));

                    } else {
                        sender.sendMessage(Util.fixColor(Lang.ERROR_WHITELIST_CONTAINS));
                        return false;
                    }

                } else {
                    sender.sendMessage(getUsage());
                    return false;
                }

            } else if(args[0].equalsIgnoreCase("remove")){



            }else {

                sender.sendMessage(getUsage());

                return false;
            }

        } else {
            sender.sendMessage(getUsage());
        }

        return false;
    }
}
