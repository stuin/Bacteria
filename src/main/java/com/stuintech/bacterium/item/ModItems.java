package com.stuintech.bacterium.item;

import com.stuintech.bacterium.Bacterium;
import com.stuintech.socketwrench.SocketWrench;
import com.stuintech.socketwrench.item.BasicWrenchItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class ModItems {
    public static Item.Settings settings = new Item.Settings();

    public static Item jammer = new Jammer(settings.group(ItemGroup.REDSTONE));
    public static Item compactedMust = new Item(settings.group(ItemGroup.MATERIALS));

    //Testing SocketWrench library
    //public static BasicWrenchItem wrench = new BasicWrenchItem(settings.group(ItemGroup.TOOLS)
    //        .maxCount(1).maxDamage(SocketWrench.DEFAULT_WRENCH_DURABILITY));
    
    public static void register() {
        Registry.register(Registry.ITEM, Bacterium.MODID + ":jammer", jammer);
        Registry.register(Registry.ITEM, Bacterium.MODID + ":compacted_must", compactedMust);

        //Registry.register(Registry.ITEM, Bacterium.MODID + ":wrench", wrench);
    }
}
