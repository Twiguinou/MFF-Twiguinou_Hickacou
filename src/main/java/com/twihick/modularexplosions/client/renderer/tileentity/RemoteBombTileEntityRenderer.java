package com.twihick.modularexplosions.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.twihick.modularexplosions.tileentities.RemoteBombTileEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RemoteBombTileEntityRenderer extends TileEntityRenderer<RemoteBombTileEntity> {

    public RemoteBombTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(RemoteBombTileEntity bomb, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buffer, int light, int texture) {
    }

}
