package com.twihick.modularexplosions.util.world;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class CustomExplosion {

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
        Random rand = new Random();
        int a = this.radius;
        int b = (int) (this.radius*this.distFactor);
        for(int x = -a; x <= a; x++) {
            for(int y = -b; y <= b; y++) {
                for(int z = -a; z <= a; z++) {
                    rand.setSeed(42L);
                    if((Math.pow(x+rand.nextFloat(), 2)/Math.pow(a, 2))+(Math.pow(y+rand.nextFloat(), 2)/Math.pow(b, 2))+(Math.pow(z+rand.nextFloat(), 2)/Math.pow(a, 2)) < 1) {
                        BlockPos aPos = new BlockPos(pos.getX()+x, pos.getY()+y, pos.getZ()+z);
                        BlockState state = world.getBlockState(aPos);
                        if(state != Blocks.AIR.getDefaultState() && state != Blocks.BEDROCK.getDefaultState()) {
                            world.setBlockState(aPos, Blocks.AIR.getDefaultState());
                        }
                    }
                }
            }
        }
    }

}
