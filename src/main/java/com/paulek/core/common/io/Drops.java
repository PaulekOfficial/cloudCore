package com.paulek.core.common.io;

import com.paulek.core.Core;
import com.paulek.core.basic.drop.DropMask;
import com.paulek.core.basic.drop.StoneDrop;
import com.paulek.core.basic.drop.mask.BlockMask;
import com.paulek.core.common.ColorUtil;
import com.paulek.core.common.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.*;

public class Drops {

    private Map<String, DropMask> blockDropMaskHashMap;
    private List<StoneDrop> drops;
    private Core core;

    private File file;
    private YamlConfiguration configuration;

    private byte turboMode;

    private boolean titledMessageForTurbo;

    private int titleInterval;
    private BukkitTask titleTask;
    private double removeFromBarValue;
    private double turboInterval;

    public Drops(Core core){
        this.core = Objects.requireNonNull(core, "Core");
        file = new File(core.getPlugin().getDataFolder(), "drops.yml");
        create();
        setup();
    }

    public DropMask getMask(String name) {
        return blockDropMaskHashMap.get(name);
    }

    public void addDropMask(String material, DropMask dropMask){
        blockDropMaskHashMap.put(material, dropMask);
    }

    public List<StoneDrop> getDrops() {
        return drops;
    }

    public void initTurbo(long time, String message, String orginalTime){

        if(titledMessageForTurbo){
            Bukkit.getScheduler().runTaskAsynchronously(core.getPlugin(), new Runnable() {
                @Override
                public void run() {

                    String[] turboMessages = Lang.DROP_TURBO_TITLE.split(" ");

                    String turboMessage = "";

                    for(int i = 0; i < turboMessages.length; i++){

                        turboMessage += turboMessages[i] + " ";

                        for(Player player : Bukkit.getOnlinePlayers()){
                            Util.sendTitle(player, ColorUtil.fixColor(turboMessage), ColorUtil.fixColor(Lang.DROP_TURBO_SUBTITLE.replace("{message}", message)), 0, 20*5, 40);
                        }

                        try{
                            Thread.sleep(1000 * titleInterval);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                }
            });
        } else {
            Bukkit.broadcastMessage(ColorUtil.fixColor(Lang.DROP_TURBO_CHAT.replace("{message}", message).replace("{time}", orginalTime)));
        }

        if(turboMode == 1){

            turboInterval += time;
            removeFromBarValue = 1.0 / turboInterval;

        } else {

            core.getConsoleLog().info("Initializing turboo drops...");
            drops = null;
            drops = new ArrayList<>();
            drops.addAll((Collection<? extends StoneDrop>) configuration.getList("drop-turbo"));
            core.getConsoleLog().info("Loaded " + drops.size() + " turboo drops");

            turboInterval = time;
            removeFromBarValue = 1.0 / turboInterval;

            turboMode = 1;

            BossBar bossBar = core.getServer().createBossBar(ColorUtil.fixColor(Lang.DROP_TURBO_BOSS_TITLE), BarColor.valueOf(Lang.DROP_TURBO_BOSS_BARCOLOR), BarStyle.SOLID);
            bossBar.setProgress(1.0);

            for (Player player : Bukkit.getOnlinePlayers()) {
                bossBar.addPlayer(player);
            }

            bossBar.setVisible(true);

            this.titleTask = Bukkit.getScheduler().runTaskTimerAsynchronously(core.getPlugin(), new Runnable() {
                @Override
                public void run() {

                    double progress = bossBar.getProgress() - removeFromBarValue;

                    if (progress < 0) progress = 0;

                    if (bossBar.getProgress() == 0) {
                        bossBar.setVisible(false);
                        bossBar.removeAll();
                        killTask();
                        turboMode = 0;
                        turboInterval = 0;
                        removeFromBarValue = 0;
                        reload();
                    }

                    bossBar.setProgress(progress);

                    for (Player player : Bukkit.getOnlinePlayers()) {

                        if (!bossBar.getPlayers().contains(player)) bossBar.addPlayer(player);

                    }
                }
            }, 20, 20);
        }
    }

    private void killTask(){
        if(titleTask != null){
            Bukkit.getScheduler().cancelTask(titleTask.getTaskId());
        }
    }

    public void reload(){
        create();
        dispose();
        setup();
    }

    public void dispose(){
        if(titleTask != null) removeFromBarValue = 1;

        turboMode = 0;
        turboInterval = 0;
        removeFromBarValue = 0;

        configuration = null;
        blockDropMaskHashMap = null;
        drops = null;

        titledMessageForTurbo = true;
        titleInterval = 0;
    }

    private void create() {
        core.getConsoleLog().info("Initializing drops...");
        if (!file.exists()) {
            file.getParentFile().mkdir();
            core.getPlugin().saveResource("drops.yml", true);
        }
    }

    private void setup(){

        configuration = YamlConfiguration.loadConfiguration(file);
        blockDropMaskHashMap = new HashMap<>();
        drops = new ArrayList<>();

        titledMessageForTurbo = configuration.getBoolean("turbodrop-info-title");
        titleInterval = configuration.getInt("title-interval");

        blockDropMaskHashMap.put(Material.STONE.name(), new BlockMask(core));
        //blockDropMaskHashMap.put(Material.GRANITE.name(), new BlockMask(core));

        drops.addAll((Collection<? extends StoneDrop>) configuration.getList("drop-normal"));

        core.getConsoleLog().info("Loaded " + drops.size() + " drops");

    }
}
