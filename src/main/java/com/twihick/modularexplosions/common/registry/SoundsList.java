package com.twihick.modularexplosions.common.registry;

import com.twihick.modularexplosions.StringID;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = StringID.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SoundsList {

    public static final SoundEvent DYNAMITE_BLAST = CommonRegistryMethods.buildSound(IDS.DYNAMITE_BLAST.getValue(), SoundLocations.ITEM);
    public static final SoundEvent GRENADE_LAUNCHER_RELOAD = CommonRegistryMethods.buildSound(IDS.GRENADE_LAUNCHER_RELOAD.getValue(), SoundLocations.ITEM);
    public static final SoundEvent GRENADE_LAUNCHER_EMPTY = CommonRegistryMethods.buildSound(IDS.GRENADE_LAUNCHER_EMPTY.getValue(), SoundLocations.ITEM);
    public static final SoundEvent GRENADE_LAUNCHER_FIRE = CommonRegistryMethods.buildSound(IDS.GRENADE_LAUNCHER_FIRE.getValue(), SoundLocations.ITEM);

    public static final SoundEvent BOMB_GENERIC = CommonRegistryMethods.buildSound(IDS.BOMB_GENERIC.getValue(), SoundLocations.BLOCK);

    public static final SoundEvent BALLISTIC_MISSILE_THRUST = CommonRegistryMethods.buildSound(IDS.BALLISTIC_MISSILE_THRUST.getValue(), SoundLocations.ENTITY);

    @SubscribeEvent
    public static void registerSounds(final RegistryEvent.Register<SoundEvent> event) {
        CommonRegistryMethods.registerSounds(event);
    }

    private enum IDS {
        DYNAMITE_BLAST("dynamite.blast"),
        GRENADE_LAUNCHER_RELOAD("grenade_launcher.reload"),
        GRENADE_LAUNCHER_EMPTY("grenade_launcher.empty"),
        GRENADE_LAUNCHER_FIRE("grenade_launcher.fire"),
        BOMB_GENERIC("explosions.bomb_generic"),
        BALLISTIC_MISSILE_THRUST("ballistic_missile.thrust");

        private final String value;
        IDS(String label) {
            this.value = label;
        }
        public String getValue() {
            return value;
        }
    }

}
