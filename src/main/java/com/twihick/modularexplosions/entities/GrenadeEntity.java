package com.twihick.modularexplosions.entities;

import com.twihick.modularexplosions.common.registry.EntitiesList;
import com.twihick.modularexplosions.common.registry.SoundsList;
import com.twihick.modularexplosions.util.world.CustomExplosion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class GrenadeEntity extends Entity {

    private static final DataParameter<Integer> PRIMER = DynamiteEntity.PRIMER;

    private final int power = 4;
    private int primer = 65;

    public GrenadeEntity(World worldIn) {
        super(EntitiesList.GRENADE, worldIn);
    }

    public GrenadeEntity(World worldIn, PlayerEntity thrower) {
        this(worldIn);
        this.setPositionAndRotation(thrower.getPosX(), thrower.getPosY()+thrower.getEyeHeight(), thrower.getPosZ(), thrower.getYaw(1.0F), 0.0F);
        Vec3d motion = thrower.getLookVec().scale(1.6F).add(thrower.getMotion());
        this.setRotation(this.rotationYaw, (float)Math.asin(-motion.y));
        this.setMotion(motion);
    }

    @Override
    protected void registerData() {
        this.dataManager.register(PRIMER, 65);
    }

    @Override
    public void tick() {
        Vec3d adjustedMotion = new Vec3d(this.getMotion().x, this.getMotion().y-0.03D, this.getMotion().z);
        this.move(MoverType.SELF, this.getMotion());
        if(this.onGround) {
            this.setMotion(Vec3d.ZERO);
            this.setRotation(this.getYaw(1.0F), 0.0F);
        }else if(this.collidedHorizontally) {
            this.setMotion(new Vec3d(-this.getMotion().x, this.getMotion().y, -this.getMotion().z).scale(0.5D));
        }else {
            this.setMotion(adjustedMotion);
        }
        this.primer--;
        if(this.primer <= 0) {
            this.remove();
            if(!this.world.isRemote) {
                CustomExplosion explosion = new CustomExplosion(this.world, this.getPosition(), this.power, 0.85F);
                explosion.explodeExcluding(this, SoundsList.DYNAMITE_BLAST);
            }
        }else {
            this.handleWaterMovement();
        }
    }

    /*
    All of these methods pasted from DynamiteEntity.
     */

    @Override
    protected void readAdditional(CompoundNBT compound) {
        this.setPrimer(compound.getShort("Primer"));
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putShort("Primer", (short)this.getPrimer());
    }

    public int getPrimerDataManager() {
        return this.dataManager.get(PRIMER);
    }

    public int getPrimer() {
        return this.primer;
    }

    public void setPrimer(int primer) {
        this.dataManager.set(PRIMER, primer);
        this.primer = primer;
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> key) {
        if(PRIMER.equals(key)) {
            this.primer = this.getPrimerDataManager();
        }
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
