package com.stuintech.bacteria.block;

import com.stuintech.bacteria.block.entity.BacteriaBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DestroyerStarter extends Block {
    public DestroyerStarter(Settings settings) {
        super(settings);
    }

    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos neighborPos, boolean moved) {
        if(world.isReceivingRedstonePower(pos)) {
            Set<Block> s = new HashSet<>();
            BlockState input = world.getBlockState(pos.up());
            BlockPos next = pos.up();
            while(!input.isAir() &&
                    input.getBlock() != ModBlocks.replacer &&
                    input.getBlock() != ModBlocks.destroyer &&
                    input.getHardness(world, next) != -1) {
                s.add(world.getBlockState(next).getBlock());
                world.setBlockState(next, Blocks.AIR.getDefaultState());
                next = next.up();
                input = world.getBlockState(next);
            }

            if(s.size() > 0) {
                new BacteriaBlockEntity(world, pos, s, Blocks.AIR);
            }
            
        }
    }
}
