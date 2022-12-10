package com.stuintech.bacterium.block;

import com.stuintech.bacterium.Bacterium;
import com.stuintech.bacterium.block.entity.BacteriaBlockEntity;
import com.stuintech.bacterium.item.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class ModBlocks {
    public static AbstractBlock.Settings settings = FabricBlockSettings.copy(Blocks.SPONGE);
    public static AbstractBlock.Settings breakSettings = FabricBlockSettings.copy(Blocks.SPONGE).breakInstantly();
    
    public static final Block replacer = new BacteriaBlock(breakSettings);
    public static final Block replacerStarter = new ReplacerStarter(settings);
    public static final Block destroyer = new BacteriaBlock(breakSettings);
    public static final Block destroyerStarter = new DestroyerStarter(settings);
    public static final Block must = new MustBlock(settings);

    public static BlockEntityType<BacteriaBlockEntity> bacteriaEntity;

    //Block tags
    public static TagKey<Block> unbreakable;
    public static TagKey<Block> unplaceable;
    
    public static void register() {
        //Register blocks
        Registry.register(Registries.BLOCK, Bacterium.MODID + ":replacer", replacer);
        Registry.register(Registries.BLOCK, Bacterium.MODID + ":replacer_starter", replacerStarter);
        Registry.register(Registries.BLOCK, Bacterium.MODID + ":destroyer", destroyer);
        Registry.register(Registries.BLOCK, Bacterium.MODID + ":destroyer_starter", destroyerStarter);
        Registry.register(Registries.BLOCK, Bacterium.MODID + ":must", must);

        //Register block items
        Registry.register(Registries.ITEM, Bacterium.MODID + ":replacer_starter",
                new BlockItem(replacerStarter, ModItems.settings));
        Registry.register(Registries.ITEM, Bacterium.MODID + ":destroyer_starter",
                new BlockItem(destroyerStarter, ModItems.settings));
        Registry.register(Registries.ITEM, Bacterium.MODID + ":must",
                new BlockItem(must, ModItems.settings));

        //Add items to creative groups
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries -> entries.add(replacerStarter));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries -> entries.add(destroyerStarter));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> entries.add(must));

        //Register block entities
        bacteriaEntity = register("bacteria", BacteriaBlockEntity::new, replacer, destroyer);

        //Register block tags
        unbreakable = TagKey.of(RegistryKeys.BLOCK, new Identifier(Bacterium.MODID, "unbreakable"));
        unplaceable = TagKey.of(RegistryKeys.BLOCK, new Identifier(Bacterium.MODID, "unplaceable"));
    }

    private static <T extends BlockEntity> BlockEntityType<T> register(String name, FabricBlockEntityTypeBuilder.Factory<T> factory, Block... blocks) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Bacterium.MODID, name), FabricBlockEntityTypeBuilder.create(factory, blocks).build(null));
    }

    @Nullable
    public static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> checkType(BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<? super E> ticker) {
        return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
    }
}
