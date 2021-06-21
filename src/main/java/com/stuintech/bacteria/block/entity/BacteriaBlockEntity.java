package com.stuintech.bacteria.block.entity;

import com.stuintech.bacteria.block.BacteriaBlock;
import com.stuintech.bacteria.block.ModBlocks;
import com.stuintech.bacteria.util.NeighborLists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.inventory.Inventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BacteriaBlockEntity extends BlockEntity implements BlockEntityTicker<BacteriaBlockEntity> {
    private Set<Block> input;
    private Block output;

    private static final Random RANDOM = new Random();
    public static final int MAXDELAY = 30;
    public static final int MINDELAY = 10;
    public static final int JAMMERDELAY = MAXDELAY * 2;
    public static boolean jammed = false;
    public static long jammedAt = 0;

    public BacteriaBlockEntity(BlockPos pos, BlockState state, Set<Block> input, Block output) {
        super(ModBlocks.bacteriaEntity, pos, state);
        this.input = input;
        this.output = output;

        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof Inventory) {
            ItemScatterer.spawn(world, pos, (Inventory)blockEntity);
            world.updateComparators(pos, world.getBlockState(pos).getBlock());
        }

        state = ModBlocks.replacer.getDefaultState();
        if(output == Blocks.AIR)
            state = ModBlocks.destroyer.getDefaultState();

        world.setBlockState(pos, state);
        world.getBlockTickScheduler().schedule(pos, world.getBlockState(pos).getBlock(), RANDOM.nextInt(MAXDELAY) + MINDELAY);
        world.playSound(null, pos, SoundEvents.BLOCK_CHORUS_FLOWER_GROW, SoundCategory.BLOCKS, .8f, 1f);
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state, BacteriaBlockEntity blockEntity) {
        blockEntity.runTick();
    }

    
    public void runTick() {
        if(world != null) {
            //Update jammer time
            if(jammed) {
                if(jammedAt == 0)
                    jammedAt = world.getTime();
                else if(jammedAt + JAMMERDELAY < world.getTime() || jammedAt > world.getTime()) {
                    jammed = false;
                    jammedAt = 0;
                }
            }

            //Spread
            if(!jammed) {
                BlockPos next = NeighborLists.nextPlace(world, pos, input);
                if(next != null) {
                    new BacteriaBlockEntity(world, next, input, output);
                    world.getBlockTickScheduler().schedule(pos, world.getBlockState(pos).getBlock(), RANDOM.nextInt(MAXDELAY) + MINDELAY);
                } else {
                    world.setBlockState(pos, output.getDefaultState());
                    markRemoved();
                }
            } else {
                world.setBlockState(pos, output.getDefaultState());
                markRemoved();
            }
        }
    }

    @Override
    public void fromTag(NbtCompound tag) {
        input = new HashSet<>();
        for(String s : tag.getString("inputID").split("#"))
            input.add(Registry.BLOCK.get(Identifier.tryParse(s)));
        
        output = Registry.BLOCK.get(Identifier.tryParse(tag.getString("outputID")));
        super.fromTag(state, tag);
    }

    @Override
    public NbtCompound toTag(NbtCompound tag) {
        StringBuilder s = new StringBuilder();
        for(Block b : input)
            s.append(Registry.BLOCK.getId(b).toString()).append("#");
        tag.putString("inputID", s.toString());
        
        tag.putString("outputID", Registry.BLOCK.getId(output).toString());
        return super.toTag(tag);
    }
}
