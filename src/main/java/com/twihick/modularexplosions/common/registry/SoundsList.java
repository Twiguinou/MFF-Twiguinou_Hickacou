package com.twihick.modularexplosions.common.registry;

import com.twihick.modularexplosions.StringID;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = StringID.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SoundsList {

    public static final SoundEvent DYNAMITE_BLAST = CommonRegistryMethods.buildSound(IDS.DYNAMITE_BLAST.getValue(), SoundLocations.ITEM);
    public static final SoundEvent BOMB_GENERIC = CommonRegistryMethods.buildSound(IDS.BOMB_GENERIC.getValue(), SoundLocations.BLOCK);

    @SubscribeEvent
    public static void registerSounds(final RegistryEvent.Register<SoundEvent> event) {
        CommonRegistryMethods.registerSounds(event);
    }

    private enum IDS {
        DYNAMITE_BLAST("dynamite.blast"),
        BOMB_GENERIC("explosions.bomb_generic");

        private final String value;
        IDS(String label) {
            this.value = label;
        }
        public String getValue() {
            return value;
        }
    }

}
