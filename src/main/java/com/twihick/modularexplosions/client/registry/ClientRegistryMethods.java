package com.twihick.modularexplosions.client.registry;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ClientRegistryMethods {

    private static final Logger regLog = LogManager.getLogger();

    private static final List<AssociativeModel> MODELS = new ArrayList<>();

    public static AssociativeModel buildModel(String label, ModelLocations prefix) {
        AssociativeModel model = new AssociativeModel(label, prefix);
        MODELS.add(model);
        return model;
    }

    public static KeyBinding buildKeyBinding(String label, int keyCode, KeyCategories category) {
        return new KeyBinding("key.modularexplosions." + label, keyCode, category.getValue());
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerModels(ModelRegistryEvent event) {
        if(!MODELS.isEmpty()) {
            for(AssociativeModel model : MODELS) {
                ModelLoader.addSpecialModel(model.getLocation());
                regLog.info("Model with path: " + model.getLocation().getPath() + " has been registered.");
            }
            MODELS.clear();
        }
    }

}
