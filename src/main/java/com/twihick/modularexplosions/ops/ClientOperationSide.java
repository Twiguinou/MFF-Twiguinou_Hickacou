package com.twihick.modularexplosions.ops;

import com.twihick.modularexplosions.client.renderer.MasterRenderer;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public final class ClientOperationSide extends ServerOperationSide {

    @Override
    public void client(FMLClientSetupEvent event) {
        MasterRenderer.tileEntities();
    }

}
