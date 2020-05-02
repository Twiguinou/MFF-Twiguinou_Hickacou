package com.twihick.modularexplosions.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.twihick.modularexplosions.StringID;
import com.twihick.modularexplosions.client.renderer.entity.models.ExplosiveBeltModel;
import com.twihick.modularexplosions.items.ExplosiveBeltItem;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;

public class ExplosiveBeltLayer<T extends PlayerEntity, K extends BipedModel<T>> extends LayerRenderer<T, K> {

    private static final ResourceLocation TEXTURE_BASE = new ResourceLocation(StringID.ID, "textures/models/entity/explosive_belt_base.png");
    private static final ResourceLocation TEXTURE_DYNAMITE = new ResourceLocation(StringID.ID, "textures/models/entity/explosive_belt_dynamite.png");
    private final ExplosiveBeltModel<T> model;

    public ExplosiveBeltLayer(IEntityRenderer<T, K> renderer, ExplosiveBeltModel model) {
        super(renderer);
        this.model = model;
    }

    @Override
    public void render(MatrixStack matrix, IRenderTypeBuffer buffer, int light, T player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if(player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() instanceof ExplosiveBeltItem) {
            matrix.push();
            this.getEntityModel().setModelAttributes(this.model);
            this.model.copyFromBipedModelAngles(this.getEntityModel());
            IVertexBuilder builder_base = ItemRenderer.getBuffer(buffer, this.model.getRenderType(TEXTURE_BASE), false, false);
            this.model.renderBase(matrix, builder_base, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            IVertexBuilder builder_dynamite = ItemRenderer.getBuffer(buffer, this.model.getRenderType(TEXTURE_DYNAMITE), false, false);
            this.model.renderDynamite(matrix, builder_dynamite, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            matrix.pop();
        }
    }

}
