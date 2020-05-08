package com.twihick.modularexplosions.items;

import com.twihick.modularexplosions.Main;
import com.twihick.modularexplosions.entities.BallisticMissileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class BallisticMissileItem extends Item {

    public BallisticMissileItem() {
        super(new Item.Properties()
                .group(Main.getGroup())
                .maxStackSize(1));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if(!worldIn.isRemote) {
            RayTraceResult rtr = Item.rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.NONE);
            RayTraceResult.Type type = rtr.getType();
            if(type == RayTraceResult.Type.BLOCK) {
                BlockRayTraceResult blockRtr = (BlockRayTraceResult) rtr;
                BlockState state = worldIn.getBlockState(blockRtr.getPos());
                if(state.getBlock() == Blocks.IRON_BLOCK && blockRtr.getFace() == Direction.UP) {
                    BallisticMissileEntity missile = new BallisticMissileEntity(worldIn, blockRtr.getPos().getX()+0.5D, blockRtr.getPos().getY()+1.0D, blockRtr.getPos().getZ()+0.5D);
                    worldIn.addEntity(missile);
                    return ActionResult.resultSuccess(itemstack);
                }
            }
        }
        return ActionResult.resultFail(itemstack);
    }

}
