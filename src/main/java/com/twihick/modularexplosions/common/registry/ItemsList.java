package com.twihick.modularexplosions.common.registry;

import com.twihick.modularexplosions.StringID;
import com.twihick.modularexplosions.items.RemoteControllerItem;
import com.twihick.modularexplosions.items.ThrowableDynamiteItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = StringID.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemsList {

    public static final Item REMOTE_CONTROLLER = CommonRegistryMethods.buildItem(RemoteControllerItem.class, IDS.REMOTE_CONTROLLER.getValue());
    public static final Item THROWABLE_DYNAMITE = CommonRegistryMethods.buildItem(ThrowableDynamiteItem.class, IDS.THROWABLE_DYNAMITE.getValue());

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        CommonRegistryMethods.registerItems(event);
    }

    private enum IDS {
        REMOTE_CONTROLLER("remote_controller"),
        THROWABLE_DYNAMITE("throwable_dynamite");

        private final String value;
        IDS(String label) {
            this.value = label;
        }
        public String getValue() {
            return value;
        }
    }

}
