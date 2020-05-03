package com.twihick.modularexplosions.items;

import com.twihick.modularexplosions.Main;
import com.twihick.modularexplosions.blocks.RemoteBombBlock;
import com.twihick.modularexplosions.tileentities.RemoteBombTileEntity;
import com.twihick.modularexplosions.util.world.CustomExplosion;
import com.twihick.modularexplosions.util.world.WorldUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
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
                            bomb.setActivated();
                        }
                    }
                }
            }
        }
        return ActionResult.resultSuccess(itemstack);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        StringTextComponent textComponent = new StringTextComponent("Right-Click to link to a remote bomb.");
        StringTextComponent textComponent1 = new StringTextComponent("Right-Click + Shift to explode linked bombs.");
        StringTextComponent textComponent2 = new StringTextComponent("Bombs must be in a radius of 30 blocks.");
        tooltip.add(textComponent);
        tooltip.add(textComponent1);
        tooltip.add(textComponent2);
    }

}
