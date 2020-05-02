package com.twihick.modularexplosions.util.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;

/*
This class is useful to provide faster response time of keys.
 */
@OnlyIn(Dist.CLIENT)
public class KeyboardUtil {

    public static boolean isKeyPressed(KeyBinding key) {
        Minecraft mc = Minecraft.getInstance();
        return GLFW.glfwGetKey(mc.getMainWindow().getHandle(), key.getKey().getKeyCode()) == GLFW.GLFW_PRESS && mc.currentScreen == null;
    }

}
