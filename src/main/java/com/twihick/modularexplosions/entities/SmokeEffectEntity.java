package com.twihick.modularexplosions.entities;

import com.twihick.modularexplosions.common.registry.EntitiesList;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

public class SmokeEffectEntity extends Entity {

    private int runningTicks;
    private int maxLifetime;
    private float maxSize;

    public SmokeEffectEntity(World worldIn) {
        super(EntitiesList.SMOKE_EFFECT, worldIn);
        this.preventEntitySpawning = true;
        this.noClip = true;
    }

    public SmokeEffectEntity(World worldIn, BlockPos pos, int maxLifetime, float maxSize) {
        this(worldIn);
        this.maxLifetime = maxLifetime;
        this.maxSize = maxSize;
        this.setPosition(pos.getX(), pos.getY(), pos.getZ());
        this.setMotion(Vec3d.ZERO);
    }

    @Override
    protected void registerData() {
    }

    @Override
    public void tick() {
        BlockPos pos = this.getPosition();
        world.addParticle(new ItemParticleData(ParticleTypes.ITEM, new ItemStack(Items.LIGHT_GRAY_WOOL)), pos.getX(), pos.getY(), pos.getZ(), this.rand.nextDouble(), this.rand.nextDouble()*0.2D, this.rand.nextDouble());
        world.addParticle(new ItemParticleData(ParticleTypes.ITEM, new ItemStack(Items.GRAY_WOOL)), pos.getX(), pos.getY(), pos.getZ(), this.rand.nextDouble(), this.rand.nextDouble()*0.2D, this.rand.nextDouble());
        world.addParticle(new ItemParticleData(ParticleTypes.ITEM, new ItemStack(Items.BLACK_WOOL)), pos.getX(), pos.getY(), pos.getZ(), this.rand.nextDouble(), this.rand.nextDouble()*0.2D, this.rand.nextDouble());
        runningTicks++;
        if(runningTicks >= maxLifetime) {
            this.remove();
        }
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
