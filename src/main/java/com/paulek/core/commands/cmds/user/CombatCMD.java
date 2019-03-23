package com.paulek.core.commands.cmds.user;

import com.paulek.core.basic.Warrior;
import com.paulek.core.basic.data.CombatStorage;
import com.paulek.core.commands.Command;
import com.paulek.core.common.Util;
import com.paulek.core.common.io.Config;
import com.paulek.core.common.io.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CombatCMD extends Command {

    private long timetoend = Config.COMBAT_TIME;
    private String yes = Lang.INFO_COMBAT_YES;
    private String no = Lang.INFO_COMBAT_NO;

    public CombatCMD(){
        super("combat", "checks you combat status", "/combat", "core.cmd.combat", new String[]{"walka", "bicie"});
    }

    //TODO Dodać opcję teleportacji gracza po stringu
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if (CombatStorage.isMarked(player.getUniqueId())) {

                for (Warrior po : CombatStorage.getMarked().values()) {

                    if (po.getUuid().equals(player.getUniqueId())) {
                        long time = (java.lang.System.currentTimeMillis() / 1000L) - (po.getCurenttimemilirs() / 1000L);

                        long coldown = timetoend - time;

                        if (coldown > 0) {

                            String str = Util.fixColor(Lang.INFO_COMBAT_COMMAND.replace("{time}", "$c" + coldown + "/" + timetoend).replace("{check}", "$c" + no));

                            player.sendMessage(str);

                            return false;
                        }

                    }

                }

                return false;
            }

            String str = Util.fixColor(Lang.INFO_COMBAT_COMMAND.replace("{time}", "$a0/" + timetoend).replace("{check}", "$a" + yes));

            player.sendMessage(str);
        } else {
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
            return false;
        }
        return false;
    }
}
