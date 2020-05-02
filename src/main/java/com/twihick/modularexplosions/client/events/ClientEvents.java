package com.twihick.modularexplosions.client.events;

import com.twihick.modularexplosions.Main;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientEvents {

    @SubscribeEvent
    public void onPlayerOpenContainer(final PlayerContainerEvent.Open event) {
        if(event.getContainer() instanceof CreativeScreen.CreativeContainer) {
            Main.patchCreativeSlots((CreativeScreen.CreativeContainer)event.getContainer());
        }
    }

}
