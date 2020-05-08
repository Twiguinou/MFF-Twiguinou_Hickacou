package com.twihick.modularexplosions.client.registry;

import com.twihick.modularexplosions.StringID;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = StringID.ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModelsList {

    public static final AssociativeModel DYNAMITE = ClientRegistryMethods.buildModel(IDS.DYNAMITE.getValue(), ModelLocations.CUSTOMS);
    public static final AssociativeModel GRENADE = ClientRegistryMethods.buildModel(IDS.GRENADE.getValue(), ModelLocations.CUSTOMS);
    public static final AssociativeModel BALLISTIC_MISSILE = ClientRegistryMethods.buildModel(IDS.BALLISTIC_MISSILE.getValue(), ModelLocations.CUSTOMS);
    public static final AssociativeModel ROCKET_ENGINE = ClientRegistryMethods.buildModel(IDS.ROCKET_ENGINE.getValue(), ModelLocations.CUSTOMS);

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerModels(final ModelRegistryEvent event) {
        ClientRegistryMethods.registerModels(event);
    }

    private enum IDS {
        DYNAMITE("dynamite"),
        GRENADE("grenade"),
        BALLISTIC_MISSILE("ballistic_missile"),
        ROCKET_ENGINE("rocket_engine");

        private final String value;
        IDS(String label) {
            this.value = label;
        }
        public String getValue() {
            return value;
        }
    }

}
