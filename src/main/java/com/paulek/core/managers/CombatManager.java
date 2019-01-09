package com.paulek.core.managers;

import com.paulek.core.data.CombatStorage;
import com.paulek.core.data.configs.Config;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.data.objects.Warrior;
import com.paulek.core.utils.Util;
import org.bukkit.Bukkit;

import java.util.Iterator;

public class CombatManager implements Runnable {

    private long timetoend = Config.SETTINGS_COMBAT_TIME;
    private String message = Util.fixColor(Lang.INFO_COMBAT_ACTIONBAR);
    private String end_combat = Util.fixColor(Lang.INFO_COMBAT_ENDEDACTION);

    public void run() {

        if(CombatStorage.getMarked() == null) return;

        Iterator<Warrior> i = CombatStorage.getMarked().values().iterator();

        while(i.hasNext()){

            Warrior p = i.next();

            long time = (java.lang.System.currentTimeMillis() / 1000L) - (p.getCurenttimemilirs() / 1000L);

            long coldown = timetoend - time;

            String a = message.replace("{coldown}", Long.toString(coldown));

            if((Bukkit.getPlayer(p.getNick()) != null)) Util.sendActionbar(Bukkit.getPlayer(p.getNick()), a);

            if(coldown <= 0){
                i.remove();

                if((Bukkit.getPlayer(p.getNick()) != null)) Util.sendActionbar(Bukkit.getPlayer(p.getNick()), end_combat);

                if((Bukkit.getPlayer(p.getNick()) != null)) if(Config.SETTINGS_COMBAT_CHATMESSAGE) Bukkit.getPlayer(p.getNick()).sendMessage(Util.fixColor(Lang.INFO_COMBAT_ENDCHAT));

            }

        }


    }
}
