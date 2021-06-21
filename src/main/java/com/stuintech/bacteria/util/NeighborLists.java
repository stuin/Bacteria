package com.stuintech.bacteria.util;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.Set;

public class NeighborLists {
    public static final BlockPos[] close = new BlockPos[]{
            new BlockPos(-1, -1, -1),
            new BlockPos(0, -1, -1),
            new BlockPos(1, -1, -1),
            new BlockPos(-1, 0, -1),
            new BlockPos(0, 0, -1),
            new BlockPos(1, 0, -1),
            new BlockPos(-1, 1, -1),
            new BlockPos(0, 1, -1),
            new BlockPos(1, 1, -1),
            new BlockPos(-1, -1, 0),
            new BlockPos(0, -1, 0),
            new BlockPos(1, -1, 0),
            new BlockPos(-1, 0, 0),
            new BlockPos(0, 0, 0),
            new BlockPos(1, 0, 0),
            new BlockPos(-1, 1, 0),
            new BlockPos(0, 1, 0),
            new BlockPos(1, 1, 0),
            new BlockPos(-1, -1, 1),
            new BlockPos(0, -1, 1),
            new BlockPos(1, -1, 1),
            new BlockPos(-1, 0, 1),
            new BlockPos(0, 0, 1),
            new BlockPos(1, 0, 1),
            new BlockPos(-1, 1, 1),
            new BlockPos(0, 1, 1),
            new BlockPos(1, 1, 1),
    };
    
    public static final BlockPos[] far = new BlockPos[]{
            new BlockPos(-2,0,0),
            new BlockPos(2,0,0),
            new BlockPos(0,-2,0),
            new BlockPos(0,2,0),
            new BlockPos(0,0,-2),
            new BlockPos(0,0,2),
            new BlockPos(-3,0,0),
            new BlockPos(3,0,0),
            new BlockPos(0,-3,0),
            new BlockPos(0,3,0),
            new BlockPos(0,0,-3),
            new BlockPos(0,0,3),
    };

    private static final Random RANDOM = new Random();

    @Nullable
    public static BlockPos nextPlace(BlockView world, BlockPos pos, Set<Block> filter) {
        int randI = RANDOM.nextInt(27);
        int i = randI;
        
        //Try all close blocks
        do {
            if(filter.contains(world.getBlockState(NeighborLists.close[i].add(pos)).getBlock())) {
                return NeighborLists.close[i].add(pos);
            } else
                i = (i + 1) % 27;
        } while(i != randI);
        
        //Try specific far blocks
        for(i = 0; i < 12; i++) {
            if(filter.contains(world.getBlockState(NeighborLists.far[i].add(pos)).getBlock()))
                return NeighborLists.far[i].add(pos);
        }

        return null;
    }
}
