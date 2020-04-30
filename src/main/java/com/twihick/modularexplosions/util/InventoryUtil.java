package com.twihick.modularexplosions.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import java.util.UUID;

public class InventoryUtil {

    public static boolean hasItemInInventory(World world, UUID uuid, Item item) {
        PlayerEntity player = world.getPlayerByUuid(uuid);
        return hasItemInInventory(player, item);
    }

    public static boolean hasItemInInventory(PlayerEntity player, Item item) {
        for(int i = 0; i < player.inventory.getSizeInventory(); i++) {
            if(player.inventory.getStackInSlot(i).getItem() == item) {
                return true;
            }
        }
        return false;
    }

}
