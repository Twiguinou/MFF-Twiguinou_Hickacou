package com.twihick.modularexplosions.items;

import com.twihick.modularexplosions.Main;
import com.twihick.modularexplosions.StringID;
import com.twihick.modularexplosions.client.registry.KeyBindingsList;
import com.twihick.modularexplosions.common.registry.SoundsList;
import com.twihick.modularexplosions.util.world.CustomExplosion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ExplosiveBeltItem extends ArmorItem {

    public ExplosiveBeltItem() {
        super(new ClothArmorMaterial(), EquipmentSlotType.CHEST, new Item.Properties().group(Main.getGroup()));
    }

    @Override
    public void onArmorTick(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        if(KeyBindingsList.KEY_EXPLODE.isPressed() && !playerIn.isDiscrete()) {
            if(!worldIn.isRemote) {
                playerIn.setItemStackToSlot(EquipmentSlotType.CHEST, ItemStack.EMPTY);
                CustomExplosion explosion = new CustomExplosion(worldIn, playerIn.getPosition(), 3, 0.86196F);
                explosion.explodeExcluding(null, SoundsList.DYNAMITE_BLAST);
            }
        }
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return StringID.ID + ":textures/models/armor/cloth_layer_1.png";
    }

}
