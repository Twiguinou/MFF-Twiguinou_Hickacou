package com.twihick.modularexplosions.common.events;

import com.twihick.modularexplosions.util.world.CustomExplosion;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.List;

public class ExtendedEvents extends ForgeEventFactory {

    public static void onCustomExplosionDetonate(World world, CustomExplosion explosion, BlockPos pos, List<Entity> entities) {
        MinecraftForge.EVENT_BUS.post(new CustomExplosionEvent.Detonate(world, explosion, pos, entities));
    }

}
