package com.stuintech.bacteria.item;

import com.stuintech.bacteria.block.entity.BacteriaBlockEntity;
import com.stuintech.bacteria.util.SyncedList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class Jammer extends Item {
    private static final SyncedList<Integer> timers = new SyncedList<>(0);
    private static final int MAXTIME = (BacteriaBlockEntity.MAXDELAY + BacteriaBlockEntity.MINDELAY) * 2;

    public Jammer(Item.Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        
        if(itemStack.getOrCreateTag().getInt("timer") <= 0)
            itemStack.getOrCreateTag().putInt("timer", timers.addNext(MAXTIME));
        BacteriaBlockEntity.jammed = true;
        return TypedActionResult.success(itemStack);
    }

    @Override
    public void inventoryTick(ItemStack itemStack, World world, Entity entity, int slot, boolean selected) {
        if(itemStack.getOrCreateTag().getInt("timer") > 0) {
            int timer = itemStack.getOrCreateTag().getInt("timer") - 1;
            if(timer >= timers.size()) {
                itemStack.getOrCreateTag().putInt("timer", timers.addNext(MAXTIME));
                BacteriaBlockEntity.jammed = true;
            } else {
                //Check existing timer
                int time = timers.get(timer);
                if(time > 0) {
                    timers.set(timer, time - 1);
                } else {
                    //End timer
                    BacteriaBlockEntity.jammed = false;
                    itemStack.getOrCreateTag().putInt("timer", 0);
                    timers.clear(timer);
                }
            }
        }
    }
}
