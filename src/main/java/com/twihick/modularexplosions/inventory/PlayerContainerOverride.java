package com.twihick.modularexplosions.inventory;

import com.mojang.datafixers.util.Pair;
import com.twihick.modularexplosions.StringID;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PlayerContainerOverride extends PlayerContainer {

    public static final ResourceLocation EMPTY_BELT_SLOT = PlayerContainer.EMPTY_ARMOR_SLOT_SHIELD;

    public PlayerContainerOverride(PlayerInventory playerInventory, boolean localWorld, PlayerEntity playerIn) {
        super(playerInventory, localWorld, playerIn);
        this.addSlot(new Slot(playerInventory, 41, 77, 8) {
            public boolean isItemValid(ItemStack stack) {
                return stack.getItem() instanceof BowItem;
            }
            @OnlyIn(Dist.CLIENT)
            public Pair<ResourceLocation, ResourceLocation> func_225517_c_() {
                return Pair.of(AtlasTexture.LOCATION_BLOCKS_TEXTURE, EMPTY_BELT_SLOT);
            }
        });
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if(slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            EquipmentSlotType equipmentslottype = MobEntity.getSlotForItemStack(itemstack);
            if(index != 46) {
                if(!this.inventorySlots.get(46).getHasStack()) {
                    if(!this.mergeItemStack(itemstack1, 46, 47, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }
            if(index == 0) {
                if(!this.mergeItemStack(itemstack1, 9, 45, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(itemstack1, itemstack);
            }else if(index < 5) {
                if(!this.mergeItemStack(itemstack1, 9, 45, false)) {
                    return ItemStack.EMPTY;
                }
            }else if(index < 9) {
                if(!this.mergeItemStack(itemstack1, 9, 45, false)) {
                    return ItemStack.EMPTY;
                }
            }else if(equipmentslottype.getSlotType() == EquipmentSlotType.Group.ARMOR && !this.inventorySlots.get(8-equipmentslottype.getIndex()).getHasStack()) {
                int i = 8 - equipmentslottype.getIndex();
                if(!this.mergeItemStack(itemstack1, i, i+1, false)) {
                    return ItemStack.EMPTY;
                }
            }else if(equipmentslottype == EquipmentSlotType.OFFHAND && !this.inventorySlots.get(45).getHasStack()) {
                if(!this.mergeItemStack(itemstack1, 45, 46, false)) {
                    return ItemStack.EMPTY;
                }
            }else if(index == 46) {
                if(!this.mergeItemStack(itemstack1, 9, 45, false)) {
                    return ItemStack.EMPTY;
                }
            }else if(index < 36) {
                if(!this.mergeItemStack(itemstack1, 36, 45, false)) {
                    return ItemStack.EMPTY;
                }
            }else if(index < 45) {
                if(!this.mergeItemStack(itemstack1, 9, 36, false)) {
                    return ItemStack.EMPTY;
                }
            }else if(!this.mergeItemStack(itemstack1, 9, 45, false)) {
                return ItemStack.EMPTY;
            }
            if(itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            }else {
                slot.onSlotChanged();
            }
            if(itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }
            ItemStack itemstack2 = slot.onTake(playerIn, itemstack1);
            if(index == 0) {
                playerIn.dropItem(itemstack2, false);
            }
        }
        return itemstack;
    }

}
