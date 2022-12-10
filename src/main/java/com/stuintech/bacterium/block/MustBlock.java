package com.stuintech.bacterium.block;

import com.stuintech.bacterium.util.NeighborLists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.math.random.RandomSeed;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MustBlock extends Block implements Fertilizable {
    public static final IntProperty AGE;
    private static final Set<Block> filter = new HashSet<>(Collections.singletonList(Blocks.WATER));

    public MustBlock(Block.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        if(world != null) {
            int i = state.get(AGE) + random.nextInt(3) + 1;
            if(i < 16)
                world.setBlockState(pos, state.with(AGE, i));
            if(random.nextInt(8) + 8 < i) {
                BlockPos next = NeighborLists.nextPlace(world, pos, filter, random);
                if(next != null) {
                    world.setBlockState(next, this.getDefaultState());
                    world.setBlockState(pos, state.with(AGE, random.nextInt(10)));
                }
            }
        }
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return NeighborLists.nextPlace(world, pos, filter, 0) != null;
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        grow(world, random, pos, state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    static {
        AGE = Properties.AGE_15;
    }
}
