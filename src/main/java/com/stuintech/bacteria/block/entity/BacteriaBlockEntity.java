package com.stuintech.bacteria.block.entity;

import com.stuintech.bacteria.block.ModBlocks;
import com.stuintech.bacteria.util.NeighborLists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BacteriaBlockEntity extends BlockEntity {
    private Set<Block> input;
    private Block output;

    private static final Random RANDOM = new Random();
    public static final int MAXDELAY = 30;
    public static final int MINDELAY = 10;
    public static final int JAMMERDELAY = MAXDELAY * 2;
    public static boolean jammed = false;
    public static long jammedAt = 0;
    
    public BacteriaBlockEntity() {
        super(ModBlocks.bacteriaEntity);
    }

    public BacteriaBlockEntity(World world, BlockPos pos, Set<Block> input, Block output) {
        super(ModBlocks.bacteriaEntity);
        this.input = input;
        this.output = output;

        if(output == Blocks.AIR)
            world.setBlockState(pos, ModBlocks.destroyer.getDefaultState());
        else
            world.setBlockState(pos, ModBlocks.replacer.getDefaultState());

        world.setBlockEntity(pos, this);
        world.getBlockTickScheduler().schedule(pos, world.getBlockState(pos).getBlock(), RANDOM.nextInt(MAXDELAY) + MINDELAY);
        world.playSound(null, pos, SoundEvents.BLOCK_CHORUS_FLOWER_GROW, SoundCategory.BLOCKS, .8f, 1f);
    }
    
    public void tick() {
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
    public void fromTag(BlockState state, CompoundTag tag) {
        input = new HashSet<>();
        for(String s : tag.getString("inputID").split("#"))
            input.add(Registry.BLOCK.get(Identifier.tryParse(s)));
        
        output = Registry.BLOCK.get(Identifier.tryParse(tag.getString("outputID")));
        super.fromTag(state, tag);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        StringBuilder s = new StringBuilder();
        for(Block b : input)
            s.append(Registry.BLOCK.getId(b).toString()).append("#");
        tag.putString("inputID", s.toString());
        
        tag.putString("outputID", Registry.BLOCK.getId(output).toString());
        return super.toTag(tag);
    }
}
