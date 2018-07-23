package com.paulek.core.listeners.player;

import com.paulek.core.data.configs.Config;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.data.CombatStorage;
import com.paulek.core.utils.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerCommandPreprocessEvent implements Listener {

    @EventHandler
    public void onCommand(org.bukkit.event.player.PlayerCommandPreprocessEvent e) {

        if (Config.SETTINGS_COMBAT_DISABLECOMMAND) {

            if(CombatStorage.isMarked(e.getPlayer().getUniqueId())) {
                boolean found = false;

                for (String s : Config.SETTINGS_COMBAT_IGNORED_COMMANDS) {
                    if (e.getMessage().contains(s)) {
                        found = true;
                    }
                }

                if (!found) {
                    e.getPlayer().sendMessage(Util.fixColor(Lang.ERROR_COMBAT_COMMANDDISABLED));
                    e.setCancelled(true);
                }
            }

        }
    }

}
