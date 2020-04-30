package com.twihick.modularexplosions.util.world;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.*;

public class CustomExplosion {

    private final Map<PlayerEntity, Vec3d> playerKnockbackMap = new HashMap<>();

    private final World world;
    private final BlockPos pos;
    private final int radius;
    private final float distFactor;

    public CustomExplosion(World world, BlockPos pos, int radius, float distFactor) {
        this.world = world;
        this.pos = pos;
        this.radius = radius;
        this.distFactor = distFactor;
    }

    public void explode() {
        explodeExcluding(null);
    }

    public void explodeExcluding(@Nullable Entity entityIn) {
        List<BlockPos> set = new ArrayList<>();
        Random rand = new Random();
        int a = this.radius;
        int b = (int) (this.radius*this.distFactor);
        for(int x = -a; x <= a; x++) {
            for(int y = -b; y <= b; y++) {
                for(int z = -a; z <= a; z++) {
                    if((Math.pow(x+rand.nextFloat(), 2)/Math.pow(a+rand.nextFloat(), 2))+(Math.pow(y+rand.nextFloat(), 2)/Math.pow(b+rand.nextFloat(), 2))+(Math.pow(z+rand.nextFloat(), 2)/Math.pow(a+rand.nextFloat(), 2)) < 1) {
                        BlockPos blockpos = new BlockPos(pos.getX()+x, pos.getY()+y, pos.getZ()+z);
                        BlockState state = world.getBlockState(blockpos);
                        if(state != Blocks.AIR.getDefaultState() && state != Blocks.BEDROCK.getDefaultState()) {
                            set.add(blockpos);
                        }
                    }
                }
            }
        }
        int minX = MathHelper.floor(pos.getX()-this.radius);
        int minY = MathHelper.floor(pos.getY()-this.radius);
        int minZ = MathHelper.floor(pos.getZ()-this.radius);
        int maxX = MathHelper.ceil(pos.getX()+this.radius);
        int maxY = MathHelper.ceil(pos.getY()+this.radius);
        int maxZ = MathHelper.ceil(pos.getZ()+this.radius);
        AxisAlignedBB aabb = new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
        List<Entity> entities;
        entities = entityIn == null ? world.getEntitiesWithinAABB(Entity.class, aabb) : world.getEntitiesWithinAABBExcludingEntity(entityIn, aabb);
        Vec3d vecPos = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
        float f = radius * 4.0F;
        for(Entity entity : entities) {
            if(!entity.isImmuneToExplosions()) {
                double distance = Math.sqrt(entity.getDistanceSq(vecPos)) / f;
                if(distance <= 1.0D) {
                    double kx = entity.getPosX() - vecPos.x;
                    double ky = entity.getPosY() - vecPos.y;
                    double kz = entity.getPosZ() - vecPos.z;
                    double d0 = Math.sqrt(kx*kx+ky*ky+kz*kz);
                    if(d0 != 0.0D) {
                        kx /= d0;
                        ky /= d0;
                        kz /= d0;
                        double density = Explosion.getBlockDensity(vecPos, entity);
                        double d1 = (1.0D-distance) * density;
                        entity.attackEntityFrom(DamageSource.GENERIC, (float)((int)((d1*d1+d1)/2.0D*7.0D*(double)f+1.0D)));
                        double d2 = d1;
                        if(entity instanceof LivingEntity) {
                            d2 = ProtectionEnchantment.getBlastDamageReduction((LivingEntity)entity, d1);
                        }
                        entity.setMotion(entity.getMotion().add(kx*d2, ky*d2, kz*d2));
                        if(entity instanceof PlayerEntity) {
                            PlayerEntity player = (PlayerEntity) entity;
                            if(!player.isSpectator() && !player.isCreative()) {
                                playerKnockbackMap.put(player, new Vec3d(kx-d1, ky-d1, kz-d1));
                            }
                        }
                    }
                }
            }
        }
        playerKnockbackMap.forEach((player, motion) -> player.setMotion(player.getMotion().add(motion.scale(1.75F))));
        set.forEach(blockPos -> world.setBlockState(blockPos, Blocks.AIR.getDefaultState()));
    }

}
