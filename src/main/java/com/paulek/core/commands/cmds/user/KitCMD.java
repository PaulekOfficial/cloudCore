package com.paulek.core.commands.cmds.user;

import com.paulek.core.Core;
import com.paulek.core.basic.Kit;
import com.paulek.core.basic.Timestamp;
import com.paulek.core.basic.User;
import com.paulek.core.basic.gui.GUIItem;
import com.paulek.core.basic.gui.GUIWindow;
import com.paulek.core.commands.Command;
import com.paulek.core.common.Util;
import com.paulek.core.common.io.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class KitCMD extends Command {

    public KitCMD(Core core) {
        super("kit", "Get a kit", "/kit (name)", "core.cmd.kit", new String[]{}, core);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if (args.length == 2) {

            if (!sender.hasPermission("core.cmd.kit.admin")) {
                setPermissionMessage("core.cmd.kit.admin");
                return false;
            }

            String name = args[0];

            if (getCore().getKits().getKits().get(name) == null) {
                sender.sendMessage(Util.fixColor(Lang.ERROR_KIT_NOKIT));
                return false;
            }

            if (Bukkit.getPlayer(args[1]) != null) {
                Player player2 = Bukkit.getPlayer(args[1]);

                Kit kit = getCore().getKits().getKits().get(name);

                GUIWindow gui = getKitWindow(kit, player2);

                gui.openInventory(player2);

                player2.sendMessage(Util.fixColor(Lang.INFO_KIT_GRANTED.replace("{kit}", name)));

                return false;

            }
            sender.sendMessage(Util.fixColor(Lang.ERROR_KIT_NOPLAYER));

            return false;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(Lang.ERROR_MUSTBEPLAYER);
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 1) {

            String kitName = args[0];

            if (getCore().getKits().getKits().get(kitName) != null) {

                User user = getCore().getUsersStorage().getUser(player.getUniqueId());

                if (user.getTimeStamp(kitName) != null) {

                    Timestamp timestamp = user.getTimeStamp(kitName);
                    if (timestamp.getClassName().equalsIgnoreCase("kit")) {
                        if (timestamp.applicable()) {
                            sender.sendMessage(Util.fixColor(Lang.ERROR_KIT_WAIT.replace("{time}", timestamp.timeLeft())));
                            return false;
                        } else {
                            user.removeTimestamp(kitName);
                        }

                    }

                }

                Kit kit = getCore().getKits().getKits().get(kitName);

                if (!player.hasPermission(kit.getPermission())) {
                    sender.sendMessage(Util.fixColor(Lang.KIT_NOACCES));
                    return false;
                }

                GUIWindow gui = getKitWindow(kit, player);

                gui.openInventory(player);

                player.sendMessage(Util.fixColor(Lang.INFO_KIT_SUCCES.replace("{kit}", kitName)));

                if (!player.hasPermission("core.cmd.kit.nocooldown"))
                    user.addTimestamp(kitName, new Timestamp("kit", System.currentTimeMillis() + (kit.getCooldown() * 1000L)));

                return false;
            }
            player.sendMessage(Util.fixColor(Lang.ERROR_KIT_NOKIT));

            return false;
        }

        Bukkit.getScheduler().runTaskAsynchronously(getCore().getPlugin(), new Runnable() {
            @Override
            public void run() {

                User user = getCore().getUsersStorage().getUser(player.getUniqueId());

                GUIWindow gui = new GUIWindow(Util.fixColor(Lang.INFO_KIT_GUINAME), getVaildRows(getCore().getKits().getKits().size()));

                int slot = 0;
                for (String kitName : getCore().getKits().getKits().keySet()) {

                    Kit kit = getCore().getKits().getKits().get(kitName);

                    if (kit.isShowInGui() && sender.hasPermission(kit.getPermission())) {

                        ItemStack itemStack = kit.getGuiItem();
                        ItemMeta itemMeta = itemStack.getItemMeta();

                        List<String> lore = new ArrayList<>();

                        Timestamp timestamp = null;
                        if (user.getTimeStamp(kitName) != null) {
                            timestamp = user.getTimeStamp(kitName);
                        }

                        String availability = Lang.INFO_KIT_YES;
                        String timeLeft = "";

                        if (timestamp != null) {
                            if (timestamp.applicable()) availability = Lang.INFO_KIT_NO;
                            timeLeft = timestamp.timeLeft();
                        }

                        for (String s : Lang.INFO_KIT_LORE) {

                            s = s.replace("{availability}", availability);
                            s = s.replace("{availablein}", timeLeft);
                            s = s.replace("{description}", kit.getDescription());

                            lore.add(s);

                        }

                        itemMeta.setLore(Util.fixColors(lore));
                        itemStack.setItemMeta(itemMeta);

                        final String finalTimeLeft = timeLeft;

                        GUIItem item = new GUIItem(itemStack, event -> {

                            GUIWindow kitWindow = getKitWindow(kit, player);

                            kitWindow.openInventory(player);

                            player.sendMessage(Util.fixColor(Lang.INFO_KIT_SUCCES.replace("{kit}", kit.getName())));

                            if (!player.hasPermission("core.kit.nocooldown"))
                                user.addTimestamp(kitName, new Timestamp("kit", System.currentTimeMillis() + (kit.getCooldown() * 1000L)));

                        });
                        if (timestamp != null) {
                            if (timestamp.applicable()) {
                                item = new GUIItem(itemStack, event -> {
                                    player.sendMessage(Util.fixColor(Lang.ERROR_KIT_WAIT.replace("{time}", finalTimeLeft)));
                                });
                            }
                        }
                        gui.setItem(slot, item);

                        slot++;
                    }
                }

                gui.setInventoryCloseEvent(event -> {
                    gui.unregister();
                });

                gui.register();

                gui.openInventory(player);
            }
        });

        return false;
    }

    private GUIWindow getKitWindow(Kit kit, Player player) {

        GUIWindow gui = new GUIWindow(Util.fixColor(kit.getName()), getVaildRows(kit.getItems().size()));
        gui.setCancellOpen(false);
        gui.setCancellInteract(false);

        int kitSlot = 0;
        for (ItemStack itemStack : kit.getItems()) {

            if (itemStack != null && !itemStack.getType().equals(Material.AIR)) {

                GUIItem kitItem = new GUIItem(itemStack, null);
                gui.setItem(kitSlot, kitItem);

                kitSlot++;
            }
        }

        gui.setInventoryCloseEvent(closeEvent -> {
            if (closeEvent.getInventory().getSize() > 0) {
                for (ItemStack is : closeEvent.getInventory().getContents()) {
                    if (is != null)
                        closeEvent.getPlayer().getLocation().getWorld().dropItemNaturally(closeEvent.getPlayer().getLocation(), is);
                }
            }
            gui.unregister();
        });

        gui.register();

        return gui;
    }

    private int getVaildRows(int slots) {

        if (slots >= 46) return 6;
        if (slots >= 39) return 5;
        if (slots >= 28) return 4;
        if (slots >= 19) return 3;
        if (slots >= 10) return 2;

        return 1;
    }
}
