package com.twihick.modularexplosions.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.twihick.modularexplosions.tileentities.ConfigurableBombTileEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ConfigurableBombTileEntityRenderer extends TileEntityRenderer<ConfigurableBombTileEntity> {

    public ConfigurableBombTileEntityRenderer(TileEntityRendererDispatcher renderer) {
        super(renderer);
    }

    @Override
    public void render(ConfigurableBombTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
    }

}
