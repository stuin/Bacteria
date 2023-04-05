package com.stuintech.bacterium.item;

import com.stuintech.bacterium.Bacterium;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModItems {
    public static Item.Settings settings = new Item.Settings();

    public static Item jammer = new Jammer(settings);
    public static Item compactedMust = new Item(settings);

    //Testing SocketWrench library
    //public static BasicWrenchItem wrench = new BasicWrenchItem(
    //       settings.maxCount(1).maxDamage(SocketWrench.DEFAULT_WRENCH_DURABILITY));
    
    public static void register() {
        Registry.register(Registries.ITEM, Bacterium.MODID + ":jammer", jammer);
        Registry.register(Registries.ITEM, Bacterium.MODID + ":compacted_must", compactedMust);

        //Add items to creative groups
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries -> entries.add(jammer));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(compactedMust));

        //Registry.register(Registries.ITEM, Bacterium.MODID + ":wrench", wrench);
        //ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(wrench));
    }
}
