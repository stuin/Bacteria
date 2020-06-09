package com.stuintech.bacteria.block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IStarter {
    boolean activate(World world, BlockPos pos);
}
