package com.twihick.modularexplosions.common.registry;

import com.twihick.modularexplosions.StringID;
import com.twihick.modularexplosions.items.RemoteControllerItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = StringID.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemsList {

    public static final Item REMOTE_CONTROLLER = RegistryMethods.buildItem(RemoteControllerItem.class, IDS.REMOTE_CONTROLLER.getValue());

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        RegistryMethods.registerItems(event);
    }

    private enum IDS {
        REMOTE_CONTROLLER("remote_controller");

        private final String value;
        IDS(String label) {
            this.value = label;
        }
        public String getValue() {
            return value;
        }
    }

}
