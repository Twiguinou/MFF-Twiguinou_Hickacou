package com.twihick.modularexplosions.common.registry;

import com.twihick.modularexplosions.StringID;
import com.twihick.modularexplosions.entities.SmokeEffectEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = StringID.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntitiesList {

    public static final EntityType<SmokeEffectEntity> SMOKE_EFFECT = RegistryMethods.buildEntity(SmokeEffectEntity.class, EntityClassification.MISC, 0.00001F, 0.00001F, IDS.SMOKE_EFFECT.getValue());

    @SubscribeEvent
    public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event) {
        RegistryMethods.registerEntities(event);
    }

    private enum IDS {
        SMOKE_EFFECT("smoke_effect");

        private final String value;
        IDS(String label) {
            this.value = label;
        }
        public String getValue() {
            return value;
        }
    }

}
