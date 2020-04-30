package com.twihick.modularexplosions.items;

import com.twihick.modularexplosions.Main;
import com.twihick.modularexplosions.blocks.RemoteBombBlock;
import com.twihick.modularexplosions.tileentities.RemoteBombTileEntity;
import com.twihick.modularexplosions.util.world.WorldUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import java.util.Set;

public class RemoteControllerItem extends Item {

    public RemoteControllerItem() {
        super(new Item.Properties()
                .group(Main.getGroup())
                .maxStackSize(1));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if(playerIn.isDiscrete()) {
            Set<BlockPos> set = WorldUtil.getBlocksInRadius(worldIn, playerIn.getPosition(), 30, false);
            for(BlockPos pos : set) {
                if(worldIn.getBlockState(pos).getBlock() instanceof RemoteBombBlock) {
                    RemoteBombTileEntity bomb = (RemoteBombTileEntity) worldIn.getTileEntity(pos);
                    if(bomb.playerLink != null) {
                        if(bomb.playerLink == playerIn.getUniqueID()) {
                            worldIn.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 4.0F, Explosion.Mode.BREAK);
                        }
                    }
                }
            }
        }
        return ActionResult.resultSuccess(itemstack);
    }

}
