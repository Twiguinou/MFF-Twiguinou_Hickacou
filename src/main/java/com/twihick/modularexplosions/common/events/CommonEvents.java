package com.twihick.modularexplosions.common.events;

import com.twihick.modularexplosions.Main;
import com.twihick.modularexplosions.entities.SmokeEffectEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CommonEvents {

    @SubscribeEvent
    public void onCustomExplosionDetonate(final CustomExplosionEvent.Detonate event) {
        SmokeEffectEntity smokeeffect = new SmokeEffectEntity(event.getWorld(), event.getPos(), 250, event.getRadius());
        event.getWorld().addEntity(smokeeffect);
    }

    @SubscribeEvent
    public void entityJoinWorld(final EntityJoinWorldEvent event) {
        if(event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            Main.onPlayerInit(player);
        }
    }

    @SubscribeEvent
    public void onPlayerTick(final TickEvent.ServerTickEvent.PlayerTickEvent event) {
        if(event.player instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.player;
            Main.getCreativeSlotMax(player);
        }
    }

}
