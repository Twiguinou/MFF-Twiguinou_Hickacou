package com.twihick.modularexplosions.client.renderer.entity;

import com.twihick.modularexplosions.entities.ExplosiveArrowEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class ExplosiveArrowEntityRenderer extends EntityRenderer<ExplosiveArrowEntity> {

    public ExplosiveArrowEntityRenderer(EntityRendererManager renderManager) {
        super(renderManager);
        renderManager.setDebugBoundingBox(true);
    }

    @Override
    public ResourceLocation getEntityTexture(ExplosiveArrowEntity entity) {
        return null;
    }

}
