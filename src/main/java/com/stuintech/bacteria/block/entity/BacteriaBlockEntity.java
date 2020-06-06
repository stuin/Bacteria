package com.stuintech.bacteria.block.entity;

import com.stuintech.bacteria.block.ModBlocks;
import com.stuintech.bacteria.util.NeighborLists;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
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
    public static boolean jammed = false;
    
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
    }
    
    public void tick() {
        if(world != null && !jammed) {
            int randI = RANDOM.nextInt(27);
            int i = randI;
            boolean success = false;
            //Try all close blocks
            do {
                if(input.contains(world.getBlockState(NeighborLists.close[i].add(pos)).getBlock())) {
                    new BacteriaBlockEntity(world, NeighborLists.close[i].add(pos), input, output);
                    i = randI;
                    success = true;
                } else
                    i = (i + 1) % 27;
            } while(i != randI && !success);
            
            if(!success) {
                //Try specific far blocks
                for(i = 0; i < 12 && !success; i++)
                    if(input.contains(world.getBlockState(NeighborLists.far[i].add(pos)).getBlock())) {
                        new BacteriaBlockEntity(world, NeighborLists.far[i].add(pos), input, output);
                        success = true;
                    }
            }

            //Finish up
            if(success)
                world.getBlockTickScheduler().schedule(pos, world.getBlockState(pos).getBlock(), RANDOM.nextInt(MAXDELAY) + MINDELAY);
            else {
                world.setBlockState(pos, output.getDefaultState());
                markRemoved();
            }
        } else if(world != null) {
            world.setBlockState(pos, output.getDefaultState());
            markRemoved();
        }
    }

    @Override
    public void fromTag(CompoundTag tag) {
        input = new HashSet<>();
        for(String s : tag.getString("inputID").split("#"))
            input.add(Registry.BLOCK.get(Identifier.tryParse(s)));
        output = Registry.BLOCK.get(Identifier.tryParse(tag.getString("outputID")));
        super.fromTag(tag);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        StringBuilder s = new StringBuilder();
        for(Block b : input)
            s.append(Registry.BLOCK.getId(b).toString() + "#");
        tag.putString("inputID", s.toString());
        
        tag.putString("outputID", Registry.BLOCK.getId(output).toString());
        return super.toTag(tag);
    }
}
