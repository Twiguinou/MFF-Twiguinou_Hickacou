package com.twihick.modularexplosions.ops;

import com.twihick.modularexplosions.client.registry.KeyBindingsList;
import com.twihick.modularexplosions.client.renderer.MasterRenderer;
import com.twihick.modularexplosions.client.renderer.entity.layers.ExplosiveBeltLayer;
import com.twihick.modularexplosions.client.renderer.entity.models.ExplosiveBeltModel;
import com.twihick.modularexplosions.util.ReflectionUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Map;

public final class ClientOperationSide extends ServerOperationSide {

    @Override
    public void client(FMLClientSetupEvent event) {
        addExplosiveBeltLayerToPlayerModel();
        MasterRenderer.entities();
        MasterRenderer.tileEntities();
        ClientRegistry.registerKeyBinding(KeyBindingsList.KEY_EXPLODE);
    }

    private void addExplosiveBeltLayerToPlayerModel() {
        Map<String, PlayerRenderer> skins = Minecraft.getInstance().getRenderManager().getSkinMap();
        PlayerRenderer playerRendererDefault = skins.get("default");
        PlayerRenderer playerRendererSlim = skins.get("slim");
        ReflectionUtil.addLayerToPlayerModel(playerRendererDefault, new ExplosiveBeltLayer<>(playerRendererDefault, new ExplosiveBeltModel(0.0F)));
        ReflectionUtil.addLayerToPlayerModel(playerRendererSlim, new ExplosiveBeltLayer<>(playerRendererSlim, new ExplosiveBeltModel(0.0F)));
    }

}
