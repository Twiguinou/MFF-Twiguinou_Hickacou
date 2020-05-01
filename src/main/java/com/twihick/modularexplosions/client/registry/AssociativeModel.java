package com.twihick.modularexplosions.client.registry;

import com.twihick.modularexplosions.StringID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AssociativeModel {

    private final ResourceLocation location;

    @OnlyIn(Dist.CLIENT)
    private IBakedModel bakedModel;

    public AssociativeModel(String label, ModelLocations prefix) {
        this.location = new ResourceLocation(StringID.ID, ModelLocations.format(prefix, label));
    }

    public ResourceLocation getLocation() {
        return this.location;
    }

    @OnlyIn(Dist.CLIENT)
    public IBakedModel bake() {
        if(this.bakedModel == null) {
            ModelManager modelManager = Minecraft.getInstance().getModelManager();
            IBakedModel model = modelManager.getModel(this.location);
            if(model == modelManager.getMissingModel()) {
                return model;
            }
            this.bakedModel = model;
        }
        return this.bakedModel;
    }

}
