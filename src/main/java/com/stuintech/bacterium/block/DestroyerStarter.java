package com.stuintech.bacterium.block;

import com.stuintech.bacterium.block.entity.BacteriaBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;

public class DestroyerStarter extends Block implements IStarter {
    public DestroyerStarter(Settings settings) {
        super(settings);
    }

    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos neighborPos, boolean moved) {
        if(world.isReceivingRedstonePower(pos))
            activate(world, pos);
    }
    
    public boolean activate(World world, BlockPos pos) {
        Set<Block> s = new HashSet<>();
        BlockState input = world.getBlockState(pos.up());
        BlockPos next = pos.up();
        while(!input.isAir() && !input.isIn(ModBlocks.unbreakable) &&
                input.getBlock() != ModBlocks.replacer && input.getBlock() != ModBlocks.destroyer) {

            s.add(world.getBlockState(next).getBlock());
            world.setBlockState(next, Blocks.AIR.getDefaultState());
            next = next.up();
            input = world.getBlockState(next);
        }

        if(s.size() > 0) {
            BacteriaBlockEntity.replace(world, pos, s, Blocks.AIR);
            return true;
        }
        return false;
    }
}
