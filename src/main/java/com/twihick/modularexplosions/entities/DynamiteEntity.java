package com.twihick.modularexplosions.entities;

import com.twihick.modularexplosions.common.registry.EntitiesList;
import com.twihick.modularexplosions.util.world.CustomExplosion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class DynamiteEntity extends Entity {

    private PlayerEntity owner;
    private final int maxLifetime = 30;
    private int runningTicks = 0;
    private int power;

    public DynamiteEntity(World worldIn) {
        super(EntitiesList.DYNAMITE, worldIn);
        this.preventEntitySpawning = true;
    }

    public DynamiteEntity(World worldIn, PlayerEntity thrower, int power, double speedFactor) {
        this(worldIn);
        this.owner = thrower;
        this.power = power;
        this.setPositionAndRotation(thrower.getPosX(), thrower.getPosY(), thrower.getPosZ(), thrower.rotationYaw, 0.0F);
        Vec3d motion = thrower.getLookVec().scale(speedFactor);
        this.setMotion(motion);
    }

    @Override
    protected void registerData() {
    }

    @Override
    public void tick() {
        Vec3d adjustedMotion = new Vec3d(this.getMotion().x, this.getMotion().y-0.04D, this.getMotion().z);
        this.move(MoverType.SELF, this.getMotion());
        if(this.onGround) {
            this.setMotion(Vec3d.ZERO);
            this.setRotation(this.getYaw(1.0F), 0.0F);
        }else {
            this.setRotation(this.getYaw(1.0F), this.getPitch(1.0F)+7.5F);
            this.setMotion(adjustedMotion);
        }
        this.runningTicks++;
        if(this.runningTicks >= maxLifetime) {
            this.remove();
            if(!this.world.isRemote) {
                CustomExplosion explosion = new CustomExplosion(this.world, this.getPosition(), this.power, 0.85F);
                explosion.explodeExcluding(this);
            }
        }else {
            this.handleWaterMovement();
            if(this.world.isRemote) {
                this.world.addParticle(ParticleTypes.SMOKE, this.getPosX(), this.getPosY(), this.getPosZ(), 0.0D, 0.0D, 0.0D);
            }
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
