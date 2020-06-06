package com.stuintech.bacteria.block;

import com.stuintech.bacteria.block.entity.BacteriaBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.Random;

public class BacteriaBlock extends BlockWithEntity {
    public BacteriaBlock(Block.Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return new BacteriaBlockEntity();
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        ((BacteriaBlockEntity)world.getBlockEntity(pos)).tick();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
