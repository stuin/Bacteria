package com.stuintech.bacterium.integration.socketwrench;

import com.stuintech.bacterium.Bacterium;
import com.stuintech.bacterium.block.IStarter;
import com.stuintech.socketwrench.socket.Socket;
import com.stuintech.socketwrench.socket.SocketSetLoader;
import com.stuintech.socketwrench.socket.SocketSetManager;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ActivateAction extends Socket.BlockActionSocket implements SocketSetLoader {

    @Override
    public boolean onFasten(PlayerEntity playerEntity, World world, BlockPos blockPos, Vec3d vec3d, Direction direction) {
        Block block = world.getBlockState(blockPos).getBlock();
        if(block instanceof IStarter)
            return ((IStarter) block).activate(world, blockPos);
        return false;
    }

    @Override
    public void registerSockets() {
        SocketSetManager.addSocket(new ActivateAction(), new Identifier("sonicdevices", "base.activate"));
        SocketSetManager.addSocket(new JammerAction(), new Identifier("sonicdevices", "base.deactivate"));
    }
}
