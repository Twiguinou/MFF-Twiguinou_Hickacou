package com.twihick.modularexplosions.util.world;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;

public class WorldUtil {

    public static int getHighestBlockAtColumnPos(World worldIn, double x, double z) {
        Set<BlockPos> set = new HashSet<>();
        for(int i = 0; i < 256; i++) {
            BlockPos pos = new BlockPos(x, i, z);
            if(!worldIn.isAirBlock(pos)) {
                set.add(pos);
            }
        }
        if(!set.isEmpty()) {
            int highestY = 0;
            for(BlockPos pos : set) {
                if(pos.getY() > highestY) {
                    highestY = pos.getY();
                }
            }
            return highestY;
        }else {
            return 0;
        }
    }

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

}
