package com.stuintech.bacteria.integration.sonicdevices;

import com.stuintech.bacteria.block.BacteriaBlock;
import com.stuintech.bacteria.block.entity.BacteriaBlockEntity;
import com.stuintech.sonicdevicesapi.CancelActionException;
import com.stuintech.sonicdevicesapi.IAction;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class JammerAction extends IAction.IBlockAction {
    @Override
    public boolean interact(PlayerEntity playerEntity, World world, BlockPos blockPos, Direction direction) throws CancelActionException {
        Block block = world.getBlockState(blockPos).getBlock();
        if(block instanceof BacteriaBlock)
           return BacteriaBlockEntity.jammed = true;
        return false;
    }
}
