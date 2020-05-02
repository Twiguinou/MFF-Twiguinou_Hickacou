package com.twihick.modularexplosions.util.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Direction;
import net.minecraftforge.client.ForgeHooksClient;

import java.util.Collection;
import java.util.Random;

public class ModelRendererUtil {

    public static void renderModel(IBakedModel model, MatrixStack matrix, IRenderTypeBuffer buffer, int light) {
        matrix.push();
        ForgeHooksClient.handleCameraTransforms(matrix, model, ItemCameraTransforms.TransformType.NONE, false);
        matrix.translate(-0.5D, -0.5D, -0.5D);
        IVertexBuilder builder = buffer.getBuffer(Atlases.getSolidBlockType());
        for(Direction direction : Direction.values()) {
            renderQuads(matrix, builder, model.getQuads(null, direction, new Random()), light);
        }
        renderQuads(matrix, builder, model.getQuads(null, null, new Random()), light);
        matrix.pop();
    }

    public static void renderQuad(MatrixStack.Entry entry, IVertexBuilder builder, BakedQuad quad, int light) {
        float[] floats = decode(quad.getTintIndex());
        builder.addVertexData(entry, quad, floats[0], floats[1], floats[2], floats[3], light, OverlayTexture.NO_OVERLAY);
    }

    public static void renderQuads(MatrixStack matrix, IVertexBuilder builder, Collection<BakedQuad> quads, int light) {
        MatrixStack.Entry entry = matrix.getLast();
        for(BakedQuad quad : quads) {
            renderQuad(entry, builder, quad, light);
        }
    }

    public static float[] decode(int color) {
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        float a = (float) (color >> 24 & 255) / 255.0F;
        return new float[] { r, g, b, a };
    }

}
