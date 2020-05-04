package com.twihick.modularexplosions.util;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.List;

public class ReflectionUtil {

    public static void setCustomPlayerModel(PlayerRenderer playerRenderer, PlayerModel playerModel) {
        ObfuscationReflectionHelper.setPrivateValue(LivingRenderer.class, playerRenderer, playerModel, "field_77045_g");
    }

    public static void addLayerToPlayerModel(PlayerRenderer playerRenderer, LayerRenderer layerRenderer) {
        List<LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>>> layers = ObfuscationReflectionHelper.getPrivateValue(LivingRenderer.class, playerRenderer, "field_177097_h");
        layers.add(layerRenderer);
    }

}
