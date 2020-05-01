package com.twihick.modularexplosions.items;

import com.twihick.modularexplosions.Main;
import com.twihick.modularexplosions.entities.ExplosiveArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.SpectralArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ExplosiveArrowItem extends ArrowItem {

    public ExplosiveArrowItem() {
        super(new Item.Properties()
                .group(Main.getGroup()));
    }

    @Override
    public AbstractArrowEntity createArrow(World worldIn, ItemStack stack, LivingEntity shooter) {
        return new ExplosiveArrowEntity(worldIn, shooter);
    }

}
