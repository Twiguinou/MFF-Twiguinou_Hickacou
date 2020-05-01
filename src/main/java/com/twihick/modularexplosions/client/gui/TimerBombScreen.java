package com.twihick.modularexplosions.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.twihick.modularexplosions.StringID;
import com.twihick.modularexplosions.blocks.TimerBombBlock;
import com.twihick.modularexplosions.tileentities.TimerBombTileEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.awt.*;

public class TimerBombScreen extends Screen {

    private static final ResourceLocation GUI_BACKGROUND = new ResourceLocation(StringID.ID, "textures/gui/timer_bomb_screen.png");

    private final int xSize = 151;
    private final int ySize = 101;

    private final TimerBombTileEntity bomb;
    private Button buttonMore;
    private Button buttonLess;
    private Button buttonActivate;

    public TimerBombScreen(TimerBombTileEntity bomb) {
        super(new TranslationTextComponent("gui.modularexplosions.timer_bomb_screen"));
        this.bomb = bomb;
    }

    @Override
    protected void init() {
        int leftCorner = (this.width-this.xSize) / 2;
        int topCorner = (this.height-this.ySize) / 2;
        this.buttonMore = this.addButton(new Button(leftCorner+5, topCorner+30, 40, 20, I18n.format("gui.button.modularexplosions.more"), button -> {
            bomb.setInitialTicks(bomb.getInitialTicks()+5);
        }));
        this.buttonLess = this.addButton(new Button(leftCorner+105, topCorner+30, 40, 20, I18n.format("gui.button.modularexplosions.less"), button -> {
            bomb.setInitialTicks(bomb.getInitialTicks()-5);
        }));
        this.buttonActivate = this.addButton(new Button(leftCorner+35, topCorner+65, 80, 20, I18n.format("gui.button.modularexplosions.activate"), button -> {
            bomb.getWorld().setBlockState(bomb.getPos(), bomb.getBlockState().with(TimerBombBlock.ACTIVATED, Boolean.valueOf(true)));
            this.minecraft.player.closeScreen();
        }));
        this.buttonMore.active = false;
        this.buttonLess.active = false;
        this.buttonActivate.active = false;
    }

    @Override
    public void tick() {
        super.tick();
        this.buttonMore.active = !bomb.isBlockActivated();
        this.buttonLess.active = !bomb.isBlockActivated() && bomb.getInitialTicks() > 20;
        this.buttonActivate.active = !bomb.isBlockActivated();
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI_BACKGROUND);
        int leftCorner = (this.width-this.xSize) / 2;
        int topCorner = (this.height-this.ySize) / 2;
        this.blit(leftCorner, topCorner, 0, 0, this.xSize, this.ySize);
        super.render(mouseX, mouseY, partialTicks);
        this.font.drawString(new StringTextComponent(String.valueOf(this.bomb.getRunningTicks())).getFormattedText(), (leftCorner+this.xSize/2)-7, topCorner+25, Color.WHITE.getRGB());
    }

}
