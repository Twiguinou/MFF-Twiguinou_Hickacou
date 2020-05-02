package com.twihick.modularexplosions.client.renderer.entity.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ExplosiveBeltModel<T extends LivingEntity> extends BipedModel<T> {

    private final ModelRenderer belt_base;
    private final ModelRenderer belt_dynamite;

    public ExplosiveBeltModel(float modelSize) {
        super(modelSize);
        this.textureWidth = 16;
        this.textureHeight = 16;
        belt_base = new ModelRenderer(this);
        belt_base.setRotationPoint(0.0F, 24.0F, 0.0F);
        belt_base.setTextureOffset(0, 0).addBox(-4.0F, 3.5F, -2.5F, 8.0F, 7.0F, 5.0F, 0.0F);
        belt_dynamite = new ModelRenderer(this);
        belt_dynamite.setRotationPoint(0.0F, 24.0F, 0.0F);
        belt_dynamite.setTextureOffset(0, 0).addBox(-3.3125F, -19.0F, -3.0F, 1.0F, 5.0F, 6.0F, 0.0F, false);
        belt_dynamite.setTextureOffset(0, 0).addBox(-1.4375F, -19.0F, -3.0F, 1.0F, 5.0F, 6.0F, 0.0F, false);
        belt_dynamite.setTextureOffset(0, 0).addBox(2.3125F, -19.0F, -3.0F, 1.0F, 5.0F, 6.0F, 0.0F, false);
        belt_dynamite.setTextureOffset(0, 0).addBox(0.4375F, -19.0F, -3.0F, 1.0F, 5.0F, 6.0F, 0.0F, false);
    }

    public void renderBase(MatrixStack matrix, IVertexBuilder builder, int light, int texture, float r, float g, float b, float a) {
        belt_base.render(matrix, builder, light, texture);
    }

    public void renderDynamite(MatrixStack matrix, IVertexBuilder builder, int light, int texture, float r, float g, float b, float a) {
        belt_dynamite.render(matrix, builder, light, texture);
    }

    public void copyFromBipedModelAngles(BipedModel<T> model) {
        this.belt_base.copyModelAngles(model.bipedBody);
    }

}
