package com.twihick.modularexplosions.common.registry;

import com.twihick.modularexplosions.StringID;
import com.twihick.modularexplosions.entities.BallisticMissileEntity;
import com.twihick.modularexplosions.entities.DynamiteEntity;
import com.twihick.modularexplosions.entities.GrenadeEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = StringID.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntitiesList {

    public static final EntityType<DynamiteEntity> DYNAMITE = CommonRegistryMethods.buildEntity(DynamiteEntity.class, EntityClassification.MISC, 0.25F, 0.25F, IDS.DYNAMITE.getValue());
    public static final EntityType<GrenadeEntity> GRENADE = CommonRegistryMethods.buildEntity(GrenadeEntity.class, EntityClassification.MISC, 0.25F, 0.25F, IDS.GRENADE.getValue());
    public static final EntityType<BallisticMissileEntity> BALLISTIC_MISSILE = CommonRegistryMethods.buildEntity(BallisticMissileEntity.class, EntityClassification.MISC, 1.25F, 7.8F, IDS.BALLISTIC_MISSILE.getValue());

    @SubscribeEvent
    public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event) {
        CommonRegistryMethods.registerEntities(event);
    }

    private enum IDS {
        DYNAMITE("dynamite"),
        GRENADE("grenade"),
        BALLISTIC_MISSILE("ballistic_missile");

        private final String value;
        IDS(String label) {
            this.value = label;
        }
        public String getValue() {
            return value;
        }
    }

}
