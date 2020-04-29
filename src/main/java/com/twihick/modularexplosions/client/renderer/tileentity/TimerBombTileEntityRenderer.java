package com.twihick.modularexplosions.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.twihick.modularexplosions.blocks.TimerBombBlock;
import com.twihick.modularexplosions.tileentities.TimerBombTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TimerBombTileEntityRenderer extends TileEntityRenderer<TimerBombTileEntity> {

    public TimerBombTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(TimerBombTileEntity bomb, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buffer, int light, int texture) {
        BlockState state = bomb.getBlockState();
        if(state.getBlock() instanceof TimerBombBlock) {
            matrix.push();
            matrix.translate(0.5D, 1.1D, 0.75D);
            int deg = state.get(TimerBombBlock.DIRECTION).getHorizontalIndex();
            matrix.rotate(Vector3f.YP.rotationDegrees(-90F*deg+180F));
            matrix.rotate(Vector3f.XP.rotationDegrees(-180F));
            matrix.scale(0.01F, 0.01F, 0.01F);
            FontRenderer fontRenderer = this.renderDispatcher.fontRenderer;
            fontRenderer.renderString(bomb.getStringFormatted(), (float)(-fontRenderer.getStringWidth(bomb.getStringFormatted())/2), 0, texture, false, matrix.getLast().getMatrix(), buffer, false, 0, light);
            matrix.pop();
        }
    }

}
