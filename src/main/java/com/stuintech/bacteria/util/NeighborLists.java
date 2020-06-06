package com.stuintech.bacteria.util;

import net.minecraft.util.math.BlockPos;

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
}
