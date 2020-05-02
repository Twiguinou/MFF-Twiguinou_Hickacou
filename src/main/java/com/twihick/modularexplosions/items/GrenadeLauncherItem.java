package com.twihick.modularexplosions.items;

import com.twihick.modularexplosions.Main;
import com.twihick.modularexplosions.common.registry.SoundsList;
import com.twihick.modularexplosions.util.InventoryUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class GrenadeLauncherItem extends Item {

    public GrenadeLauncherItem() {
        super(new Item.Properties()
                .group(Main.getGroup())
                .maxStackSize(1));
        this.addPropertyOverride(new ResourceLocation("loaded"), (stack, world, entity) -> {
            if(entity != null && isLoaded(stack)) {
                return 1.0F;
            }
            return 0.0F;
        });
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.getCooldownTracker().setCooldown(this, 10);
        if(isLoaded(itemstack)) {
            /*
            TODO: Add entity shooting and sound event !
             */
            setLoaded(itemstack, false);
            worldIn.playMovingSound((PlayerEntity)null, playerIn, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 0.2F+this.random.nextFloat());
            return ActionResult.resultSuccess(itemstack);
        }else if(InventoryUtil.hasItemInInventory(Items.ARROW, playerIn)){
            ItemStack ammo = InventoryUtil.getItemStackFromInventory(Items.ARROW, playerIn);
            ammo.setCount(ammo.getCount()-1);
            setLoaded(itemstack, true);
            float pitch = this.random.nextFloat() * 0.3F;
            worldIn.playMovingSound((PlayerEntity)null, playerIn, SoundsList.GRENADE_LAUNCHER_RELOAD, SoundCategory.PLAYERS, 1.0F, -pitch);
            return ActionResult.resultPass(itemstack);
        }
        worldIn.playMovingSound((PlayerEntity)null, playerIn, SoundsList.GRENADE_LAUNCHER_EMPTY, SoundCategory.PLAYERS, 1.0F, 0.35F+this.random.nextFloat());
        return ActionResult.resultFail(itemstack);
    }

    public static boolean isLoaded(ItemStack stack) {
        CompoundNBT compound = stack.getTag();
        return compound != null && compound.getBoolean("Loaded");
    }

    public static void setLoaded(ItemStack stack, boolean loaded) {
        CompoundNBT compound = stack.getOrCreateTag();
        compound.putBoolean("Loaded", loaded);
    }

}
