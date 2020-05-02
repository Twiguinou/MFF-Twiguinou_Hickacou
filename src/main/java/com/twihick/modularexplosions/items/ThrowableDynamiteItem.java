package com.twihick.modularexplosions.items;

import com.twihick.modularexplosions.Main;
import com.twihick.modularexplosions.entities.DynamiteEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ThrowableDynamiteItem extends Item {

    public ThrowableDynamiteItem() {
        super(new Item.Properties()
                .group(Main.getGroup())
                .maxStackSize(16));
    }

    @Override
    public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn) {
        return !playerIn.isCreative();
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if(!worldIn.isRemote) {
            DynamiteEntity dynamite = new DynamiteEntity(worldIn, playerIn, 4, 1.05D);
            worldIn.addEntity(dynamite);
        }
        return ActionResult.resultConsume(itemstack);
    }

}
