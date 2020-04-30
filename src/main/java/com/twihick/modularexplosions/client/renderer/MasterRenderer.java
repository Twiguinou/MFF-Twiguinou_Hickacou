package com.twihick.modularexplosions.client.renderer;

import com.twihick.modularexplosions.client.renderer.tileentity.RemoteBombTileEntityRenderer;
import com.twihick.modularexplosions.client.renderer.tileentity.TimerBombTileEntityRenderer;
import com.twihick.modularexplosions.common.registry.TileEntitiesList;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class MasterRenderer {

    public static void tileEntities() {
        ClientRegistry.bindTileEntityRenderer(TileEntitiesList.TIMER_BOMB, TimerBombTileEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TileEntitiesList.REMOTE_BOMB, RemoteBombTileEntityRenderer::new);
    }

}
