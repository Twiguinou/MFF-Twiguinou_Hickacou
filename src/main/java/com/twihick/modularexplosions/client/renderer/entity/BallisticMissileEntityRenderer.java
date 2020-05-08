package com.twihick.modularexplosions.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.twihick.modularexplosions.client.registry.ModelsList;
import com.twihick.modularexplosions.entities.BallisticMissileEntity;
import com.twihick.modularexplosions.util.client.ModelRendererUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BallisticMissileEntityRenderer extends EntityRenderer<BallisticMissileEntity> {

    private static final String LAUNCH_TEXT = "The missile is ready to launch !";

    public BallisticMissileEntityRenderer(EntityRendererManager renderManager) {
        super(renderManager);
        renderManager.setDebugBoundingBox(true);
    }

    @Override
    public void render(BallisticMissileEntity missile, float entityYaw, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buffer, int light) {
        matrix.push();
        if(!missile.isLaunched()) {
            Minecraft mc = Minecraft.getInstance();
            double distance = this.renderManager.squareDistanceTo(missile);
            if(!(distance > 4096.0D)) {
                float height = missile.getHeight() + 0.5F;
                matrix.push();
                matrix.translate(0.0D, (double)height, 0.0D);
                matrix.rotate(this.renderManager.getCameraOrientation());
                matrix.scale(-0.025F, -0.025F, 0.025F);
                Matrix4f matrix4f = matrix.getLast().getMatrix();
                float opacity = mc.gameSettings.getTextBackgroundOpacity(0.25F);
                int alpha = (int) (opacity * 255.0F) << 24;
                FontRenderer font = this.getFontRendererFromRenderManager();
                float width = (float) (-font.getStringWidth(LAUNCH_TEXT)/2);
                font.renderString(LAUNCH_TEXT, width, 0.0F, 553648127, false, matrix4f, buffer, true, alpha, light);
                font.renderString(LAUNCH_TEXT, width, 0.0F, -1, false, matrix4f, buffer, false, 0, light);
                matrix.pop();
            }
        }
        matrix.translate(0.0D, 4.125D, 0.0D);
        if(missile.isLaunched()) {
            matrix.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(partialTicks, missile.prevRotationYaw, missile.rotationYaw)-180.0F));
            matrix.rotate(Vector3f.XP.rotationDegrees(MathHelper.lerp(partialTicks, missile.prevRotationPitch, missile.prevRotationPitch)-90.0F));
        }
        matrix.scale(1.0F, 2.75F, 1.0F);
        ModelRendererUtil.renderModel(ModelsList.BALLISTIC_MISSILE.bake(), matrix, buffer, light);
        matrix.scale(1.0F, 0.4F, 1.0F);
        matrix.translate(0.0D, -3.15D, 0.0D);
        ModelRendererUtil.renderModel(ModelsList.ROCKET_ENGINE.bake(), matrix, buffer, light);
        matrix.pop();
    }

    @Override
    public ResourceLocation getEntityTexture(BallisticMissileEntity entity) {
        return null;
    }

}
