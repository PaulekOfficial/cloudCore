package com.paulek.core.common;

import org.bukkit.Material;

import java.util.Arrays;

public enum XMaterial {


    END_STONE(0, "END_STONE", "ENDER_STONE"),
    PISTON(0, "PISTON", "PISTON_BASE"),
    LEGACY_WOOD_BUTTON(0, "LEGACY_WOOD_BUTTON", "WOOD_BUTTON");

    private int data;
    private String[] materialNames;

    XMaterial(int data, String... materialNames){
        this.data = data;
        this.materialNames = materialNames;
    }

    public Material parseMaterial(){
        Material material = Material.matchMaterial(Arrays.toString(materialNames));
        if(material == null){

            for(String materialName : materialNames){
                material = Material.matchMaterial(materialName);

                if(material != null) break;

            }

        }
        return material;
    }

}
