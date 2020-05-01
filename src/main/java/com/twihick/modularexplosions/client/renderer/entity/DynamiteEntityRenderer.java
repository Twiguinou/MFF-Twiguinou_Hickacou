package com.twihick.modularexplosions.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.twihick.modularexplosions.client.registry.ModelsList;
import com.twihick.modularexplosions.entities.DynamiteEntity;
import com.twihick.modularexplosions.util.client.ModelRendererUtil;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DynamiteEntityRenderer extends EntityRenderer<DynamiteEntity> {

    public DynamiteEntityRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(DynamiteEntity entity, float yaw, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buffer, int light) {
        matrix.push();
        matrix.translate(0.0D, 0.1D, 0.0D);
        matrix.rotate(Vector3f.YP.rotationDegrees(yaw));
        matrix.rotate(Vector3f.XP.rotationDegrees(entity.getPitch(partialTicks)));
        ModelRendererUtil.renderModel(ModelsList.DYNAMITE.bake(), matrix, buffer, light);
        matrix.pop();
    }

    @Override
    public ResourceLocation getEntityTexture(DynamiteEntity entity) {
        return null;
    }

}
