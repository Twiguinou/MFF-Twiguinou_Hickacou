package com.twihick.modularexplosions.client.renderer;

import com.twihick.modularexplosions.client.renderer.entity.DynamiteEntityRenderer;
import com.twihick.modularexplosions.client.renderer.entity.SmokeEffectEntityRenderer;
import com.twihick.modularexplosions.client.renderer.tileentity.ConfigurableBombTileEntityRenderer;
import com.twihick.modularexplosions.client.renderer.tileentity.RemoteBombTileEntityRenderer;
import com.twihick.modularexplosions.client.renderer.tileentity.TimerBombTileEntityRenderer;
import com.twihick.modularexplosions.common.registry.EntitiesList;
import com.twihick.modularexplosions.common.registry.TileEntitiesList;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class MasterRenderer {

    public static void entities() {
        RenderingRegistry.registerEntityRenderingHandler(EntitiesList.SMOKE_EFFECT, SmokeEffectEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitiesList.DYNAMITE, DynamiteEntityRenderer::new);
    }

    public static void tileEntities() {
        ClientRegistry.bindTileEntityRenderer(TileEntitiesList.TIMER_BOMB, TimerBombTileEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TileEntitiesList.REMOTE_BOMB, RemoteBombTileEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TileEntitiesList.CONFIGURABLE_BOMB, ConfigurableBombTileEntityRenderer::new);
    }

}
