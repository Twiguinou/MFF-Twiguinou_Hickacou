package com.twihick.modularexplosions.client.events;

import com.mojang.blaze3d.systems.RenderSystem;
import com.twihick.modularexplosions.items.GrenadeLauncherItem;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public void onRenderGameOverlayPost(final RenderGameOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if(mc.player == null) {
            return;
        }
        if(event.getType().equals(RenderGameOverlayEvent.ElementType.ALL)) {
            PlayerEntity player = mc.player;
            ItemStack stack = player.getHeldItemMainhand();
            if(stack.getItem() instanceof GrenadeLauncherItem) {
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.enableBlend();
                RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                int width = event.getWindow().getScaledWidth();
                String text;
                String text1;
                if(GrenadeLauncherItem.isLoaded(stack)) {
                    text = "Ammo: 1/1";
                }else {
                    text = "Ammo: 0/1";
                }
                mc.fontRenderer.drawString(text, width-3-mc.fontRenderer.getStringWidth(text), 4, 0xFFFFFF);
            }
        }
    }

}
