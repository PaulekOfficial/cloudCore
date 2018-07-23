package com.paulek.core.commands.cmds.admin;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import com.paulek.core.data.UserStorage;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.utils.Util;
import net.minecraft.server.v1_12_R1.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public class VanishCMD extends Command {

    private static HashMap<UUID, BukkitTask> list = new HashMap<UUID, BukkitTask>();

    public VanishCMD() {
        super("vanish", "vanish yourself", "/v {Player}", "core.vanish", new String[] {"v"});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        //TODO Więcej funkcji

        if(sender instanceof Player) {
            final Player p = (Player) sender;
            UUID uuid = p.getUniqueId();
            if (args.length == 0) {
                if(!list.containsKey(uuid)) {
                    BukkitTask id;
                    id = Bukkit.getScheduler().runTaskTimer(Core.getPlugin(), new Runnable() {
                        public void run() {
                            for(Player player : Bukkit.getOnlinePlayers()){
                                player.hidePlayer(p);
                                Util.sendActionbar(p, Util.fixColor(Lang.INFO_VANISH_ACTIONBAR));
                            }
                        }
                    },20, 20);
                    list.put(uuid, id);
                    UserStorage.getUser(uuid).setVanish(true);
                    Util.giveParticle(p, EnumParticle.SMOKE_LARGE, p.getLocation(), 5, 5, 5, 1, 50);
                    p.sendMessage(Util.fixColor(Lang.INFO_VANISH_VANISHED));
                } else {
                    Util.giveParticle(p, EnumParticle.SMOKE_LARGE, p.getLocation(), 5, 5, 5, 1, 50);
                    list.get(uuid).cancel();
                    list.remove(uuid);
                    UserStorage.getUser(uuid).setVanish(false);
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.showPlayer(p);
                    }
                    p.sendMessage(Util.fixColor(Lang.INFO_VANISH_VISIBLE));
                }
            } else if(args[0] != null){
                String nick = args[0];
                if(Bukkit.getPlayer(nick) != null) {
                    Player player = Bukkit.getPlayer(nick);
                    if(!list.containsKey(uuid)) {
                        BukkitTask id;
                        id = Bukkit.getScheduler().runTaskTimer(Core.getPlugin(), new Runnable() {
                            public void run() {
                                for(Player player : Bukkit.getOnlinePlayers()){
                                    player.hidePlayer(p);
                                    Util.sendActionbar(p, Util.fixColor(Lang.INFO_VANISH_ACTIONBAR));
                                }
                            }
                        },20, 20);
                        list.put(uuid, id);
                        UserStorage.getUser(uuid).setVanish(true);
                        player.sendMessage(Util.fixColor(Lang.INFO_VANISH_VANISHED));
                        Util.giveParticle(player, EnumParticle.SMOKE_LARGE, p.getLocation(), 5, 5, 5, 1, 50);
                    } else {
                        list.get(uuid).cancel();
                        list.remove(uuid);
                        UserStorage.getUser(uuid).setVanish(false);
                        for (Player d : Bukkit.getOnlinePlayers()) {
                            d.showPlayer(player);
                        }
                        player.sendMessage(Util.fixColor(Lang.INFO_VANISH_VISIBLE));
                        Util.giveParticle(player, EnumParticle.SMOKE_LARGE, p.getLocation(), 5, 5, 5, 1, 50);
                    }
                } else {
                    p.sendMessage(Util.fixColor(Lang.ERROR_VANISH_PLAYEROFFINLE));
                }
            } else {
                sender.sendMessage(getUsage());
                return false;
            }
        } else {
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
            return false;
        }
        return false;
    }

    public static HashMap<UUID, BukkitTask> getList() {
        return list;
    }
}
