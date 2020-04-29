package com.twihick.modularexplosions.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.twihick.modularexplosions.blocks.TimerBombBlock;
import com.twihick.modularexplosions.tileentities.TimerBombTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

public class TimerBombTileEntityRenderer extends TileEntityRenderer<TimerBombTileEntity> {

    public TimerBombTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(TimerBombTileEntity bomb, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buffer, int light, int texture) {
        BlockState state = bomb.getBlockState();
        final String test = "0000";
        if(state.getBlock() instanceof TimerBombBlock) {
            matrix.push();
            matrix.translate(0.51D, 0.5D, 0.0D);
            matrix.scale(0.0175F, 0.0175F, 0.0175F);
            FontRenderer fontRenderer = this.renderDispatcher.fontRenderer;
            fontRenderer.renderString(test, (float)(-fontRenderer.getStringWidth(test)/2), 0, texture, false, matrix.getLast().getMatrix(), buffer, false, 0, light);
            matrix.pop();
        }
    }

}
