package com.twihick.modularexplosions.items;

import com.twihick.modularexplosions.Main;
import com.twihick.modularexplosions.StringID;
import com.twihick.modularexplosions.client.registry.KeyBindingsList;
import com.twihick.modularexplosions.common.registry.SoundsList;
import com.twihick.modularexplosions.util.world.CustomExplosion;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ExplosiveBeltItem extends ArmorItem {

    public ExplosiveBeltItem() {
        super(new ClothArmorMaterial(), EquipmentSlotType.CHEST, new Item.Properties().group(Main.getGroup()));
        this.addPropertyOverride(new ResourceLocation("activated"), (stack, world, entity) -> {
            if(entity != null && isActivated(stack)) {
                return 1.0F;
            }
            return 0.0F;
        });
    }

    @Override
    public void onArmorTick(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        if(isActivated(stack)) {
            if(!worldIn.isRemote) {
                playerIn.setItemStackToSlot(EquipmentSlotType.CHEST, ItemStack.EMPTY);
                CustomExplosion explosion = new CustomExplosion(worldIn, playerIn.getPosition(), 7, 0.86196F);
                explosion.explodeExcluding(null, SoundsList.DYNAMITE_BLAST);
            }
        }
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return StringID.ID + ":textures/models/armor/cloth_layer_1.png";
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        StringTextComponent textComponent = new StringTextComponent("Not the best way to do the things.");
        StringTextComponent keyComponent = new StringTextComponent("Press " + (char)KeyBindingsList.KEY_EXPLODE.getKey().getKeyCode() + " to activate");
        tooltip.add(textComponent);
        tooltip.add(keyComponent);
    }

    public static boolean isActivated(ItemStack stack) {
        CompoundNBT compound = stack.getTag();
        return compound != null && compound.getBoolean("activated");
    }

    public static void setActivated(ItemStack stack, boolean activated) {
        CompoundNBT compound = stack.getOrCreateTag();
        compound.putBoolean("activated", activated);
    }

}
