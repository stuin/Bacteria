package com.stuintech.bacteria.integration.socketwrench;

import com.stuintech.bacteria.block.BacteriaBlock;
import com.stuintech.bacteria.block.entity.BacteriaBlockEntity;
import com.stuintech.socketwrench.socket.Socket;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class JammerAction extends Socket.BlockActionSocket {
    @Override
    public boolean onFasten(PlayerEntity playerEntity, World world, BlockPos blockPos, Vec3d vec3d, Direction direction) {
        Block block = world.getBlockState(blockPos).getBlock();
        if(block instanceof BacteriaBlock) {
            BacteriaBlockEntity.setJammed(world);
            return true;
        }
        return false;
    }
}
