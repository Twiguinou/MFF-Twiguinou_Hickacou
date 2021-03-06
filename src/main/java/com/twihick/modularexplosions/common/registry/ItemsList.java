package com.twihick.modularexplosions.common.registry;

import com.twihick.modularexplosions.StringID;
import com.twihick.modularexplosions.items.*;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = StringID.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemsList {

    public static final Item REMOTE_CONTROLLER = CommonRegistryMethods.buildItem(RemoteControllerItem.class, IDS.REMOTE_CONTROLLER.getValue());
    public static final Item THROWABLE_DYNAMITE = CommonRegistryMethods.buildItem(ThrowableDynamiteItem.class, IDS.THROWABLE_DYNAMITE.getValue());
    public static final Item EXPLOSIVE_BELT = CommonRegistryMethods.buildItem(ExplosiveBeltItem.class, IDS.EXPLOSIVE_BELT.getValue());
    public static final Item GRENADE_LAUNCHER = CommonRegistryMethods.buildItem(GrenadeLauncherItem.class, IDS.GRENADE_LAUNCHER.getValue());
    public static final Item GRENADE = CommonRegistryMethods.buildItem(GrenadeItem.class, IDS.GRENADE.getValue());
    public static final Item BALLISTIC_MISSILE = CommonRegistryMethods.buildItem(BallisticMissileItem.class, IDS.BALLISTIC_MISSILE.getValue());

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        CommonRegistryMethods.registerItems(event);
    }

    private enum IDS {
        REMOTE_CONTROLLER("remote_controller"),
        THROWABLE_DYNAMITE("throwable_dynamite"),
        EXPLOSIVE_BELT("explosive_belt"),
        GRENADE_LAUNCHER("grenade_launcher"),
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
