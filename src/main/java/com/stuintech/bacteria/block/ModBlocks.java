package com.stuintech.bacteria.block;

import com.stuintech.bacteria.BacteriaMod;
import com.stuintech.bacteria.block.entity.BacteriaBlockEntity;
import com.stuintech.bacteria.item.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
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
        Registry.register(Registry.BLOCK, BacteriaMod.MODID + ":replacer", replacer);
        Registry.register(Registry.BLOCK, BacteriaMod.MODID + ":replacer_starter", replacerStarter);
        Registry.register(Registry.BLOCK, BacteriaMod.MODID + ":destroyer", destroyer);
        Registry.register(Registry.BLOCK, BacteriaMod.MODID + ":destroyer_starter", destroyerStarter);
        Registry.register(Registry.BLOCK, BacteriaMod.MODID + ":must", must);

        //Register block items
        Registry.register(Registry.ITEM, BacteriaMod.MODID + ":replacer_starter",
                new BlockItem(replacerStarter, ModItems.settings.group(ItemGroup.REDSTONE)));
        Registry.register(Registry.ITEM, BacteriaMod.MODID + ":destroyer_starter",
                new BlockItem(destroyerStarter, ModItems.settings.group(ItemGroup.REDSTONE)));
        Registry.register(Registry.ITEM, BacteriaMod.MODID + ":must",
                new BlockItem(must, ModItems.settings.group(ItemGroup.DECORATIONS)));

        //Register block entities
        bacteriaEntity = register("bacteria", BacteriaBlockEntity::new, replacer, destroyer);

        //Register block tags
        unbreakable = TagKey.of(Registry.BLOCK_KEY, new Identifier(BacteriaMod.MODID, "unbreakable"));
        unplaceable = TagKey.of(Registry.BLOCK_KEY, new Identifier(BacteriaMod.MODID, "unplaceable"));
    }

    private static <T extends BlockEntity> BlockEntityType<T> register(String name, FabricBlockEntityTypeBuilder.Factory<T> factory, Block... blocks) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(BacteriaMod.MODID, name), FabricBlockEntityTypeBuilder.create(factory, blocks).build(null));
    }

    @Nullable
    public static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> checkType(BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<? super E> ticker) {
        return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
    }
}
