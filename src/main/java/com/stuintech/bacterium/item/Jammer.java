package com.stuintech.bacterium.item;

import com.stuintech.bacterium.block.entity.BacteriaBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class Jammer extends Item {

    public Jammer(Item.Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        BacteriaBlockEntity.setJammed(world);
        return TypedActionResult.success(itemStack);
    }
}
