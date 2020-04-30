package com.twihick.modularexplosions.common.events;

import com.twihick.modularexplosions.util.world.CustomExplosion;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.Event;

import java.util.List;

public class CustomExplosionEvent extends Event {

    private final World world;
    private final CustomExplosion explosion;
    private final BlockPos pos;

    public CustomExplosionEvent(World world, CustomExplosion explosion, BlockPos pos) {
        this.world = world;
        this.explosion = explosion;
        this.pos = pos;
    }

    public World getWorld() {
        return world;
    }

    public CustomExplosion getExplosion() {
        return explosion;
    }

    public BlockPos getPos() {
        return pos;
    }

    public static class Detonate extends CustomExplosionEvent {
        private final List<Entity> entities;
        public Detonate(World world, CustomExplosion explosion, BlockPos pos, List<Entity> entities) {
            super(world, explosion, pos);
            this.entities = entities;
        }
        public List<Entity> getEntities() {
            return entities;
        }
        public int getRadius() {
            return getExplosion().radius;
        }
    }

}
