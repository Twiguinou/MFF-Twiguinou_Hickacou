package com.twihick.modularexplosions.client.registry;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class KeyBindingsList {

    public static final KeyBinding KEY_EXPLODE = ClientRegistryMethods.buildKeyBinding(IDS.EXPLODE.getValue(), GLFW.GLFW_KEY_C, KeyCategories.GAMEPLAY);

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
