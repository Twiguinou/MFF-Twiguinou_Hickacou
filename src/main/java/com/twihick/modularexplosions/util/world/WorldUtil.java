package com.twihick.modularexplosions.util.world;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
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

}
