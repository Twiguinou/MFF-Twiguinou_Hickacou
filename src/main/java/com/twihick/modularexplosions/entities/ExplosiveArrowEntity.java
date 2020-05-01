package com.twihick.modularexplosions.entities;

import com.twihick.modularexplosions.common.registry.EntitiesList;
import com.twihick.modularexplosions.common.registry.ItemsList;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ExplosiveArrowEntity extends AbstractArrowEntity {

    public ExplosiveArrowEntity(World worldIn) {
        super(EntitiesList.EXPLOSIVE_ARROW, worldIn);
    }

    public ExplosiveArrowEntity(World worldIn, LivingEntity shooter) {
        super(EntitiesList.EXPLOSIVE_ARROW, shooter, worldIn);
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(ItemsList.EXPLOSIVE_ARROW);
    }

    @Override
    protected void arrowHit(LivingEntity living) {
        super.arrowHit(living);
        /*
        TODO: ICI TU METS L'EXPLOSION !
         */
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
