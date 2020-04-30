package com.twihick.modularexplosions.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.twihick.modularexplosions.entities.SmokeEffectEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SmokeEffectEntityRenderer extends EntityRenderer<SmokeEffectEntity> {

    public SmokeEffectEntityRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(SmokeEffectEntity entity, float yaw, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buffer, int light) {
    }

    @Override
    public ResourceLocation getEntityTexture(SmokeEffectEntity entity) {
        return null;
    }

}
