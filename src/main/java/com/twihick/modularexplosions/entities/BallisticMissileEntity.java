package com.twihick.modularexplosions.entities;

import com.twihick.modularexplosions.client.gui.BallisticMissileScreen;
import com.twihick.modularexplosions.common.registry.EntitiesList;
import com.twihick.modularexplosions.common.registry.SoundsList;
import com.twihick.modularexplosions.util.world.CustomExplosion;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class BallisticMissileEntity extends Entity {

    private static final DataParameter<Integer> ORIGIN_Y = EntityDataManager.createKey(BallisticMissileEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> TARGET_X = EntityDataManager.createKey(BallisticMissileEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> TARGET_Y = EntityDataManager.createKey(BallisticMissileEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> TARGET_Z = EntityDataManager.createKey(BallisticMissileEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> LAUNCHED = EntityDataManager.createKey(BallisticMissileEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> STAGE = EntityDataManager.createKey(BallisticMissileEntity.class, DataSerializers.VARINT);

    private int runningTickForSound = 0;

    public BallisticMissileEntity(World worldIn) {
        super(EntitiesList.BALLISTIC_MISSILE, worldIn);
        this.preventEntitySpawning = true;
    }

    public BallisticMissileEntity(World worldIn, double x, double y, double z) {
        this(worldIn);
        this.setPositionAndRotation(x, y, z, 0.0F, 0.0F);
        this.setMotion(Vec3d.ZERO);
        this.setTargetX((int)x);
        this.setTargetY((int)y);
        this.setTargetZ((int)z);
    }

    @Override
    protected void registerData() {
        this.dataManager.register(ORIGIN_Y, (int)this.getPosY());
        this.dataManager.register(TARGET_X, (int)this.getPosX());
        this.dataManager.register(TARGET_Y, (int)this.getPosY());
        this.dataManager.register(TARGET_Z, (int)this.getPosZ());
        this.dataManager.register(LAUNCHED, false);
        this.dataManager.register(STAGE, 0);
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBox(Entity entityIn) {
        return entityIn.canBePushed() ? entityIn.getBoundingBox() : null;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox() {
        return this.getBoundingBox();
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.removed;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if(this.isInvulnerableTo(source)) {
            return false;
        }else if(!this.world.isRemote && !this.removed) {
            if(source instanceof IndirectEntityDamageSource && source.getTrueSource() != null) {
                return false;
            }else {
                this.remove();
                return true;
            }
        }else {
            return true;
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public boolean isInRangeToRenderDist(double distance) {
        if(Minecraft.getInstance().gameSettings.fancyGraphics) {
            return true;
        }
        return super.isInRangeToRenderDist(distance);
    }

    @Override
    public void tick() {
        if(this.isLaunched()) {
            Vec3d adjustedMotion = new Vec3d(this.getMotion().x, this.getMotion().y-0.02D, this.getMotion().z);
            this.move(MoverType.SELF, this.getMotion());
            if(this.getStage() == 0) {
                if(!this.world.isRemote) {
                    if(runningTickForSound%6 == 0) {
                        runningTickForSound = 0;
                        this.world.playMovingSound((PlayerEntity)null, this, SoundsList.BALLISTIC_MISSILE_THRUST, SoundCategory.PLAYERS, 1.67F+this.rand.nextFloat(), this.rand.nextFloat()+(this.rand.nextFloat()*2));
                    }
                    runningTickForSound++;
                }
                Vec3d motionWithPush = adjustedMotion.add(0.0D, 0.0215D, 0.0D);
                motionWithPush = correctMotion(motionWithPush);
                this.setMotion(motionWithPush);
                if(this.getPosY() >= this.getOriginY()+100) {
                    this.setStage(1);
                }
            }else if(this.getStage() == 1) {
                Vec3d vec3d = new Vec3d(this.getTargetX()-this.getPosX(), this.getTargetY()-this.getPosY(), this.getTargetZ()-this.getPosZ()).scale(0.0001F);
                Vec3d motionWithPush = correctMotion(this.getMotion().add(vec3d));
                this.setMotion(motionWithPush);
                if(this.collidedVertically) {
                    this.remove();
                    if(!this.world.isRemote) {
                        CustomExplosion explosion = new CustomExplosion(this.world, this.getPosition(), 12, 0.89F);
                        explosion.explodeExcluding(null, SoundsList.BOMB_GENERIC);
                    }
                }
            }
            this.calculateYawAndPitch(this.getMotion());
            this.generateParticles(12);
        }
    }

    private void calculateYawAndPitch(Vec3d vec) {
        double vecX = vec.x;
        double vecY = vec.y;
        double vecZ = vec.z;
        float f = MathHelper.sqrt(horizontalMag(vec));
        float yaw = (float) (MathHelper.atan2(vecX, vecZ)*(double)(180/(float)Math.PI));
        float pitch = (float) (MathHelper.atan2(vecY, (double)f)*(double)(180F/(float)Math.PI));
        this.rotationYaw = yaw;
        this.rotationPitch = pitch;
        this.prevRotationYaw = yaw;
        this.prevRotationPitch = pitch;
    }

    private Vec3d correctMotion(Vec3d nextMotion) {
        if(this.getMotion().x > 3.5D) {
            nextMotion = new Vec3d(3.5D, nextMotion.y, nextMotion.z);
        }else if(this.getMotion().x < -3.5D) {
            nextMotion = new Vec3d(-3.5D, nextMotion.y, nextMotion.z);
        }
        if(this.getMotion().y > 3.5D) {
            nextMotion = new Vec3d(nextMotion.x, 3.5D, nextMotion.z);
        }else if(this.getMotion().y < -3.5D) {
            nextMotion = new Vec3d(nextMotion.x, -3.5D, nextMotion.z);
        }
        if(this.getMotion().z > 3.5D) {
            nextMotion = new Vec3d(nextMotion.x, nextMotion.y, 3.5D);
        }else if(this.getMotion().z < -3.5D) {
            nextMotion = new Vec3d(nextMotion.x, nextMotion.y, -3.5D);
        }
        return nextMotion;
    }

    private void generateParticles(int limit) {
        for(int i = 0; i <= limit; i++) {
            if(this.getMotion().y >= 0) {
                this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, new ItemStack(Items.ORANGE_WOOL)), this.getPosX(), this.getPosY(), this.getPosZ(), rand.nextDouble()*0.1D, -(rand.nextDouble()*0.05D), -(rand.nextDouble()*0.1D));
                this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, new ItemStack(Items.ORANGE_WOOL)), this.getPosX(), this.getPosY(), this.getPosZ(), -(rand.nextDouble()*0.1D), -(rand.nextDouble()*0.1D), rand.nextDouble()*0.1D);
                if(i%2 == 0) {
                    this.world.addParticle(ParticleTypes.SMOKE, this.getPosX(), this.getPosY(), this.getPosZ(), rand.nextDouble()*0.1D, -(rand.nextDouble()*0.3D), -(rand.nextDouble()*0.1D));
                    this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, new ItemStack(Items.YELLOW_WOOL)), this.getPosX(), this.getPosY(), this.getPosZ(), rand.nextDouble()*0.1D, -(rand.nextDouble()*0.05D), -(rand.nextDouble()*0.1D));
                    this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, new ItemStack(Items.YELLOW_WOOL)), this.getPosX(), this.getPosY(), this.getPosZ(), -(rand.nextDouble()*0.1D), -(rand.nextDouble()*0.05D), rand.nextDouble()*0.1D);
                }else {
                    this.world.addParticle(ParticleTypes.SMOKE, this.getPosX(), this.getPosY(), this.getPosZ(), -(rand.nextDouble()*0.1D), -(rand.nextDouble()*0.3D), rand.nextDouble()*0.1D);
                    this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, new ItemStack(Items.RED_WOOL)), this.getPosX(), this.getPosY(), this.getPosZ(), rand.nextDouble()*0.1D, -(rand.nextDouble()*0.05D), -(rand.nextDouble()*0.1D));
                    this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, new ItemStack(Items.RED_WOOL)), this.getPosX(), this.getPosY(), this.getPosZ(), -(rand.nextDouble()*0.1D), -(rand.nextDouble()*0.05D), rand.nextDouble()*0.1D);
                }
            }else {
                this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, new ItemStack(Items.ORANGE_WOOL)), this.getPosX(), this.getPosY(), this.getPosZ(), rand.nextDouble()*0.1D, rand.nextDouble()*0.05D, -(rand.nextDouble()*0.1D));
                this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, new ItemStack(Items.ORANGE_WOOL)), this.getPosX(), this.getPosY(), this.getPosZ(), -(rand.nextDouble()*0.1D), rand.nextDouble()*0.1D, rand.nextDouble()*0.1D);
                if(i%2 == 0) {
                    this.world.addParticle(ParticleTypes.SMOKE, this.getPosX(), this.getPosY(), this.getPosZ(), rand.nextDouble()*0.1D, rand.nextDouble()*0.3D, -(rand.nextDouble()*0.1D));
                    this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, new ItemStack(Items.YELLOW_WOOL)), this.getPosX(), this.getPosY(), this.getPosZ(), rand.nextDouble()*0.1D, rand.nextDouble()*0.05D, -(rand.nextDouble()*0.1D));
                    this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, new ItemStack(Items.YELLOW_WOOL)), this.getPosX(), this.getPosY(), this.getPosZ(), -(rand.nextDouble()*0.1D), rand.nextDouble()*0.05D, rand.nextDouble()*0.1D);
                }else {
                    this.world.addParticle(ParticleTypes.SMOKE, this.getPosX(), this.getPosY(), this.getPosZ(), -(rand.nextDouble()*0.1D), rand.nextDouble()*0.3D, rand.nextDouble()*0.1D);
                    this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, new ItemStack(Items.RED_WOOL)), this.getPosX(), this.getPosY(), this.getPosZ(), rand.nextDouble()*0.1D, rand.nextDouble()*0.05D, -(rand.nextDouble()*0.1D));
                    this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, new ItemStack(Items.RED_WOOL)), this.getPosX(), this.getPosY(), this.getPosZ(), -(rand.nextDouble()*0.1D), rand.nextDouble()*0.05D, rand.nextDouble()*0.1D);
                }
            }
        }
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        if(compound.contains("Origin Y")) {
            this.setOriginY(compound.getInt("Origin Y"));
        }
        if(compound.contains("Target X")) {
            this.setTargetX(compound.getInt("Target X"));
        }
        if(compound.contains("Target Y")) {
            this.setTargetY(compound.getInt("Target Y"));
        }
        if(compound.contains("Target Z")) {
            this.setTargetZ(compound.getInt("Target Z"));
        }
        if(compound.contains("Launched")) {
            this.setLaunched(compound.getBoolean("Launched"));
        }
        if(compound.contains("Stage")) {
            this.setStage(compound.getInt("Stage"));
        }
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putInt("Origin Y", this.getOriginY());
        compound.putInt("Target X", this.getTargetX());
        compound.putInt("Target Y", this.getTargetY());
        compound.putInt("Target Z", this.getTargetZ());
        compound.putBoolean("Launched", this.isLaunched());
        compound.putInt("Stage", this.getStage());
    }

    public void setOriginY(int y) {
        this.dataManager.set(ORIGIN_Y, y);
    }

    public int getOriginY() {
        return this.dataManager.get(ORIGIN_Y);
    }

    public void setTargetX(int x) {
        this.dataManager.set(TARGET_X, x);
    }

    public void setTargetY(int y) {
        this.dataManager.set(TARGET_Y, y);
    }

    public void setTargetZ(int z) {
        this.dataManager.set(TARGET_Z, z);
    }

    public int getTargetX() {
        return this.dataManager.get(TARGET_X);
    }

    public int getTargetY() {
        return this.dataManager.get(TARGET_Y);
    }

    public int getTargetZ() {
        return this.dataManager.get(TARGET_Z);
    }

    public void setLaunched(boolean launched) {
        if(!this.world.isRemote) {
            CustomExplosion explosion = new CustomExplosion(this.world, this.getPosition(), 4, 0.586F);
            explosion.explodeExcluding(this);
        }
        this.setOriginY((int)this.getPosY());
        this.dataManager.set(LAUNCHED, launched);
    }

    public boolean isLaunched() {
        return this.dataManager.get(LAUNCHED);
    }

    public void setStage(int stage) {
        this.dataManager.set(STAGE, stage);
    }

    public int getStage() {
        return this.dataManager.get(STAGE);
    }

    @Override
    public boolean processInitialInteract(PlayerEntity playerIn, Hand handIn) {
        if(playerIn.isShiftKeyDown()) {
            return false;
        }
        return this.displayScreen(this);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean displayScreen(BallisticMissileEntity missile) {
        Minecraft.getInstance().displayGuiScreen(new BallisticMissileScreen(missile));
        return true;
    }

}
