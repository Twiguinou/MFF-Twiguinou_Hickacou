package com.twihick.modularexplosions.common.registry;

import com.twihick.modularexplosions.StringID;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = StringID.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemsList {

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        RegistryMethods.registerItems(event);
    }

}
