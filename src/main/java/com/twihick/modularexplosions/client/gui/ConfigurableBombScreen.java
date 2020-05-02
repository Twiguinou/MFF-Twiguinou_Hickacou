package com.twihick.modularexplosions.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.twihick.modularexplosions.StringID;
import com.twihick.modularexplosions.tileentities.ConfigurableBombTileEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.awt.*;

public class ConfigurableBombScreen extends Screen {

    private static final ResourceLocation GUI_BACKGROUND = new ResourceLocation(StringID.ID, "textures/gui/configurable_bomb_screen.png");

    private final int xSize = 220;
    private final int ySize = 220;

    private final ConfigurableBombTileEntity bomb;
    private Button widthPlus;
    private Button widthMinus;
    private Button heightPlus;
    private Button heightMinus;

    public ConfigurableBombScreen(ConfigurableBombTileEntity bomb) {
        super(new TranslationTextComponent("gui.modularexplosions.configurable_bomb_screen"));
        this.bomb = bomb;
    }

    @Override
    public void init() {
        int leftCorner = (this.width-this.xSize) / 2;
        int topCorner = (this.height-this.ySize) / 2;
        this.widthPlus = this.addButton(new Button(leftCorner+10, topCorner+165, 80, 20, I18n.format("gui.button.modularexplosions.widthPlus"), button -> {
            this.bomb.width++;
        }));
        this.widthMinus = this.addButton(new Button(leftCorner+10, topCorner+190, 80, 20, I18n.format("gui.button.modularexplosions.widthMinus"), button -> {
            if (this.bomb.width-1 >= 0) this.bomb.width--;
        }));
        this.heightPlus = this.addButton(new Button(leftCorner+130, topCorner+165, 80, 20, I18n.format("gui.button.modularexplosions.heightPlus"), button -> {
            this.bomb.height++;
        }));
        this.heightMinus = this.addButton(new Button(leftCorner+130, topCorner+190, 80, 20, I18n.format("gui.button.modularexplosions.heightMinus"), button -> {
            if (this.bomb.height-1 >= 0) this.bomb.height--;
        }));
        this.widthPlus.active = true;
        this.widthMinus.active = true;
        this.heightPlus.active = true;
        this.heightMinus.active = true;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI_BACKGROUND);
        int leftCorner = (this.width-this.xSize) / 2;
        int topCorner = (this.height-this.ySize) / 2;
        this.blit(leftCorner, topCorner, 0, 0, this.xSize, this.ySize);
        String WIDTH_TEXT = "Width = " + this.bomb.width;
        String HEIGHT_TEXT = "Height = " + this.bomb.height;
        super.render(mouseX, mouseY, partialTicks);
        this.font.drawString(new StringTextComponent(WIDTH_TEXT).getFormattedText(), leftCorner+110-(font.getStringWidth(WIDTH_TEXT)/2), topCorner+60, Color.WHITE.getRGB());
        this.font.drawString(new StringTextComponent(HEIGHT_TEXT).getFormattedText(), leftCorner+110-(font.getStringWidth(HEIGHT_TEXT)/2), topCorner+100, Color.WHITE.getRGB());
    }

}
