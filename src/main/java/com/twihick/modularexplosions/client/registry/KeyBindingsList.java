package com.twihick.modularexplosions.client.registry;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class KeyBindingsList {

    public static final KeyBinding KEY_EXPLODE = ClientRegistryMethods.buildKeyBinding(IDS.EXPLODE.getValue(), GLFW.GLFW_KEY_C, KeyCategories.GAMEPLAY);

    public static void registerKeyBindings() {
        ClientRegistry.registerKeyBinding(KEY_EXPLODE);
    }

    private enum IDS {
        EXPLODE("explode");

        private final String value;
        IDS(String label) {
            this.value = label;
        }
        public String getValue() {
            return value;
        }
    }

}
