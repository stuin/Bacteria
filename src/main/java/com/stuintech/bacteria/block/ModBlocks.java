package com.stuintech.bacteria.block;

import com.stuintech.bacteria.BacteriaMod;
import com.stuintech.bacteria.block.entity.BacteriaBlockEntity;
import com.stuintech.bacteria.item.ModItems;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.util.registry.Registry;

public class ModBlocks {
    public static FabricBlockSettings settings = FabricBlockSettings.copy(Blocks.SPONGE);
    public static FabricBlockSettings breakSettings = FabricBlockSettings.copy(Blocks.SPONGE).breakInstantly();
    
    public static final Block replacer = new BacteriaBlock(breakSettings.build());
    public static final Block replacerStarter = new ReplacerStarter(settings.build());
    public static final Block destroyer = new BacteriaBlock(breakSettings.build());
    public static final Block destroyerStarter = new DestroyerStarter(settings.build());
    public static final Block must = new MustBlock(settings.build());

    public static BlockEntityType<BacteriaBlockEntity> bacteriaEntity;
    
    public static void register() {
        //Register blocks
        Registry.register(Registry.BLOCK, BacteriaMod.MODID + ":replacer", replacer);
        Registry.register(Registry.BLOCK, BacteriaMod.MODID + ":replacer_starter", replacerStarter);
        Registry.register(Registry.BLOCK, BacteriaMod.MODID + ":destroyer", destroyer);
        Registry.register(Registry.BLOCK, BacteriaMod.MODID + ":destroyer_starter", destroyerStarter);
        Registry.register(Registry.BLOCK, BacteriaMod.MODID + ":must", must);

        //Register block items
        Registry.register(Registry.ITEM, BacteriaMod.MODID + ":replacer_starter", new BlockItem(replacerStarter, ModItems.settings));
        Registry.register(Registry.ITEM, BacteriaMod.MODID + ":destroyer_starter", new BlockItem(destroyerStarter, ModItems.settings));
        Registry.register(Registry.ITEM, BacteriaMod.MODID + ":must", new BlockItem(must, ModItems.settings));

        //Register block entities
        bacteriaEntity = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                BacteriaMod.MODID + ":bacteria",
                BlockEntityType.Builder.create(BacteriaBlockEntity::new, new Block[]{replacer, destroyer}).build(null));
    }
}
