package com.twihick.modularexplosions.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.twihick.modularexplosions.client.registry.ModelsList;
import com.twihick.modularexplosions.entities.GrenadeEntity;
import com.twihick.modularexplosions.util.client.ModelRendererUtil;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class GrenadeEntityRenderer extends EntityRenderer<GrenadeEntity> {

    public GrenadeEntityRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(GrenadeEntity grenade, float entityYaw, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buffer, int light) {
        matrix.push();
        matrix.translate(0.0D, 0.1D, 0.0D);
        matrix.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, grenade.prevRotationYaw, grenade.rotationYaw)-180.0F));
        matrix.rotate(Vector3f.XP.rotationDegrees(MathHelper.lerp(partialTicks, grenade.prevRotationPitch, grenade.rotationPitch)));
        ModelRendererUtil.renderModel(ModelsList.GRENADE.bake(), matrix, buffer, light);
        matrix.pop();
    }

    @Override
    public ResourceLocation getEntityTexture(GrenadeEntity entity) {
        return null;
    }

}
