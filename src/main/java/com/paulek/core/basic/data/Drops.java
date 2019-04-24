package com.paulek.core.basic.data;

import com.paulek.core.basic.drop.DropMask;
import com.paulek.core.basic.drop.StoneDrop;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Drops {

    private Map<Block, DropMask> blockDropMaskHashMap = new HashMap<>();
    private List<StoneDrop> drops = new ArrayList<>();

    public DropMask getMask(String name) {
        return blockDropMaskHashMap.get(name);
    }

    public List<StoneDrop> getDrops() {
        return drops;
    }
}
