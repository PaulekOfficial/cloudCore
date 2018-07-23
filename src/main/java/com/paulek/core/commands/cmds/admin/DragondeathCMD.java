package com.paulek.core.commands.cmds.admin;

import com.paulek.core.commands.Command;
import com.paulek.core.data.configs.Lang;
import com.paulek.core.utils.Util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;

public class DragondeathCMD extends Command {

    public DragondeathCMD(){
        super("dragondeath", "play dragon death", "/dragondeaath", "core.dragondeath", new String[]{});
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(sender instanceof Player) {

            Player player = (Player) sender;

            EnderDragon enderDragon = player.getWorld().spawn(player.getLocation(), EnderDragon.class);
            enderDragon.setPhase(EnderDragon.Phase.DYING);

            sender.sendMessage(Util.fixColor(Lang.INFO_EFFECT_DRAGONDYE));

        } else {
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
        }

        return false;
    }
}
