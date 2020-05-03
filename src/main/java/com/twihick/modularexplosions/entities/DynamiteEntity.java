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
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class DynamiteEntity extends Entity {

    public static final DataParameter<Integer> PRIMER = EntityDataManager.createKey(DynamiteEntity.class, DataSerializers.VARINT);

    private PlayerEntity owner;
    private int primer = 60;
    private int power;

    public DynamiteEntity(World worldIn) {
        super(EntitiesList.DYNAMITE, worldIn);
        this.preventEntitySpawning = true;
    }

    public DynamiteEntity(World worldIn, PlayerEntity thrower, int power, double speedFactor) {
        this(worldIn);
        this.owner = thrower;
        this.power = power;
        this.setPositionAndRotation(thrower.getPosX(), thrower.getPosY()+thrower.getEyeHeight(), thrower.getPosZ(), thrower.rotationYaw, 0.0F);
        Vec3d motion = thrower.getLookVec().scale(speedFactor).add(thrower.getMotion());
        this.setMotion(motion);
    }

    @Override
    protected void registerData() {
        this.dataManager.register(PRIMER, 60);
    }

    @Override
    public void tick() {
        Vec3d adjustedMotion = new Vec3d(this.getMotion().x, this.getMotion().y-0.04D, this.getMotion().z);
        this.move(MoverType.SELF, this.getMotion());
        if(this.onGround) {
            this.setMotion(new Vec3d(this.getMotion().x*0.7D, -this.getMotion().y*0.98D, this.getMotion().z*0.7D));
            this.setRotation(this.getYaw(1.0F), 0.0F);
        }else if(this.collidedHorizontally) {
            Direction direction = Direction.getFacingFromVector(this.getMotion().x, this.getMotion().y, this.getMotion().z);
            switch(direction) {
                case EAST:
                    this.setMotion(new Vec3d(this.getMotion().x, this.getMotion().y, -this.getMotion().z).scale(0.7D));
                    break;
                case SOUTH:
                    this.setMotion(new Vec3d(-this.getMotion().x, this.getMotion().y, this.getMotion().z).scale(0.7D));
                    break;
                case WEST:
                    this.setMotion(new Vec3d(this.getMotion().x, this.getMotion().y, -this.getMotion().z).scale(0.7D));
                    break;
                default:
                    this.setMotion(new Vec3d(-this.getMotion().x, this.getMotion().y, this.getMotion().z).scale(0.7D));
                    break;
            }
        }else {
            this.setRotation(this.getYaw(1.0F), this.getPitch(1.0F)+7.5F);
            this.setMotion(adjustedMotion);
        }
        this.primer--;
        if(this.primer <= 0) {
            this.remove();
            if(!this.world.isRemote) {
                CustomExplosion explosion = new CustomExplosion(this.world, this.getPosition(), this.power, 0.88F);
                explosion.explodeExcluding(this, SoundsList.DYNAMITE_BLAST);
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
