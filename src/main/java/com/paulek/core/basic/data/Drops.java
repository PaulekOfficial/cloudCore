package com.paulek.core.basic.data;

import com.paulek.core.basic.drop.StoneDrop;
import com.paulek.core.basic.drop.DropMask;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Drops {

    private static Map<Block, DropMask> blockDropMaskHashMap = new HashMap<>();
    private static List<StoneDrop> drops = new ArrayList<>();

    public static DropMask getMask(String name){
        return  blockDropMaskHashMap.get(name);
    }

    public static List<StoneDrop> getDrops() {
        return drops;
    }
}
