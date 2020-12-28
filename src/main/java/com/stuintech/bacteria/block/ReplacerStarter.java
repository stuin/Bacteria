package com.stuintech.bacteria.block;

import com.stuintech.bacteria.block.entity.BacteriaBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.HashSet;

public class ReplacerStarter extends Block implements IStarter {
    public ReplacerStarter(Block.Settings settings) {
        super(settings);
    }

    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos neighborPos, boolean moved) {
        if(world.isReceivingRedstonePower(pos))
            activate(world, pos);
    }

    @Override
    public boolean activate(World world, BlockPos pos) {
        BlockState input = world.getBlockState(pos.down());
        BlockState output = world.getBlockState(pos.up());
        if((output.getOutlineShape(world, pos.up()) == VoxelShapes.fullCube() ||
                output.getBlock() == Blocks.WATER || output.getBlock() == Blocks.LAVA) &&

                output.getBlock() != input.getBlock() && !input.isAir() &&
                output.getHardness(world, pos.up()) != -1 && input.getPistonBehavior() != PistonBehavior.BLOCK &&
                !output.getBlock().isIn(ModBlocks.unplaceable) && !output.getBlock().isIn(BlockTags.WITHER_IMMUNE) &&
                output.getBlock() != ModBlocks.replacer && output.getBlock() != ModBlocks.replacerStarter &&
                output.getBlock() != ModBlocks.destroyer && output.getBlock() != ModBlocks.destroyerStarter &&

                input.getHardness(world, pos.down()) != -1 && input.getPistonBehavior() != PistonBehavior.BLOCK &&
                !input.getBlock().isIn(ModBlocks.unbreakable) && !input.getBlock().isIn(BlockTags.WITHER_IMMUNE) &&
                input.getBlock() != ModBlocks.replacer && input.getBlock() != ModBlocks.destroyer) {

            new BacteriaBlockEntity(world, pos, new HashSet<>(Collections.singletonList(input.getBlock())), output.getBlock());
            world.setBlockState(pos.up(), Blocks.AIR.getDefaultState());
            return true;
        }
        return false;
    }
}
