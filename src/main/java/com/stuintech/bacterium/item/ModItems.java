package com.stuintech.bacterium.item;

import com.stuintech.bacterium.Bacterium;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class ModItems {
    public static Item.Settings settings = new Item.Settings();

    public static Item jammer = new Jammer(settings.group(ItemGroup.REDSTONE));
    public static Item compactedMust = new Item(settings.group(ItemGroup.MATERIALS));
    
    public static void register() {
        Registry.register(Registry.ITEM, Bacterium.MODID + ":jammer", jammer);
        Registry.register(Registry.ITEM, Bacterium.MODID + ":compacted_must", compactedMust);
    }
}
