package com.paulek.core.basic;

import com.paulek.core.Core;
import com.paulek.core.basic.event.PlayerCombatEndEvent;
import com.paulek.core.common.ActionBarUtil;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.io.Config;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Iterator;
import java.util.Objects;

public class CombatManager implements Runnable {

    private long timetoend = Config.COMBAT_TIME;
    private String message = ColorUtil.fixColor(Lang.INFO_COMBAT_ACTIONBAR);
    private String end_combat = ColorUtil.fixColor(Lang.INFO_COMBAT_ENDEDACTION);

    private Core core;

    public CombatManager(Core core) {
        this.core = Objects.requireNonNull(core, "Core");
    }

    public void run() {

        if (core.getCombatStorage().getMarked() == null) return;

        Iterator<Warrior> i = core.getCombatStorage().getMarked().values().iterator();

        while (i.hasNext()) {

            Warrior p = i.next();

            long time = (java.lang.System.currentTimeMillis() / 1000L) - (p.getCurenttimemilirs() / 1000L);

            long coldown = timetoend - time;

            String a = message.replace("{coldown}", Long.toString(coldown));

            if ((Bukkit.getPlayer(p.getNick()) != null)){
                Player player = Bukkit.getPlayer(p.getNick());
                ActionBarUtil.sendActionbar(player, a);
            }

            if (coldown <= 0) {
                i.remove();

                if ((Bukkit.getPlayer(p.getNick()) != null)){
                    Player player = Bukkit.getPlayer(p.getNick());
                    ActionBarUtil.sendActionbar(player, end_combat);
                    if (Config.COMBAT_CHATMESSAGE) player.sendMessage(ColorUtil.fixColor(Lang.INFO_COMBAT_ENDCHAT));
                    Bukkit.getPluginManager().callEvent(new PlayerCombatEndEvent(player));
                }

            }

        }


    }
}
