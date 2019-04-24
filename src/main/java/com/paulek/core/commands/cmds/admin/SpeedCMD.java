package com.paulek.core.commands.cmds.admin;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.common.Util;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpeedCMD extends Command {

    public SpeedCMD(Core core) {
        super("speed", "change yours speed", "/speed {player} {speed}", "core.cmd.speed", new String[]{}, core);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if (args.length >= 1) {

            if (args.length == 1) {

                if (sender instanceof Player) {

                    Player player = (Player) sender;

                    double a = 0;

                    try {

                        a = Double.valueOf(args[0]);

                    } catch (Exception e) {

                        sender.sendMessage(Util.fixColor(Lang.ERROR_SPEED_NOTAVALUE));

                        return false;
                    }

                    if (setPlayerSpeed(player, a, sender)) return false;

                } else {
                    sender.sendMessage(getUsage());
                }

            } else if (args.length == 2) {

                String p = args[0];

                if (Bukkit.getPlayer(p) != null) {

                    Player player = Bukkit.getPlayer(p);

                    double a = 0;

                    try {

                        a = Double.valueOf(args[1]);

                    } catch (Exception e) {

                        sender.sendMessage(Util.fixColor(Lang.ERROR_SPEED_NOTAVALUE));

                        return false;
                    }

                    if (setPlayerSpeed(player, a, sender)) return false;

                    sender.sendMessage(Util.fixColor(Lang.INFO_SPEED_PLAYERCHANGED));

                } else {
                    sender.sendMessage(Util.fixColor(Lang.ERROR_SPEED_NOPLAYER));
                }

            } else {
                sender.sendMessage(getUsage());
            }

        }

        return false;
    }

    private boolean setPlayerSpeed(Player player, double a, CommandSender sender) {
        if (a > 10) {

            sender.sendMessage(Util.fixColor(Lang.ERROR_SPEED_VALUE));

            return true;
        }

        a = a * 0.1;

        if (player.isFlying()) {
            player.setFlySpeed((float) a);
            sender.sendMessage(Util.fixColor(Lang.INFO_SPEED_CHANGED));
        } else {
            player.setWalkSpeed((float) a);
            sender.sendMessage(Util.fixColor(Lang.INFO_SPEED_CHANGED));
        }
        return false;
    }
}
