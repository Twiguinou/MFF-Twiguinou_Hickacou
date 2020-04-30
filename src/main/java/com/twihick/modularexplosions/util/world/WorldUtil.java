package com.twihick.modularexplosions.util.world;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WorldUtil {

    public static Set<BlockPos> getBlocksInRadius(World world, BlockPos pos, int radius, boolean air) {
        Set<BlockPos> set = new HashSet<>();
        for(int x = -radius; x <= radius; x++) {
            for(int y = -radius; y <= radius; y++) {
                for(int z = -radius; z <= radius; z++) {
                    BlockPos aPos = new BlockPos(pos.getX()+x, pos.getY()+y, pos.getZ()+z);
                    BlockState state = world.getBlockState(aPos);
                    if(state != Blocks.AIR.getDefaultState()) {
                        set.add(aPos);
                    }else if(air) {
                        set.add(aPos);
                    }
                }
            }
        }
        return set;
    }

    public static <T extends Entity> List<T> getEntitiesInRadius(Class<T> clazz, World world, BlockPos pos, int radius) {
        Set<BlockPos> set = getBlocksInRadius(world, pos, radius, true);
        List<AxisAlignedBB> aabbs = new ArrayList<>();
        set.forEach(blockpos -> aabbs.add(new AxisAlignedBB(blockpos.getX(), blockpos.getY(), blockpos.getZ(), blockpos.getX()+1, blockpos.getY()+1, blockpos.getZ()+1)));
        List<T> entities = new ArrayList<>();
        for(AxisAlignedBB aabb : aabbs) {
            List<T> temp = world.getEntitiesWithinAABB(clazz, aabb);
            temp.forEach(entity -> entities.add(entity));
        }
        return entities;
    }

}
