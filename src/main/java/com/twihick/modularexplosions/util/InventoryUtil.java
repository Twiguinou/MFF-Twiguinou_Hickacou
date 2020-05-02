package com.twihick.modularexplosions.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InventoryUtil {

    public static boolean hasItemInInventory(Item item, PlayerEntity player) {
        PlayerInventory inventory = player.inventory;
        for(int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if(stack.getItem() == item) {
                return true;
            }
        }
        return false;
    }

    public static ItemStack getItemStackFromInventory(Item item, PlayerEntity player) {
        PlayerInventory inventory = player.inventory;
        for(int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if(stack.getItem() == item) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

}
