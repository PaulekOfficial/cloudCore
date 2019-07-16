package com.paulek.core.commands.cmds.admin;

import com.paulek.core.Core;
import com.paulek.core.commands.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public class VanishCMD extends Command {

    private static HashMap<UUID, BukkitTask> list = new HashMap<UUID, BukkitTask>();

    public VanishCMD(Core core) {
        super("vanish", "vanish yourself", "/v {Player}", "core.cmd.vanish", new String[]{"v"}, core);
    }

    public static HashMap<UUID, BukkitTask> getList() {
        return list;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        //TODO to fix

//        if(sender instanceof Player) {
//            final Player p = (Player) sender;
//            UUID uuid = p.getUniqueId();
//            if (args.length == 0) {
//                if(!list.containsKey(uuid)) {
//                    BukkitTask id;
//                    id = Bukkit.getScheduler().runTaskTimer(Core.getPlugin(), new Runnable() {
//                        public void run() {
//                            for(Player player : Bukkit.getOnlinePlayers()){
//                                player.hidePlayer(p);
//                                Util.sendActionbar(p, ColorUtil.fixColor(Lang.INFO_VANISH_ACTIONBAR));
//                            }
//                        }
//                    },20, 20);
//                    list.put(uuid, id);
//                    Users.getUser(uuid).setVanish(true);
//                    p.getLocation().getWorld().spawnParticle(Particle.SMOKE_LARGE, p.getLocation(), 500, 20, 20 , 20);
//                    p.sendMessage(ColorUtil.fixColor(Lang.INFO_VANISH_VANISHED));
//                } else {
//                    p.getLocation().getWorld().spawnParticle(Particle.SMOKE_LARGE, p.getLocation(), 500, 20, 20 , 20);
//                    list.get(uuid).cancel();
//                    list.remove(uuid);
//                    Users.getUser(uuid).setVanish(false);
//                    for (Player player : Bukkit.getOnlinePlayers()) {
//                        player.showPlayer(p);
//                    }
//                    p.sendMessage(ColorUtil.fixColor(Lang.INFO_VANISH_VISIBLE));
//                }
//            } else if(args[0] != null){
//                String nick = args[0];
//                if(Bukkit.getPlayer(nick) != null) {
//                    Player player = Bukkit.getPlayer(nick);
//                    if(!list.containsKey(uuid)) {
//                        BukkitTask id;
//                        id = Bukkit.getScheduler().runTaskTimer(Core.getPlugin(), new Runnable() {
//                            public void run() {
//                                for(Player player : Bukkit.getOnlinePlayers()){
//                                    player.hidePlayer(p);
//                                    Util.sendActionbar(p, ColorUtil.fixColor(Lang.INFO_VANISH_ACTIONBAR));
//                                }
//                            }
//                        },20, 20);
//                        list.put(uuid, id);
//                        Users.getUser(uuid).setVanish(true);
//                        player.sendMessage(ColorUtil.fixColor(Lang.INFO_VANISH_VANISHED));
//                        p.getLocation().getWorld().spawnParticle(Particle.SMOKE_LARGE, p.getLocation(), 500, 20, 20 , 20);
//                    } else {
//                        list.get(uuid).cancel();
//                        list.remove(uuid);
//                        Users.getUser(uuid).setVanish(false);
//                        for (Player d : Bukkit.getOnlinePlayers()) {
//                            d.showPlayer(player);
//                        }
//                        player.sendMessage(ColorUtil.fixColor(Lang.INFO_VANISH_VISIBLE));
//                        p.getLocation().getWorld().spawnParticle(Particle.SMOKE_LARGE, p.getLocation(), 500, 20, 20 , 20);
//                    }
//                } else {
//                    p.sendMessage(ColorUtil.fixColor(Lang.ERROR_VANISH_PLAYEROFFINLE));
//                }
//            } else {
//                sender.sendMessage(getUsage());
//                return false;
//            }
//        } else {
//            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
//            return false;
//        }
        return false;
    }
}
