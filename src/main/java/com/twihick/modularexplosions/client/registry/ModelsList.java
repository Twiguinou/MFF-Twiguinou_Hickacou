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

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerModels(final ModelRegistryEvent event) {
        ClientRegistryMethods.registerModels(event);
    }

    private enum IDS {
        DYNAMITE("dynamite");

        private final String value;
        IDS(String label) {
            this.value = label;
        }
        public String getValue() {
            return value;
        }
    }

}
