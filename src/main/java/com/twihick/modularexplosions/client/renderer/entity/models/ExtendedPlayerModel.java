package com.twihick.modularexplosions.client.renderer.entity.models;

import com.twihick.modularexplosions.items.GrenadeLauncherItem;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExtendedPlayerModel<T extends LivingEntity> extends PlayerModel<T> {

    public ExtendedPlayerModel(float modelSize, boolean smallArmsIn) {
        super(modelSize, smallArmsIn);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        if(entityIn.getHeldItemMainhand().getItem() instanceof GrenadeLauncherItem) {
            this.bipedRightArm.rotateAngleX = -1.5F;
            this.bipedLeftArm.rotateAngleX = -1.5F;
            this.bipedLeftArm.rotationPointX -= 1.5F;
            this.bipedLeftArm.rotateAngleY = 0.7F;

        }
        if(entityIn.getHeldItemOffhand().getItem() instanceof GrenadeLauncherItem) {
            this.bipedRightArm.rotateAngleX = -1.5F;
            this.bipedLeftArm.rotateAngleX = -1.5F;
            this.bipedRightArm.rotationPointX += 1.5F;
            this.bipedRightArm.rotateAngleY = -0.7F;
        }
    }

}
