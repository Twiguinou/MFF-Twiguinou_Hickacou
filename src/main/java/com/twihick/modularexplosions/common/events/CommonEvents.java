package com.twihick.modularexplosions.common.events;

import com.twihick.modularexplosions.entities.SmokeEffectEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CommonEvents {

    @SubscribeEvent
    public void onCustomExplosionDetonate(final CustomExplosionEvent.Detonate event) {
        SmokeEffectEntity smokeeffect = new SmokeEffectEntity(event.getWorld(), event.getPos(), 250, event.getRadius());
        event.getWorld().addEntity(smokeeffect);
    }

}
