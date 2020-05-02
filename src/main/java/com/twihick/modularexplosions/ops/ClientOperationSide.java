package com.twihick.modularexplosions.ops;

import com.twihick.modularexplosions.client.events.ClientEvents;
import com.twihick.modularexplosions.client.registry.KeyBindingsList;
import com.twihick.modularexplosions.client.renderer.MasterRenderer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public final class ClientOperationSide extends ServerOperationSide {

    @Override
    public void client(FMLClientSetupEvent event) {
        MasterRenderer.entities();
        MasterRenderer.tileEntities();
        ClientRegistry.registerKeyBinding(KeyBindingsList.KEY_EXPLODE);
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
    }

}
