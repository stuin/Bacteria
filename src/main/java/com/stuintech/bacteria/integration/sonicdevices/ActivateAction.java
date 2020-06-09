package com.stuintech.bacteria.integration.sonicdevices;

import com.stuintech.bacteria.block.IStarter;
import com.stuintech.sonicdevicesapi.DeviceList;
import com.stuintech.sonicdevicesapi.IAction;
import com.stuintech.sonicdevicesapi.ILoader;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class ActivateAction extends IAction.IBlockAction implements ILoader {

    @Override
    public boolean interact(PlayerEntity playerEntity, World world, BlockPos blockPos, Direction direction) {
        Block block = world.getBlockState(blockPos).getBlock();
        if(block instanceof IStarter)
            return ((IStarter) block).activate(world, blockPos);
        return false;
    }

    @Override
    public void onInitialize() {
        DeviceList.allActions[DeviceList.SCREWDRIVER][1].add(new ActivateAction());
        DeviceList.allActions[DeviceList.SCREWDRIVER][2].add(new JammerAction());
    }
}
