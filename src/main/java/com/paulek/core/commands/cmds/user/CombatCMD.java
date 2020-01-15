package com.paulek.core.commands.cmds.user;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.io.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class CombatCMD extends Command {

    private long timetoend;
    private String yes;
    private String no;

    public CombatCMD(Core core) {
        super("combat", "checks you combat status", "/combat", "core.cmd.combat", new String[]{"walka", "bicie"}, core);
        timetoend = core.getConfiguration().combatTime;
        yes = Lang.INFO_COMBAT_YES;
        no = Lang.INFO_COMBAT_NO;
    }

    //TODO Dodać opcję teleportacji gracza po stringu
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            LocalDateTime time = getCore().getCombatsStorage().get(player.getUniqueId());
            if (time != null) {
                long coldDown = getCore().getConfiguration().combatTime - time.until(LocalDateTime.now(), ChronoUnit.SECONDS);

                if (coldDown > 0) {

                    String str = ColorUtil.fixColor(Lang.INFO_COMBAT_COMMAND.replace("{time}", "$c" + coldDown + "/" + timetoend).replace("{check}", "$c" + no));

                    player.sendMessage(str);

                    return false;
                }
                return false;
            }

            String str = ColorUtil.fixColor(Lang.INFO_COMBAT_COMMAND.replace("{time}", "$a0/" + timetoend).replace("{check}", "$a" + yes));

            player.sendMessage(str);
        } else {
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
            return false;
        }
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }

}
