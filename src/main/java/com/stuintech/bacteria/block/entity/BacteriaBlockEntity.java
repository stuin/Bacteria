package com.stuintech.bacteria.block.entity;

import com.stuintech.bacteria.block.ModBlocks;
import com.stuintech.bacteria.util.NeighborLists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.inventory.Inventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.tick.OrderedTick;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BacteriaBlockEntity extends BlockEntity {
    private Set<Block> input;
    private Block output;
    private long nextTick = 0;
    private boolean jammed = false;

    private static final Random RANDOM = new Random();
    public static final int MAXDELAY = 30;
    public static final int MINDELAY = 10;
    public static long jammedTick = 0;

    public BacteriaBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.bacteriaEntity, pos, state);
    }

    public static void replace(World world, BlockPos pos, Set<Block> input, Block output) {
        //Drop items from previous block
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof Inventory) {
            ItemScatterer.spawn(world, pos, (Inventory)blockEntity);
            world.updateComparators(pos, world.getBlockState(pos).getBlock());
        }

        //Decide new block state
        BlockState state = ModBlocks.replacer.getDefaultState();
        if(output == Blocks.AIR)
            state = ModBlocks.destroyer.getDefaultState();

        //Replace with new bacteria block
        world.setBlockState(pos, state);
        if(world.getBlockEntity(pos) instanceof BacteriaBlockEntity bacteriaEntity) {
            bacteriaEntity.input = input;
            bacteriaEntity.output = output;
        }

        world.playSound(null, pos, SoundEvents.BLOCK_CHORUS_FLOWER_GROW, SoundCategory.BLOCKS, .8f, 1f);
    }

    public static void tick(World world, BlockPos pos, BlockState state, BacteriaBlockEntity blockEntity) {
        blockEntity.runTick(world.getRandom());
    }

    public void runTick(Random random) {
        if(world != null && !world.isClient) {
            //Update jammer time
            if(jammedTick != 0)
                jammed = true;
            if(world.getTime() >= jammedTick)
                jammedTick = 0;

            //Spread
            if(nextTick == 0)
                nextTick = getNextTick();
            else if(world.getTime() >= nextTick) {
                BlockPos next = NeighborLists.nextPlace(world, pos, input, random);
                if(!jammed && next != null) {
                    replace(world, next, this.input, this.output);
                    nextTick = getNextTick();
                } else {
                    world.setBlockState(pos, output.getDefaultState());
                    markRemoved();
                }
            }
        }
    }

    @Override
    public void readNbt(NbtCompound tag) {
        input = new HashSet<>();
        for(String s : tag.getString("inputID").split("#"))
            input.add(Registry.BLOCK.get(Identifier.tryParse(s)));

        output = Registry.BLOCK.get(Identifier.tryParse(tag.getString("outputID")));
        super.readNbt(tag);
    }

    @Override
    protected void writeNbt(NbtCompound tag) {
        StringBuilder s = new StringBuilder();
        for(Block b : input)
            s.append(Registry.BLOCK.getId(b)).append("#");
        tag.putString("inputID", s.toString());

        tag.putString("outputID", Registry.BLOCK.getId(output).toString());
        super.writeNbt(tag);
    }

    private long getNextTick() {
        if(world == null)
            return 0;
        return world.getTime() + RANDOM.nextInt(MAXDELAY) + MINDELAY;
    }

    public static void setJammed(World world) {
        jammedTick = world.getTime() + MINDELAY;
    }
}
